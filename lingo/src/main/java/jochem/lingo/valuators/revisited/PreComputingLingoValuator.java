package jochem.lingo.valuators.revisited;

import java.util.HashMap;
import java.util.Map;

import jochem.lingo.valuations.WordValuation;
import bram.lingo.words.Word;
import bram.lingo.words.wordSets.WordSet;

public abstract class PreComputingLingoValuator extends LingoValuator {

    protected WordSet c_totalWordSet;
    protected Map<Word, Map<Word, Integer>> c_valuations;
    
    public PreComputingLingoValuator(WordSet wordSet) {
        c_totalWordSet = wordSet;

        c_valuations = new HashMap<Word, Map<Word, Integer>>();
        for (Word chosenWord : c_totalWordSet) {
            Map<Word, Integer> subBeoordelingen = new HashMap<Word, Integer>();
            for (Word correctWord : c_totalWordSet) {
                subBeoordelingen.put(correctWord, WordValuation.toInteger(LingoValuator.getWordValuation(chosenWord, correctWord)));
            }
            c_valuations.put(chosenWord, subBeoordelingen);
        }
    }
}
