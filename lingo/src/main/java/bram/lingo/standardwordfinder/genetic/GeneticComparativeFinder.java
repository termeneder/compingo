package bram.lingo.standardwordfinder.genetic;

import bram.lingo.standardwordfinder.OptimalWordSets;
import bram.lingo.standardwordfinder.StandardWordSetFinder;
import bram.lingo.standardwordfinder.OptimalWordSets.SortOrder;
import bram.lingo.standardwordfinder.valuator.WordSetValuator;
import bram.lingo.words.wordSets.WordSet;
import bram.lingo.words.wordSets.WordSubsetIterable;

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
		return c_valuator.getCode() + "g";
	}

	@Override
	public String getDescription() {
		return c_valuator.getDescription();
	}


}
