package jochem.lingo.valuators.revisited;

import java.util.ArrayList;
import java.util.HashMap;

import bram.lingo.standardwordfinder.SortOrder;
import bram.lingo.words.Word;
import bram.lingo.words.wordSets.WordSet;


public class NonAmbiguityValuator extends PreComputingLingoValuator {

    public NonAmbiguityValuator(WordSet words) {
        super(words);
    }



	@Override
	public double value(WordSet totalSet, WordSet subset) {
        HashMap<ArrayList<Integer>, Integer> ambiguity = new HashMap<ArrayList<Integer>, Integer>();
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

        ArrayList<Integer> mostAmbiguousValuation = null;
        for (ArrayList<Integer> valuations : ambiguity.keySet()) {
            if (mostAmbiguousValuation == null || ambiguity.get(valuations) > ambiguity.get(mostAmbiguousValuation)) {
                mostAmbiguousValuation = valuations;
            }
        }

        return ambiguity.get(mostAmbiguousValuation);
	}

	@Override
	public String getDescription() {
		return "Jochems C algorithm";
	}

	@Override
	public String getCode() {
		return "C2";
	}

	@Override
	public SortOrder getSortOrderForBest() {
		return SortOrder.DESC;
	}
}
