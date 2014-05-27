package bram.lingo.standardwordfinder.valuator;

import bram.lingo.lingo.LingoComparator;
import bram.lingo.lingo.LingoCompareValue;
import bram.lingo.standardwordfinder.SortOrder;
import bram.lingo.words.Word;
import bram.lingo.words.wordSets.WordSet;

/**
 * Counts the total amount of letters that are correct after asking the n words
 * 
 *
 */
public class CorrectLettersValuator implements WordSetValuator {

	public double value(WordSet totalSet, WordSet subset) {
		double totalScore = 0;
		for (Word word : totalSet) {
			totalScore += calculateWordScore(word, subset);
		}
		double averageScore = totalScore/(double)totalSet.size();
		return averageScore;
	}

	private double calculateWordScore(Word word, WordSet subset) {
		LingoComparator comparator = new LingoComparator(word);
		boolean[] correctLetterList = new boolean[word.length()]; 
		for (Word wordInSubset : subset) {
			LingoCompareValue[] values = comparator.compare(wordInSubset);
			for (int i = 0 ; i < values.length ; i++) {
				if (values[i] == LingoCompareValue.Correct) {
					correctLetterList[i] = true;
				}
			}
		}
		double wordScore = 0;
		for (boolean correctLetter : correctLetterList) {
			if (correctLetter) {
				wordScore += 1;
			}
		}
		return wordScore;
	}
	
	@Override
	public String getDescription() {
		return "Optimise correct letters";
	}

	@Override
	public String getCode() {
		return "A1";
	}

	@Override
	public SortOrder getSortOrderForBest() {
		return SortOrder.ASC;
	}

	
	
	
}
