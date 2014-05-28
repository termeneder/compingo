package bram.lingo.standardwordfinder.genetic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import bram.lingo.standardwordfinder.OptimalWordSets;
import bram.lingo.standardwordfinder.Select;
import bram.lingo.standardwordfinder.SortOrder;
import bram.lingo.standardwordfinder.StandardWordSetFinder;
import bram.lingo.standardwordfinder.genetic.distributor.DistributionFactory;
import bram.lingo.standardwordfinder.genetic.distributor.GenerationDistributor;
import bram.lingo.standardwordfinder.genetic.distributor.GenerationDistributor.IndividualType;
import bram.lingo.standardwordfinder.valuator.WordSetValuator;
import bram.lingo.words.Word;
import bram.lingo.words.wordSets.OrderedWordSet;
import bram.lingo.words.wordSets.WordSet;

public class GeneticComparativeFinder extends StandardWordSetFinder {

	private WordSetValuator c_valuator;
	private GeneticConfiguration c_config;
	private SortOrder c_order;
	
	public GeneticComparativeFinder(
			WordSetValuator valuator,
			SortOrder order,
			GeneticConfiguration config) {
		c_valuator = valuator;
		c_order = order;
		c_config = config;
	}
	
	public GeneticComparativeFinder(
			WordSetValuator valuator,
			Select select,
			GeneticConfiguration config) {
		c_valuator = valuator;
		c_order = SortOrder.getSortOrderFromSelectAndBest(select, getSortOrderForBest());
		c_config = config;
	}
	
	public OptimalWordSets findOptimal(WordSet set) {
		GenerationDistributor distributor = DistributionFactory.get(c_config);
		TopResultSet topResultSet = new TopResultSet(c_config.amountOfSetKept, c_order);
		if (set.size() < getSubsetSize()) {
			return new OptimalWordSets(c_order);
		}
		for (int generation = 0 ; generation < c_config.generations ; generation++) {
			topResultSet = runGeneration(topResultSet, set, distributor);
		}
		return topResultSet.getBest();
	}

	private TopResultSet runGeneration(TopResultSet topResultSet, WordSet set, GenerationDistributor distributor) {
		topResultSet = runNewSets(topResultSet, set, distributor);
		topResultSet = runMutations(topResultSet, set, distributor);
		topResultSet = runRecombinations(topResultSet, set, distributor);
		distributor.updateDistribution();
		return topResultSet;
	}

	private TopResultSet runNewSets(TopResultSet topResultSet, WordSet set, GenerationDistributor distributor) {
		for (int i = 0 ; i < distributor.getAmount(IndividualType.RANDOM) ; i++) {
			WordSet randomSet = getRandomSubset(set);
			boolean isAdded = addIfInTop(topResultSet, set, randomSet);
			distributor.addFeedback(IndividualType.RANDOM, isAdded);
		}
		return topResultSet;
	}

	private TopResultSet runMutations(TopResultSet topResultSet, WordSet set, GenerationDistributor distributor) {
		for (int i = 0 ; i < distributor.getAmount(IndividualType.MUTATION) ; i++) {
			WordSet randomSet = topResultSet.getRandom();
			int randomWordIndexToRemove = randomInt(getSubsetSize());
			OrderedWordSet newWordSet = new OrderedWordSet();
			for (int wordIndex = 0 ; wordIndex < getSubsetSize() ; wordIndex++ ) {
				if (wordIndex != randomWordIndexToRemove) {
					newWordSet.addWord(randomSet.get(wordIndex));
				}
			}
			int randomWordIndexToAdd = randomInt(set.size());
			Word randomWord = set.get(randomWordIndexToAdd);
			newWordSet.addWord(randomWord);
			boolean isAdded = addIfInTop(topResultSet, set, newWordSet);
			distributor.addFeedback(IndividualType.MUTATION, isAdded);
		}
		return topResultSet;
	}
	
	private TopResultSet runRecombinations(TopResultSet topResultSet, WordSet set, GenerationDistributor distributor) {
		for (int i = 0 ; i < distributor.getAmount(IndividualType.RECOMBINATION) ; i++) {
			WordSet randomSetA = topResultSet.getRandom();
			WordSet randomSetB = topResultSet.getRandom();
			WordSet recombinedSet = recombine(randomSetA,randomSetB);
			if (recombinedSet != null) {
				boolean isAdded = addIfInTop(topResultSet, set, recombinedSet);
				distributor.addFeedback(IndividualType.RECOMBINATION, isAdded);
			} else {
				distributor.addFeedback(IndividualType.RECOMBINATION, false);
			}
		}
		return topResultSet;
	}

	private boolean addIfInTop(TopResultSet topResultSet, WordSet totalSet, WordSet newSubset) {
		if (isCorrectSubset(newSubset)) {
			double value = c_valuator.value(totalSet, newSubset);
			return topResultSet.addIfInTop(value, newSubset);
		}
		return false;
	}
	
	private boolean isCorrectSubset(WordSet subset) {
		if (subset.size() != getSubsetSize()) {
			return false;
		}
		List<Word> wordsInSubset = new ArrayList<Word>();
		for (Word word : subset) {
			if (wordsInSubset.contains(word)) {
				return false;
			}
			wordsInSubset.add(word);
		}
		return true;
	}

	private WordSet recombine(WordSet setA, WordSet setB) {
		// to ensure that it is a recombination, and not a copy, 
		// take from both sets 1 word. Not possible if subsetsize 
		// is 1 or if sets are equivalent.
		if (getSubsetSize() == 1 || setA.isEquivalent(setB)) {
			return null;
		}
		OrderedWordSet recombinedWordSet = new OrderedWordSet();
		recombinedWordSet.addWord(setA.get(randomInt(getSubsetSize())));
		// make sure it has words from both sets
		while (recombinedWordSet.size() < 2) {
			Word randomWord = setB.get(randomInt(getSubsetSize()));
			if (!recombinedWordSet.contains(randomWord)) {
				recombinedWordSet.addWord(randomWord);
			}
		}
		while (recombinedWordSet.size() < getSubsetSize()) {
			WordSet randomSet;
			if (randomBool()) {
				randomSet = setA;
			} else {
				randomSet = setB;
			}
			Word randomWord = randomSet.get(randomInt(getSubsetSize()));
			if (!recombinedWordSet.contains(randomWord)) {
				recombinedWordSet.addWord(randomWord);
			}
		}
		return recombinedWordSet;
	}

	private WordSet getRandomSubset(WordSet set) {
		OrderedWordSet randomWordSet = new OrderedWordSet();
		for (int i = 0 ; i < getSubsetSize() ; i++) {
			int randomIndex = randomInt(set.size());
			randomWordSet.addWord(set.get(randomIndex));
		}
		return randomWordSet;
	}
	
	@Override
	public String getCode() {
		return c_valuator.getCode() + "g" + getAmountCode();
	}

	private String getAmountCode() {
		int totalChecks = c_config.generations * (c_config.mutations + c_config.newSets + c_config.recombinations);
		String amountCode = "";
		if (totalChecks < 1000) {
			amountCode = Integer.toString(totalChecks);
		} else if (1000 <= totalChecks && totalChecks < 1000000) {
			amountCode = (totalChecks/1000) + "K";
		} else if (1000000 <= totalChecks && totalChecks < 1000000000) {
			amountCode = (totalChecks/1000000) + "M";
		} else if (1000000000 <= totalChecks) {
			amountCode = (totalChecks/1000000000) + "B";
		}
		return amountCode;
	}

	@Override
	public String getDescription() {
		return c_valuator.getDescription();
	}

	private int randomInt(int max) {
		Random rand = new Random();
		return rand.nextInt(max);
	}
	
	private boolean randomBool() {
		Random rand = new Random();
		return rand.nextBoolean();
	}

	@Override
	public SortOrder getSortOrderForBest() {
		return c_valuator.getSortOrderForBest();
	}
}
