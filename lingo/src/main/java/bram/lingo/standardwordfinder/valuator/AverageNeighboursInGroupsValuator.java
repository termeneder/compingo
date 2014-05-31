package bram.lingo.standardwordfinder.valuator;

import bram.lingo.standardwordfinder.SortOrder;
import bram.lingo.words.wordSets.WordSet;

/**
 * All words are grouped in groups that have 
 * 
 *
 */
public class AverageNeighboursInGroupsValuator implements WordSetValuator {

	public double value(WordSet totalSet, WordSet subset) {
		Differentiation differentiation = new Differentiation(totalSet, subset);
		double totalValue = 0;
		for (WordSet set : differentiation.getWordSetCollection()) {
			totalValue += ((double)set.size()*(double)set.size());
		}
		return totalValue/(double)totalSet.size();
	}

	@Override
	public String getDescription() {
		return "Minimise average neighbours per group";
	}

	@Override
	public String getCode() {
		return "J1";
	}

	@Override
	public SortOrder getSortOrderForBest() {
		return SortOrder.DESC;
	}



	
	
	
}
