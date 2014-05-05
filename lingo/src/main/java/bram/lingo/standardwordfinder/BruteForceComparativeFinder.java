package bram.lingo.standardwordfinder;

import bram.lingo.standardwordfinder.valuator.WordSetValuator;
import bram.lingo.words.wordSets.WordSet;
import bram.lingo.words.wordSets.WordSubsetIterable;

public class BruteForceComparativeFinder implements StandardWordSetFinder {

	private int c_amountOfWordsInStandardSet;
	private WordSetValuator c_valuator;
	public enum SortOrder {ASC, DESC}
	private SortOrder c_order;
	
	public BruteForceComparativeFinder(
			WordSetValuator valuator, 
			int amountOfWordsInStandardSet, 
			SortOrder order) {
		c_valuator = valuator;
		c_amountOfWordsInStandardSet = amountOfWordsInStandardSet;
		c_order = order;
	}
	
	public BruteForceComparativeFinder(
			WordSetValuator valuator, 
			int amountOfWordsInStandardSet) {
		c_valuator = valuator;
		c_amountOfWordsInStandardSet = amountOfWordsInStandardSet;
		c_order = SortOrder.ASC;
	}
	
	
	
	public OptimalWordSets findOptimal(WordSet set) {
		WordSubsetIterable allSubsets = new WordSubsetIterable(set, c_amountOfWordsInStandardSet);
		OptimalWordSets optimalWordSets = new OptimalWordSets(c_order);
		for (WordSet subset : allSubsets) {
			double value = c_valuator.value(set, subset);
			optimalWordSets.tryNewWordSet(subset, value);

		}
		
		return optimalWordSets;
	}


}
