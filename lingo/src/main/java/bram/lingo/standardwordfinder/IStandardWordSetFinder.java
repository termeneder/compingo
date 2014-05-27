package bram.lingo.standardwordfinder;

import bram.lingo.words.wordSets.WordSet;

public interface IStandardWordSetFinder {

	public OptimalWordSets findOptimal(WordSet set);
	
	public String getCode();
	
	public String getDescription();
	
	public int getSubsetSize();
	
	public void setSubsetSize(int subsetSize);
	
	public SortOrder getSortOrderForBest();
}
