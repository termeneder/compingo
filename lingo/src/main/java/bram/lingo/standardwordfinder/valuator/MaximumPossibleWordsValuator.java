package bram.lingo.standardwordfinder.valuator;

import bram.lingo.words.Word;
import bram.lingo.words.wordSets.WordSet;

/**
 * Counts the total amount of letters that are correct after asking the n words
 * 
 *
 */
public class MaximumPossibleWordsValuator implements WordSetValuator {

	public double value(WordSet totalSet, WordSet subset) {
		long highestScore = 0;
		for (Word word : totalSet) {
			long wordScore = calculateWordScore(word, subset);
			highestScore = Math.max(highestScore, wordScore);
		}
		return (double) highestScore;
	}

	private long calculateWordScore(Word word, WordSet subset) {
		WordInformation info = new WordInformation(word);
		for (Word wordInSubset : subset) {
			info.addProbeWord(wordInSubset);
			
		}
		long wordScore = (long)info.getPossibleWords();
		return wordScore;
	}
	
	
	
}
