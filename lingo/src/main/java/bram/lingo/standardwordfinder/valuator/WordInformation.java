package bram.lingo.standardwordfinder.valuator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import bram.lingo.lingo.LingoComparator;
import bram.lingo.lingo.LingoCompareValue;
import bram.lingo.words.Letter;
import bram.lingo.words.Word;

class WordInformation {
	
	private Map<Letter, Integer> c_minAmountOfLettersInWord;
	private Map<Letter, Integer> c_maxAmountOfLettersInWord;
	private Map<Integer, List<Letter>> c_positionDoesNotContainLetter;
	private Map<Integer, Letter> c_positionContainsLetter;
	private Word c_word;
	private LingoComparator c_comparator;
	
	public WordInformation(Word word) {
		c_word = word;
		c_comparator = new LingoComparator(word);
		c_minAmountOfLettersInWord = new HashMap<Letter, Integer>();
		c_maxAmountOfLettersInWord = new HashMap<Letter, Integer>();
		c_positionDoesNotContainLetter = new HashMap<Integer, List<Letter>>();
		c_positionContainsLetter = new HashMap<Integer, Letter>();
	}
	
	public void addProbeWord(Word probe) {
		LingoCompareValue[] values = c_comparator.compare(probe);
		Map<Letter, Integer> minAmountOfLettersInWord = new HashMap<Letter, Integer>();
		Map<Letter, Integer> maxAmountOfLettersInWord = new HashMap<Letter, Integer>();
		
		for (int i = 0 ; i < values.length ; i++) {
			LingoCompareValue value = values[i];
			Letter letter = probe.getLetter(i);
			if (value == LingoCompareValue.Correct) {
				minAmountOfLettersInWord = addMinAmountOfLettersInWord(minAmountOfLettersInWord, letter);
				c_positionContainsLetter.put(i, letter);
			} else if (value == LingoCompareValue.Miss) {
				minAmountOfLettersInWord = addMinAmountOfLettersInWord(minAmountOfLettersInWord, letter);
				addPositionDoesNotContainLetter(i, letter);
			} else if (value == LingoCompareValue.Incorrect) {
				maxAmountOfLettersInWord = setMaxAmountOfLettersInWord(letter, maxAmountOfLettersInWord, minAmountOfLettersInWord);
			}
		}
		updateWithMinAmount(minAmountOfLettersInWord);
		updateWithMaxAmount(maxAmountOfLettersInWord);
	}

	private Map<Letter, Integer> setMaxAmountOfLettersInWord(
			Letter letter,
			Map<Letter, Integer> maxAmountOfLettersInWord,
			Map<Letter, Integer> minAmountOfLettersInWord) {
		int newMaxAmount;
		if (minAmountOfLettersInWord.containsKey(letter)) {
			newMaxAmount = minAmountOfLettersInWord.get(letter);
		} else {
			newMaxAmount = 0;
		}
		maxAmountOfLettersInWord.put(letter, newMaxAmount);
		return maxAmountOfLettersInWord;
	}

	private void addPositionDoesNotContainLetter(int i, Letter letter) {
		if (c_positionDoesNotContainLetter.containsKey(i)) {
			List<Letter> notContainedLetters = c_positionDoesNotContainLetter.get(i);
			if (!notContainedLetters.contains(letter)) {
				notContainedLetters.add(letter);
			}
		} else {
			List<Letter> notContainedLetters = new ArrayList<Letter>();
			notContainedLetters.add(letter);
			c_positionDoesNotContainLetter.put(i, notContainedLetters);
		}
		
	}

	private void updateWithMinAmount(Map<Letter, Integer> minAmountOfLettersInWord) {
		for (Entry<Letter, Integer> entry : minAmountOfLettersInWord.entrySet()) {
			int previousValue;
			if (c_minAmountOfLettersInWord.containsKey(entry.getKey())) {
				previousValue = c_minAmountOfLettersInWord.get(entry.getKey());
			} else {
				previousValue = 0;
			}
			int currentUpdate = entry.getValue();
			int newValue = Math.max(previousValue, currentUpdate);
			c_minAmountOfLettersInWord.put(entry.getKey(), newValue);
		}
	}

	private void updateWithMaxAmount(Map<Letter, Integer> maxAmountOfLettersInWord) {
		for (Entry<Letter, Integer> entry : maxAmountOfLettersInWord.entrySet()) {
			int previousValue;
			if (c_maxAmountOfLettersInWord.containsKey(entry.getKey())) {
				previousValue = c_maxAmountOfLettersInWord.get(entry.getKey());
			} else {
				previousValue = Integer.MAX_VALUE;
			}
			int currentUpdate = entry.getValue();
			int newValue = Math.min(previousValue, currentUpdate);
			c_maxAmountOfLettersInWord.put(entry.getKey(), newValue);
		}
	}
	
	private Map<Letter, Integer> addMinAmountOfLettersInWord(
			Map<Letter, Integer> minAmountOfLettersInWord, Letter letter) {
		int currentAmount;
		if (minAmountOfLettersInWord.containsKey(letter)) {
			int previousAmount = minAmountOfLettersInWord.get(letter);
			currentAmount = previousAmount+1;
		} else {
			currentAmount = 1;
		}
		minAmountOfLettersInWord.put(letter, currentAmount);
		return minAmountOfLettersInWord;
	}
	
	public long getPossibleWords() {
		PossibleWordCalculator calculator = new PossibleWordCalculator(this);
		return calculator.calculate();
	}
	
	public long getAmountOfLettersKnownToBeInWord() {
		long amount = 0;
		for (Integer value : c_minAmountOfLettersInWord.values()) {
			amount += value;
		}
		return amount;
	}
	
	public String toString() {
		String str = "Current info about "+ c_word+"\n";
		str += "Contains at least: ";
		for (Entry<Letter, Integer> entry : c_minAmountOfLettersInWord.entrySet()) {
			str += entry.getValue() + "" + entry.getKey() + " ";
		}
		str += "\n";
		str += "Contains at most: ";
		for (Entry<Letter, Integer> entry : c_maxAmountOfLettersInWord.entrySet()) {
			str += entry.getValue() + "" + entry.getKey() + " ";
		}
		str += "\n"; 
		for (int position = 0 ; position < c_word.length() ; position++) {
			str += position+ ": ";
			if (c_positionContainsLetter.containsKey(position)) {
				Letter letter = c_positionContainsLetter.get(position);
				str += letter;
			} else if (c_positionDoesNotContainLetter.containsKey(position)) {
				List<Letter> letters = c_positionDoesNotContainLetter.get(position);
				str += "not ";
				boolean isFirst = true;
				for (Letter letter : letters) {
					if (isFirst) {
						isFirst = false;
					} else {
						str += ",";
					}
					str += letter;
				}
			}
			str += "\n";
		}
		return str;
	}
	
	Map<Letter, Integer> getMinAmountOfLettersInWord() {
		return c_minAmountOfLettersInWord;
	}
	
	Map<Letter, Integer> getmaxAmountOfLettersInWord() { 
		return c_maxAmountOfLettersInWord;
	}
	
	Map<Integer, List<Letter>> getPositionDoesNotContainLetter() {
		return c_positionDoesNotContainLetter;
	}
	
	Map<Integer, Letter> getPositionContainsLetter() {
		return c_positionContainsLetter;
	}
	
	int getWordLength() {
		return c_word.length();
	}
}