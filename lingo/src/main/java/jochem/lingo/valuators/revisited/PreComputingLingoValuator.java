package jochem.lingo.valuators.revisited;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jochem.lingo.valuations.CharValuation;
import jochem.lingo.valuations.WordValuation;
import bram.lingo.standardwordfinder.valuator.WordSetValuator;
import bram.lingo.words.Letter;
import bram.lingo.words.Word;
import bram.lingo.words.wordSets.WordSet;

public abstract class PreComputingLingoValuator implements WordSetValuator {
    protected WordSet c_totalWordSet;
    protected Map<Word, Map<Word, Integer>> c_valuations;
    
    public PreComputingLingoValuator(WordSet totalWordSet) {
    	c_totalWordSet = totalWordSet;
    	c_valuations = new HashMap<Word, Map<Word, Integer>>();
    	for (Word chosenWord : c_totalWordSet) {
            Map<Word, Integer> subBeoordelingen = new HashMap<Word, Integer>();
            for (Word correctWord : c_totalWordSet) {
                subBeoordelingen.put(correctWord, WordValuation.toInteger(getCharValuation(chosenWord, correctWord)));
            }
            c_valuations.put(chosenWord, subBeoordelingen);
        }
    }

    private List<CharValuation> getCharValuation(Word chosenWord, Word correctWord) {
        List<CharValuation> result = new ArrayList<CharValuation>();
        for (int i = 0; i < chosenWord.length(); i++) {
            if (chosenWord.getLetter(i) == correctWord.getLetter(i)) {
                result.add(CharValuation.GOED);
            } else {
                if (zitErInEnMinstensEenNietGoed(correctWord, chosenWord, chosenWord.getLetter(i))) {
                    result.add(CharValuation.ZIT_ER_IN);
                } else {
                    result.add(CharValuation.FOUT);
                }
            }
        }
        return result;
    }
    
    private static boolean zitErInEnMinstensEenNietGoed(Word echtWoord, Word gekozenWoord, Letter c) {
        boolean zitErIn = false;
        boolean minstensEenNietGoed = false;
        for (int i = 0; i < echtWoord.length(); i++) {
            if (echtWoord.getLetter(i) == c) {
                zitErIn = true;
                if (echtWoord.getLetter(i) != gekozenWoord.getLetter(i))
                    minstensEenNietGoed = true;
            }
        }
        return zitErIn && minstensEenNietGoed;
    }
}
