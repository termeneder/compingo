package bram.lingo.standardwordfinder;

import bram.lingo.standardwordfinder.valuator.WordSetValuator;
import bram.lingo.words.wordSets.WordSet;
import bram.lingo.words.wordSets.WordSubsetIterable;

public class ExhaustiveComparativeFinder extends StandardWordSetFinder {

	private WordSetValuator c_valuator;
	
	private SortOrder c_order;
	
	public ExhaustiveComparativeFinder(
			WordSetValuator valuator,
			SortOrder order) {
		c_valuator = valuator;
		c_order = order;
	}
	
	public ExhaustiveComparativeFinder(
			WordSetValuator valuator,
			Select select) {
		c_valuator = valuator;
		c_order = SortOrder.getSortOrderFromSelectAndBest(select, getSortOrderForBest());
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

	@Override
	public SortOrder getSortOrderForBest() {
		return c_valuator.getSortOrderForBest();
	}


}
