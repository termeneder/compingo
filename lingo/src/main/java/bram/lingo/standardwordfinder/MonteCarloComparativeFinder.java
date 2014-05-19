package bram.lingo.standardwordfinder;

import java.util.Random;

import bram.lingo.standardwordfinder.valuator.WordSetValuator;
import bram.lingo.words.wordSets.OrderedWordSet;
import bram.lingo.words.wordSets.WordSet;

public class MonteCarloComparativeFinder extends StandardWordSetFinder {

	private WordSetValuator c_valuator;
	
	private SortOrder c_order;
	private int c_iterations;
	
	public MonteCarloComparativeFinder(
			WordSetValuator valuator,
			SortOrder order,
			int iterations) {
		c_valuator = valuator;
		c_order = order;
		c_iterations = iterations;
	}
	
	public OptimalWordSets findOptimal(WordSet set) {
		
		OptimalWordSets optimalWordSets = new OptimalWordSets(c_order);
		if (set.size() < getSubsetSize()) {
			return optimalWordSets;
		}
		for (int iteration = 0 ; iteration < c_iterations ; iteration++) {
			WordSet subset = getRandomSubset(set);
			double value = c_valuator.value(set, subset);
			optimalWordSets.tryNewWordSet(subset, value);
		}
		return optimalWordSets;
	}

	private WordSet getRandomSubset(WordSet set) {
		OrderedWordSet randomWordSet = new OrderedWordSet();
		Random rand = new Random();
		for (int i = 0 ; i < getSubsetSize() ; i++) {
			int randomIndex = rand.nextInt(set.size());
			randomWordSet.addWord(set.get(randomIndex));
		}
		return randomWordSet;
	}

	@Override
	public String getCode() {
		String amountCode = "";
		if (c_iterations < 1000) {
			amountCode = Integer.toString(c_iterations);
		} else if (1000 <= c_iterations && c_iterations < 1000000) {
			amountCode = (c_iterations/1000) + "K";
		} else if (1000000 <= c_iterations && c_iterations < 1000000000) {
			amountCode = (c_iterations/1000000) + "M";
		} else if (1000000000 <= c_iterations) {
			amountCode = (c_iterations/1000000000) + "B";
		}
		return c_valuator.getCode() + "mc" + amountCode;
	}

	@Override
	public String getDescription() {
		return c_valuator.getDescription();
	}


}
