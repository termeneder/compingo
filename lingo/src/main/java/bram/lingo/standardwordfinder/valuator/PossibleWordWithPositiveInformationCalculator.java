package bram.lingo.standardwordfinder.valuator;

import java.util.List;

public class PossibleWordWithPositiveInformationCalculator {

	private final WordInformation c_wordInformation;
	
	public PossibleWordWithPositiveInformationCalculator(WordInformation wordInformation) {
		c_wordInformation = wordInformation;
	}

	public long calculate() {
		long amountOfUnknownPositions = c_wordInformation.getWordLength() - c_wordInformation.getAmountOfLettersOnCorrectSpot();
		List<Long> amountOfSimilarLettersWithUnknownSpot;
		return 0;
	}

	
	

}
