package bram.lingo.standardwordfinder.valuator;

import bram.lingo.words.Word;
import bram.lingo.words.wordSets.WordSet;

/**
 * Counts the total amount of letters that are correct after asking the n words
 * 
 *
 */
public class CountPossibleWordsValuator implements WordSetValuator {

	public double value(WordSet totalSet, WordSet subset) {
		long totalScore = 0;
		for (Word word : totalSet) {
			totalScore += calculateWordScore(word, subset);
		}
		double averageScore = (double)totalScore / (double) totalSet.size();
		return averageScore;
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
