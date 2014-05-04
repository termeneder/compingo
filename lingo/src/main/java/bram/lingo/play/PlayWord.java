package bram.lingo.play;

import bram.lingo.input.Input;
import bram.lingo.lingo.LingoComparator;
import bram.lingo.lingo.LingoCompareValue;
import bram.lingo.words.Letter;
import bram.lingo.words.Word;
import bram.lingo.words.wordSets.WordSet;

public class PlayWord {

	public static final int AMOUNT_OF_TRIES = 5;
	
	private final Word c_word;
	private final int c_wordLength;
	private final WordSet c_validWordSet;
	private LingoComparator c_comparator;
	private Letter[] c_correctLetters;
	
	public PlayWord(Word word, WordSet validWordSet) {
		c_word = word;
		c_wordLength = word.length();
		c_validWordSet = validWordSet;
		c_comparator = new LingoComparator(word);
		c_correctLetters = new Letter[c_wordLength];
		c_correctLetters[0] = word.getLetter(0);
	}
	
	public boolean play() {
		printHeader();
		int tries = 0;
		boolean guessedWord = false;
		while (tries < AMOUNT_OF_TRIES && ! guessedWord) {
			guessedWord = guess();
			tries++;
		}
		if (guessedWord) {
			System.out.println("Geraden!");
			return true;
		} else {
			System.out.println("Niet geraden. Het woord was: " + c_word);
			return false;
		}
	}

	private boolean guess() {
		printCurrentState();
		String input = Input.getLine();
		Word inputWord = new Word(input);
		if (isValidInput(inputWord)) {
			LingoCompareValue[] compareValues = c_comparator.compare(inputWord);
			printCompareValues(compareValues);
			updateCorrectLetters(compareValues, inputWord);
			return wordIsGuessed(compareValues);
		} else {
			System.out.println("Invalid input");
			return false;
		}
		
	}

	private boolean wordIsGuessed(LingoCompareValue[] compareValues) {
		for (LingoCompareValue value : compareValues) {
			if (value != LingoCompareValue.Correct) {
				return false;
			}
		}
		return true;
	}

	private void updateCorrectLetters(LingoCompareValue[] values, Word inputWord) {
		for (int i = 0 ; i < c_wordLength ; i++) {
			if (values[i] == LingoCompareValue.Correct) {
				c_correctLetters[i] = inputWord.getLetter(i);
			}
		}
	}
	
	private void printCompareValues(LingoCompareValue[] compareValues) {
		String printString = "  ";
		for (LingoCompareValue value : compareValues) {
			switch (value) {
			case Correct:
				printString += "O";
				break;
			case Miss: 
				printString += ".";
				break;
			case Incorrect: 
				printString += " ";
				break;
			}
		}
		System.out.println(printString);
	}

	private boolean isValidInput(Word inputWord) {
		return c_validWordSet.contains(inputWord);
	}
	
	private void printCurrentState() {
		String currentState = "  ";
		for (Letter letter : c_correctLetters) {
			if (letter != null) {
				currentState += letter;
			} else {
				currentState += "_";
			}
		}
		System.out.println(currentState);
		
	}

	private void printHeader() {
		Letter firstLetter = c_word.getLetter(0);
		System.out.println(c_wordLength + " letters, begint met een " + firstLetter);
	}
	
}
