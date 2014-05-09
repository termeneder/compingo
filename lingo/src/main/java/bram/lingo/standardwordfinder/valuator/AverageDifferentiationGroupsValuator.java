package bram.lingo.standardwordfinder.valuator;

import bram.lingo.words.wordSets.WordSet;

/**
 * All words are grouped in groups that have 
 * 
 *
 */
public class AverageDifferentiationGroupsValuator implements WordSetValuator {

	public double value(WordSet totalSet, WordSet subset) {
		
		Differentiation differentiation = new Differentiation(totalSet, subset);
		return (double) totalSet.size() / (double) differentiation.getWordSetCollection().size();
	}

	@Override
	public String getDescription() {
		return "Minimise average group";
	}

	@Override
	public String getCode() {
		return "D1";
	}



	
	
	
}
