package jochem.lingo.valuators.revisited;

import java.util.Arrays;
import java.util.List;

import jochem.lingo.valuations.CharValuation;
import bram.lingo.standardwordfinder.valuator.WordSetValuator;
import bram.lingo.words.Word;


public abstract class LingoValuator implements WordSetValuator {
	
	
	
    public static List<CharValuation> getWordValuation(Word chosenWord, Word correctWord) {
        boolean[] correctWordLetterFoundInChosenWord = new boolean[correctWord.length()];
        CharValuation[] wordValuation = new CharValuation[correctWord.length()];

        for (int i = 0; i < wordValuation.length; i++)
            if (chosenWord.getLetter(i) == correctWord.getLetter(i)) {
                wordValuation[i] = CharValuation.GOED;
                correctWordLetterFoundInChosenWord[i] = true;
            } else {
                wordValuation[i] = CharValuation.FOUT;
            }

        for (int i = 0; i < chosenWord.length(); i++) {
            if (wordValuation[i] != CharValuation.GOED) {
                boolean chosenWordLetterFound = false;
                for (int j = 0; j < correctWord.length(); j++) {
                    if (correctWord.getLetter(j) == chosenWord.getLetter(i) && !correctWordLetterFoundInChosenWord[j]) {
                        chosenWordLetterFound = true;
                        correctWordLetterFoundInChosenWord[j] = true;
                        break;
                    }
                }
                if (chosenWordLetterFound) {
                    wordValuation[i] = CharValuation.ZIT_ER_IN;
                } else {
                    wordValuation[i] = CharValuation.FOUT;
                }
            }

        }
        return Arrays.asList(wordValuation);
    }

    public static void main(String[] args) {
        System.out.println(getWordValuation(new Word("oehoe"), new Word("opeen")));
    }
}
