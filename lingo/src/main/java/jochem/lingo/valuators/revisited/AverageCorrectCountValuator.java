package jochem.lingo.valuators.revisited;

import bram.lingo.words.Word;
import bram.lingo.words.wordSets.WordSet;

public class AverageCorrectCountValuator extends PreComputingLingoValuator {

    public AverageCorrectCountValuator(WordSet w) {
        super(w);
    }

	@Override
	public double value(WordSet totalSet, WordSet subset) {
        int result = 0;
        for (Word woord : c_totalWordSet) {
            for (int i = 0; i < woord.length(); i++) {
                for (Word chosenWord : subset) {
                    if (woord.getLetter(i) == chosenWord.getLetter(i)) {
                        result++;
                        break;
                    }
                }
            }
        }

        return (double) result / (double) c_totalWordSet.size();
	}

	@Override
	public String getDescription() {
		
		return "Jochems average correct count";
	}

	@Override
	public String getCode() {
		return "A4";
	}
}
