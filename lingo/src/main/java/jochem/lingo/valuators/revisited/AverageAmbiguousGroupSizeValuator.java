package jochem.lingo.valuators.revisited;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import bram.lingo.standardwordfinder.SortOrder;
import bram.lingo.words.Word;
import bram.lingo.words.wordSets.WordSet;

public class AverageAmbiguousGroupSizeValuator extends PreComputingLingoValuator {

    public AverageAmbiguousGroupSizeValuator(WordSet totalWordSet) {
		super(totalWordSet);
	}

    @Override
	public double value(WordSet totalSet, WordSet subset) {
        Map<ArrayList<Integer>, Integer> ambiguity = new HashMap<ArrayList<Integer>, Integer>();
        for (Word word : c_totalWordSet) {
            ArrayList<Integer> valuationsOfChosenWords = new ArrayList<Integer>();
            for (Word chosenWord : subset)
                valuationsOfChosenWords.add(c_valuations.get(chosenWord).get(word));
            if (!ambiguity.containsKey(valuationsOfChosenWords)) {
                ambiguity.put(valuationsOfChosenWords, 1);
            } else {
                ambiguity.put(valuationsOfChosenWords, ambiguity.get(valuationsOfChosenWords) + 1);
            }
        }

        int ambiguityCount = 0;
        for (ArrayList<Integer> valuations : ambiguity.keySet()) {
            ambiguityCount += ambiguity.get(valuations) * ambiguity.get(valuations);
        }

        return (double) ambiguityCount / (double) c_totalWordSet.size();
    }


	@Override
	public String getDescription() {
		return "Minimise average ambiguous neighbours";
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
