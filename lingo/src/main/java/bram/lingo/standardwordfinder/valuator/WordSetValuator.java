package bram.lingo.standardwordfinder.valuator;

import bram.lingo.words.wordSets.WordSet;

public interface WordSetValuator {

	
	public double value(WordSet totalSet, WordSet subset);
	
	
}
