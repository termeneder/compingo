package bram.lingo.standardwordfinder;

import bram.lingo.standardwordfinder.OptimalWordSets.SortOrder;
import bram.lingo.standardwordfinder.valuator.WordSetValuator;
import bram.lingo.words.wordSets.WordSet;
import bram.lingo.words.wordSets.WordSubsetIterable;

public class BruteForceComparativeFinder extends StandardWordSetFinder {

	private WordSetValuator c_valuator;
	
	private SortOrder c_order;
	
	public BruteForceComparativeFinder(
			WordSetValuator valuator,
			int subsetSize, 
			SortOrder order) {
		c_valuator = valuator;
		setSubsetSize(subsetSize);
		c_order = order;
	}
	
	public OptimalWordSets findOptimal(WordSet set) {
		WordSubsetIterable allSubsets = new WordSubsetIterable(set, getSubsetSize());
		OptimalWordSets optimalWordSets = new OptimalWordSets(c_order);
		for (WordSet subset : allSubsets) {
			double value = c_valuator.value(set, subset);
			optimalWordSets.tryNewWordSet(subset, value);

		}
		
		return optimalWordSets;
	}

	@Override
	public String getCode() {
		return c_valuator.getCode();
	}

	@Override
	public String getDescription() {
		return c_valuator.getDescription();
	}


}
