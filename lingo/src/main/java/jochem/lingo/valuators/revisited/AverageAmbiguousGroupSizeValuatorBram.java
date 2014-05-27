package jochem.lingo.valuators.revisited;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import bram.lingo.standardwordfinder.SortOrder;
import bram.lingo.words.Word;
import bram.lingo.words.wordSets.WordSet;

public class AverageAmbiguousGroupSizeValuatorBram extends PreComputingLingoValuator {

    public AverageAmbiguousGroupSizeValuatorBram(WordSet words) {
        super(words);
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

        int ambiguousGroups = 0;
        for (ArrayList<Integer> valuations : ambiguity.keySet()) {
            if (ambiguity.get(valuations) > 0)
                ambiguousGroups++;
        }

        return (double) c_totalWordSet.size() / (double) ambiguousGroups;
	}

	@Override
	public String getDescription() {
		return "Minimise average group (Jochem)";
	}

	@Override
	public String getCode() {
		return "D2";
	}
	
	@Override
	public SortOrder getSortOrderForBest() {
		return SortOrder.DESC;
	}
}
