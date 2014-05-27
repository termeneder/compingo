package bram.lingo.standardwordfinder.valuator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import bram.lingo.standardwordfinder.SortOrder;
import bram.lingo.words.Alphabet;
import bram.lingo.words.Letter;
import bram.lingo.words.Word;
import bram.lingo.words.wordSets.WordSet;


public class InformationAboutLetters3Valuator implements WordSetValuator {

	private Map<Letter, List<Integer>> c_amountOfOccurrencesForLetters;

	
	public InformationAboutLetters3Valuator(WordSet preprocessWordSet) {
		preprocess(preprocessWordSet);
	}
	
	private void preprocess(WordSet preprocessWordSet) {
		int wordLength = preprocessWordSet.get(0).length();
		initialiseMap(wordLength);
		for (Word word : preprocessWordSet) {
			preprocessWord(word);
		}
	}
	
	private void preprocessWord(Word word) {
		Map<Letter, Integer> occurrencesOfLettersInWord = getOccurrencesOfLettersInWord(word);
		for (Entry<Letter, Integer> entry : occurrencesOfLettersInWord.entrySet()) {
			Letter letter = entry.getKey();
			int occurrencesOfLetter = entry.getValue();
			List<Integer> amountOfOccurrencesOfOccurrences = c_amountOfOccurrencesForLetters.get(letter);
			int previousAmoutOfOccurrences = amountOfOccurrencesOfOccurrences.get(occurrencesOfLetter);
			int currentAmountOfOccurrences = previousAmoutOfOccurrences + 1;
			amountOfOccurrencesOfOccurrences.set(occurrencesOfLetter, currentAmountOfOccurrences);
		}

	}

	private Map<Letter, Integer> getOccurrencesOfLettersInWord(Word word) {
		Map<Letter, Integer> map = new HashMap<Letter, Integer>();
		for (Letter letterInWord : word.toLetters()) {
			int previousAmountOfOccurrences;
			if (map.containsKey(letterInWord)) {
				previousAmountOfOccurrences = map.get(letterInWord);
			} else {
				previousAmountOfOccurrences = 0;
			}
			int currentAmountOfOccurrences = previousAmountOfOccurrences+1;
			map.put(letterInWord, currentAmountOfOccurrences);
		}

		return map;
	}

	
	private void initialiseMap(int wordLength) {
		c_amountOfOccurrencesForLetters = new HashMap<Letter, List<Integer>>();
		for (Letter letter : Alphabet.getInstance()) {
			List<Integer> amountOfOccurrencesOfLetter = new ArrayList<Integer>();
			for (int occurrences = 0 ; occurrences <= wordLength ; occurrences++) {
				amountOfOccurrencesOfLetter.add(occurrences, 0);
			}
			c_amountOfOccurrencesForLetters.put(letter, amountOfOccurrencesOfLetter);
		}
	}
	
	@Override
	public double value(WordSet totalSet, WordSet subset) {
		long value = 0;
		Map<Letter, Integer> maxOccurrencesOfLetters = getMaxOccurrencesOfLetters(subset);
		for (Entry<Letter, Integer> maxOccurrenceEntry : maxOccurrencesOfLetters.entrySet()) {
			Letter letter = maxOccurrenceEntry.getKey();
			int maxOccurrencesInSubset = maxOccurrenceEntry.getValue();
			List<Integer> amountOfOccurrencesForLetter = c_amountOfOccurrencesForLetters.get(letter);
			// TODO For optimisation: move summation to preprocess too
			for (int i = 1 ; i < amountOfOccurrencesForLetter.size() ; i++) {
				value += (amountOfOccurrencesForLetter.get(i)*Math.min(i, maxOccurrencesInSubset));
			}
		}
		return (double)value/(double)totalSet.size();
	}

	private Map<Letter, Integer> getMaxOccurrencesOfLetters(WordSet set) {
		Map<Letter, Integer> maxOccurrencesOfLetters = new HashMap<Letter, Integer>();
		for (Word wordInSet : set) {
			Map<Letter, Integer> occurrencesInWord = getOccurrencesOfLettersInWord(wordInSet);
			for (Entry<Letter, Integer> occurrenceEntry : occurrencesInWord.entrySet()) {
				Letter letter = occurrenceEntry.getKey();
				int occurrenceOfLetterInCurrentWord = occurrenceEntry.getValue();
				int previousMaxOccurrence;
				if (maxOccurrencesOfLetters.containsKey(letter)) {
					previousMaxOccurrence = maxOccurrencesOfLetters.get(letter);
				} else {
					previousMaxOccurrence = 0;
				}
				int currentMaxOccurrence = Math.max(previousMaxOccurrence, occurrenceOfLetterInCurrentWord);
				maxOccurrencesOfLetters.put(letter, currentMaxOccurrence);
			}
		}
		return maxOccurrencesOfLetters;
	}

	@Override
	public String getDescription() {
		return "Optimise amount of letters";
	}

	@Override
	public String getCode() {
		return "B3";
	}

	@Override
	public SortOrder getSortOrderForBest() {
		return SortOrder.ASC;
	}
	
}