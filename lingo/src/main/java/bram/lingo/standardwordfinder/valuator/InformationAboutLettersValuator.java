package bram.lingo.standardwordfinder.valuator;

import bram.lingo.standardwordfinder.SortOrder;
import bram.lingo.words.Word;
import bram.lingo.words.wordSets.WordSet;

/**
 * Given a word set A and a subset B:
 * calculate for each word W in A the value X such that X is the amount of letters that there is information about
 * 
 * For example given B = {abcde, fghij}
 * and W = acixy
 * results in an X of 3 (information is known about a,c and i)
 * 
 * return the sum of all X's
 * 
 * 
 */
public class InformationAboutLettersValuator implements WordSetValuator {

	public double value(WordSet totalSet, WordSet subset) {
		double totalScore = 0;
		for (Word word : totalSet) {
			totalScore += calculateWordScore(word, subset);
		}
		double averageScore = totalScore/(double)totalSet.size();
		return averageScore;
	}

	private double calculateWordScore(Word word, WordSet subset) {
		WordInformation information = new WordInformation(word);
		for (Word wordInSubset : subset) {
			information.addProbeWord(wordInSubset);
		}
		return (double) information.getAmountOfLettersKnownToBeInWord();
	}

	@Override
	public String getDescription() {
		return "Optimise amount of letters";
	}

	@Override
	public String getCode() {
		return "B1";
	}

	@Override
	public SortOrder getSortOrderForBest() {
		return SortOrder.ASC;
	}
	
	
}
