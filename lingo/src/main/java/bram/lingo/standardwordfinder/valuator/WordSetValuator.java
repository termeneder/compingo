package bram.lingo.standardwordfinder.valuator;

import bram.lingo.standardwordfinder.SortOrder;
import bram.lingo.words.wordSets.WordSet;

public interface WordSetValuator {

	
	public double value(WordSet totalSet, WordSet subset);

	public String getDescription();

	public String getCode();

	public SortOrder getSortOrderForBest();
	
	
}
