package jochem.lingo.valuators.revisited;


import bram.lingo.standardwordfinder.SortOrder;
import bram.lingo.words.Word;
import bram.lingo.words.wordSets.WordSet;

public class MinCorrectCountValuator extends PreComputingLingoValuator {

    public MinCorrectCountValuator(WordSet w) {
        super(w);
    }

	@Override
	public double value(WordSet totalSet, WordSet subset) {
		
		int worstCase = Integer.MAX_VALUE;
        for (Word woord : c_totalWordSet) {
            int correct = 0;
            for (int i = 0; i < woord.length(); i++) {
                for (Word chosenWord : subset) {
                    if (woord.getLetter(i) == chosenWord.getLetter(i)) {
                        correct++;
                        break;
                    }
                }
            }
            if (correct < worstCase) {
                worstCase = correct;
            }
        }
        return (double) worstCase;
	}

	@Override
	public String getDescription() {
		
		return "??";
	}

	@Override
	public String getCode() {
		return "??";
	}

	@Override
	public SortOrder getSortOrderForBest() {
		return SortOrder.ASC;
	}
}
