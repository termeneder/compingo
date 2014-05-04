package bram.lingo.standardwordfinder.valuator;

import bram.lingo.words.wordSets.WordSet;

/**
 * All words are grouped in groups that have 
 * 
 *
 */
public class AmountOfDifferentiationGroupsValuator implements WordSetValuator {

	public double value(WordSet totalSet, WordSet subset) {
		
		Differentiation differentiation = new Differentiation(totalSet, subset);
		return (double) differentiation.getWordSetCollection().size();
	}



	
	
	
}
