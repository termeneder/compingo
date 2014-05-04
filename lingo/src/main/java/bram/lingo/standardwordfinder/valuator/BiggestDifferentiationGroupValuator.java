package bram.lingo.standardwordfinder.valuator;

import bram.lingo.words.wordSets.WordSet;

/**
 * All words are grouped in groups that have 
 * 
 *
 */
public class BiggestDifferentiationGroupValuator implements WordSetValuator {

	public double value(WordSet totalSet, WordSet subset) {
		int biggest = 0;
		Differentiation differentiation = new Differentiation(totalSet, subset);
		for (WordSet set : differentiation.getWordSetCollection()) {
			biggest = Math.max(biggest, set.size());
		}
		return (double) biggest;
	}



	
	
	
}
