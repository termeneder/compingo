package bram.lingo.standardwordfinder.valuator;

import bram.lingo.words.Word;
import bram.lingo.words.wordSets.WordSet;


public class PositiveAveragePossibleWordsValuator implements WordSetValuator {

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
		PossibleWordWithPositiveInformationCalculator calculator = new PossibleWordWithPositiveInformationCalculator(info);
		long wordScore = calculator.calculate();
		return wordScore;
	}
	
	@Override
	public String getDescription() {
		return "Minimise average possible words with positive info";
	}

	@Override
	public String getCode() {
		return "G1";
	}
	
}
