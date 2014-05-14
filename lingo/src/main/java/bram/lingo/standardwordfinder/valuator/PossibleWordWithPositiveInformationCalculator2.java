package bram.lingo.standardwordfinder.valuator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import static bram.lingo.utils.Maths.choose;
import static bram.lingo.utils.Maths.pow;
import bram.lingo.words.Alphabet;
import bram.lingo.words.Letter;
// FIXME doesn't work
public class PossibleWordWithPositiveInformationCalculator2 {

	private final WordInformation c_wordInformation;
	
	public PossibleWordWithPositiveInformationCalculator2(WordInformation wordInformation) {
		c_wordInformation = wordInformation;
	}

	public long calculate() {
		long amountOfUnknownPositions = c_wordInformation.getWordLength() - c_wordInformation.getAmountOfLettersOnCorrectSpot();
		long[] amountOfSimilarLettersWithUnknownSpot = getAmountOfSimilarLettersWithUnknownSpot();
		long count = count(amountOfUnknownPositions, amountOfSimilarLettersWithUnknownSpot);
		return count;
	}

	/**
	 * Count naively assumes that the known letters do not occur in the alphabet.
	 * X being some letter in the alphabet, Xa and aX both give 27 possible answers, so
	 * a 2 letter word with at least 1 a gives 27+27=54 possible answers.
	 * Of course aa is in both answers.
	 */
	private long countNaively(long amountOfUnknownPositions,
			long[] amountOfSimilarLettersWithUnknownSpot) {
		long remainingUnknownPositions = amountOfUnknownPositions;
		long count = 1;
		for (int amountOfSimilarLetters = 1 ; amountOfSimilarLetters < amountOfSimilarLettersWithUnknownSpot.length ; amountOfSimilarLetters++) {
			for (int similarLetterSet = 0 ; similarLetterSet < amountOfSimilarLettersWithUnknownSpot[amountOfSimilarLetters] ; similarLetterSet++) {
				count *= choose(remainingUnknownPositions, amountOfSimilarLetters);
				remainingUnknownPositions -= amountOfSimilarLetters;
			}
		}
		count *= pow(Alphabet.SIZE, remainingUnknownPositions);
		return count;
	}

	private long count(long amountOfUnknownPositions,
			long[] amountOfSimilarLettersWithUnknownSpot) {
		long naiveCount = countNaively(amountOfUnknownPositions, amountOfSimilarLettersWithUnknownSpot);
		long countDoubles = countDoubles(amountOfUnknownPositions, amountOfSimilarLettersWithUnknownSpot);
		return naiveCount - countDoubles;
	}
	
	private long countDoubles(long amountOfUnknownPositions,
			long[] amountOfSimilarLettersWithUnknownSpot) {
		long totallyUnknownLetters = countTotallyUnknownLetters(amountOfUnknownPositions, amountOfSimilarLettersWithUnknownSpot);
		if (totallyUnknownLetters == 0 || totallyUnknownLetters == amountOfUnknownPositions) {
			return 0;
		}
		long sumOfDoubles = 0;
		for (int i = 1 ; i < amountOfSimilarLettersWithUnknownSpot.length ; i++) {
			if (0 < amountOfSimilarLettersWithUnknownSpot[i]) {
				long[] shiftedAmount = shiftAmount(amountOfSimilarLettersWithUnknownSpot, i);
				long countShifted = count(amountOfUnknownPositions, shiftedAmount);
				long amountOfLettersThatCanBeShiftedThisWay = amountOfSimilarLettersWithUnknownSpot[i];
				sumOfDoubles += countShifted * amountOfLettersThatCanBeShiftedThisWay;
			}
		}
		return sumOfDoubles;
	}

	private long[] shiftAmount(long[] amounts,
			int spotToShift) {
		long[] shiftedAmounts = new long[amounts.length];
		for (int i = 0 ; i < amounts.length ; i++) {
			if (i == spotToShift) {
				shiftedAmounts[i] = amounts[i]-1;
			} else if (i == spotToShift + 1) {
				shiftedAmounts[i] = amounts[i]+1;
			} else {
				shiftedAmounts[i] = amounts[i];
			}
		}
		return shiftedAmounts;
	}

	private long countTotallyUnknownLetters(long amountOfUnknownPositions,
			long[] amountOfSimilarLettersWithUnknownSpot) {
		long totallyUnknownLetters = amountOfUnknownPositions;
		for (int i = 1 ; i < amountOfSimilarLettersWithUnknownSpot.length ; i++) {
			totallyUnknownLetters -= i * amountOfSimilarLettersWithUnknownSpot[i];
		}
		return totallyUnknownLetters;
	}
	
	private long[] getAmountOfSimilarLettersWithUnknownSpot() {
		long[] amountOfSimilarLettersWithUnknownSpot = new long[c_wordInformation.getWordLength()+1];
		
		for (Entry<Letter, Integer> entry : c_wordInformation.getMinAmountOfLettersInWord().entrySet()) {
			Letter letter = entry.getKey();
			int amountOfLetters = entry.getValue();
			for (Letter knownLetter : c_wordInformation.getPositionContainsLetter().values()) {
				if (knownLetter == letter) {
					amountOfLetters--;
				}
			}
			if (0 < amountOfLetters) {
				amountOfSimilarLettersWithUnknownSpot[amountOfLetters]++;
			}
		}
		return amountOfSimilarLettersWithUnknownSpot;
	}

	
	

}
