package bram.lingo.standardwordfinder.valuator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


import bram.lingo.words.Letter;

class PossibleWordCalculator {

	WordInformation c_wordInformation;
	
	public PossibleWordCalculator(WordInformation wordInformation) {
		c_wordInformation = wordInformation;
	}

	public long calculate() {
		Map<Letter, Integer> minAmountOfLetters = c_wordInformation.getMinAmountOfLettersInWord();
		Map<Letter, Integer> maxAmountOfLetters = c_wordInformation.getmaxAmountOfLettersInWord();
		long result = calculateFromLetter(0, minAmountOfLetters, maxAmountOfLetters);
		return result;
	}

	private long calculateFromLetter(
			int letterIndex, 
			Map<Letter, Integer> minAmountOfLetters, 
			Map<Letter, Integer> maxAmountOfLetters) {
		if (c_wordInformation.getWordLength() <= letterIndex) {
			if (noNeccessaryLettersLeft(minAmountOfLetters)) {
				return 1;
			} else {
				return 0;
			}
		}
		long sum = 0;
		if (c_wordInformation.getPositionContainsLetter().containsKey(letterIndex)) {
			Letter letterAtSpot = c_wordInformation.getPositionContainsLetter().get(letterIndex);
			sum = calculateWithSpecificLetterAtSpot(letterIndex, letterAtSpot, minAmountOfLetters, maxAmountOfLetters);
		} else {
			long nonSpecialLettersForSpot = getNonSpecialLettersForSpot(letterIndex, minAmountOfLetters, maxAmountOfLetters);
			sum += nonSpecialLettersForSpot * calculateFromLetter(letterIndex+1, minAmountOfLetters, maxAmountOfLetters);
			List<Letter> possibleSpecialLetters = getPossibleSpecialLetters(letterIndex, minAmountOfLetters, maxAmountOfLetters);
			for (Letter specialLetter : possibleSpecialLetters) {
				long valueWithSpecificLetterAtSpot = calculateWithSpecificLetterAtSpot(letterIndex, specialLetter, minAmountOfLetters, maxAmountOfLetters);
				sum += valueWithSpecificLetterAtSpot;
			}
		}
		return sum;
	}

	private boolean noNeccessaryLettersLeft(
			Map<Letter, Integer> minAmountOfLetters) {
		for (Integer value : minAmountOfLetters.values()) {
			if (value > 0) {
				return false;
			}
		}
		return true;
	}

	private long getNonSpecialLettersForSpot(int letterIndex,
			Map<Letter, Integer> minAmountOfLetters,
			Map<Letter, Integer> maxAmountOfLetters) {
		List<Letter> specialLetters = new ArrayList<Letter>();
		specialLetters = addLettersToList(specialLetters, minAmountOfLetters.keySet());
		specialLetters = addLettersToList(specialLetters, maxAmountOfLetters.keySet());
		specialLetters = addLettersToList(specialLetters, c_wordInformation.getPositionDoesNotContainLetter().get(letterIndex));
		int totalAmountOfLetters = 27;
		return totalAmountOfLetters - specialLetters.size();
	}

	private List<Letter> getPossibleSpecialLetters(
			int letterIndex, 
			Map<Letter, Integer> minAmountOfLetters, 
			Map<Letter, Integer> maxAmountOfLetters) {
		List<Letter> possibleSpecialLetters = new ArrayList<Letter>();
		for (Letter specialLetter : minAmountOfLetters.keySet()) {
			possibleSpecialLetters.add(specialLetter);
		}
		for (Letter specialLetter : maxAmountOfLetters.keySet()) {
			possibleSpecialLetters.add(specialLetter);
		}
		List<Letter> forbiddenLettersForPosition = c_wordInformation.getPositionDoesNotContainLetter().get(letterIndex);
		
		List<Letter> allowedLetters = new ArrayList<Letter>();
		for (Letter specialLetter : possibleSpecialLetters) {
			boolean isForbidden = false;
			if (forbiddenLettersForPosition != null &&
					forbiddenLettersForPosition.contains(specialLetter)) {
				isForbidden = true;
			} else if (maxAmountOfLetters.containsKey(specialLetter) && maxAmountOfLetters.get(specialLetter) == 0) {
				isForbidden = true;
			}
			if (!isForbidden) {
				allowedLetters.add(specialLetter);
			}
		}
		return allowedLetters;
	}
	
	private List<Letter> addLettersToList(List<Letter> letterList,
			Collection<Letter> newLetters) {
		if (newLetters == null) {
			return letterList;
		}
		for (Letter letter: newLetters) {
			if (!letterList.contains(letter)) {
				letterList.add(letter);
			}
		}
		return letterList;
	}

	private long calculateWithSpecificLetterAtSpot(
			int letterIndex, 
			Letter letter,
			Map<Letter, Integer> oldMinAmountOfLetters, 
			Map<Letter, Integer> oldMaxAmountOfLetters) {
		Map<Letter, Integer> newMinAmountOfLetters = copyMap(oldMinAmountOfLetters); 
		Map<Letter, Integer> newMaxAmountOfLetters = copyMap(oldMaxAmountOfLetters);
		if (newMinAmountOfLetters.containsKey(letter)) {
			Integer newValue = newMinAmountOfLetters.get(letter) - 1;
			if (newValue == 0) {
				newMinAmountOfLetters.remove(letter);
			} else {
				newMinAmountOfLetters.put(letter, newValue);
			}
		}
		
		if (newMaxAmountOfLetters.containsKey(letter)) {
			Integer newValue = newMaxAmountOfLetters.get(letter) - 1;
			if (newValue < 0) {
				return 0;
			}
			newMaxAmountOfLetters.put(letter, newValue);
		}
		return calculateFromLetter(letterIndex+1, newMinAmountOfLetters, newMaxAmountOfLetters);
	}
	
	private Map<Letter, Integer> copyMap(Map<Letter, Integer> originalMap) {
		Map<Letter, Integer> copiedMap = new HashMap<Letter, Integer>();
		for (Entry<Letter, Integer> entry : originalMap.entrySet()) {
			copiedMap.put(entry.getKey(), entry.getValue());
		}
		return copiedMap;
	}
	
}
