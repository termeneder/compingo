package bram.lingo.train;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import bram.lingo.input.Input;
import bram.lingo.input.ManualInput;



public class StandardWordTupel {

	private final int c_numberOfLetters;
	private final String  c_firstLetter;
	private final String[] c_words;
	private int c_tries;
	private int c_incorrect;
	
	public StandardWordTupel(int numberOfLetters, String firstLetter, String ... words) {
		c_numberOfLetters = numberOfLetters;
		c_firstLetter = firstLetter;
		c_words = words;
		c_tries = 0;
		c_incorrect = 0;
	}
	
	public double getOdds() {
		if (c_tries == 0) {
			return 1;
		} else {
			return (double)(c_incorrect + 1) / (double)(getWordsTried() + 1); 
		}
	}
	
	public boolean train() {
		printTupelQuery();
		String[] words = askWords();
		List<String> missedWords = checkWords(words);
		printFeedback(missedWords);
		updateTries(missedWords);
		if (missedWords.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	private void updateTries(List<String> missedWords) {
		c_tries++;
		c_incorrect += missedWords.size();
		
	}

	private void printFeedback(List<String> missedWords) {
		if (missedWords.size() == 0) {
			System.out.println("Allemaal goed!");
		} else {
			String feedback = "Je miste ";
			boolean isFirst = true;
			for (String word : missedWords) {
				if (isFirst) {
					isFirst = false;
				} else {
					feedback += ", ";
				}
				feedback += word;
			}
			System.out.println(feedback);
		}
	}

	private List<String> checkWords(String[] answeredWordList) {
		List<String> missedWords = new ArrayList<String>();
		for (String correctWord : c_words) {
			boolean hasAnsweredWord = false;
			for (String answeredWord : answeredWordList) {
				if (sameWord(correctWord, answeredWord)) {
					hasAnsweredWord = true;
				}
			}
			if (!hasAnsweredWord) {
				missedWords.add(correctWord);
			}
		}
		return missedWords;
	}

	private boolean sameWord(String wordA, String wordB) {
		String wordAStandardized = wordA.toLowerCase().trim();
		String wordBStandardized = wordB.toLowerCase().trim();
		if (wordAStandardized.equals(wordBStandardized)) {
			return true;
		} else {
			return false;
		}
	}

	private String[] askWords() {
		String[] words = new String[c_words.length];
		Input input = new ManualInput();
		for (int i = 0 ; i < words.length ; i++) {
			words[i] = input.getLine();
		}
		return words;
	}


	
	private void printTupelQuery() {
		System.out.println(c_numberOfLetters + " letters, begint met " + c_firstLetter);
	}
	
	public String toString() {
		String str = StringUtils.rightPad(Integer.toString(c_numberOfLetters) + c_firstLetter, 4);
		str += StringUtils.rightPad("("+getWordsAsSingleString()+ ")", 31);
		if (getTries() > 0) {
			str += "pogingen " + StringUtils.rightPad(Integer.toString(getTries()), 4);
			str += "correct " + StringUtils.rightPad(Integer.toString(getCorrect()), 4);
			str += "incorrect " + StringUtils.rightPad(Integer.toString(getIncorrect()), 4);
			str += "factor " + StringUtils.rightPad(Double.toString(getFactor()), 4);
		} else {
			str += "nog niet gehad";
		}
		return str;
	}

	private String getWordsAsSingleString() {
		String wordsAsSingleString = "";
		boolean isFirst = true;
		for (int i = 0 ; i < c_words.length ; i++) {
			if (isFirst) {
				isFirst = false;
			} else {
				wordsAsSingleString += ", ";
			}
			wordsAsSingleString += c_words[i];
		}
		return wordsAsSingleString;
	}

	private double getFactor() {
		if (c_tries == 0) {
			return 0;
		}
		return (double) getCorrect() / (double) (getWordsTried());
	}

	public int getIncorrect() {
		return c_incorrect;
	}

	public int getWordsTried() {
		return c_tries*c_words.length;
	}
	
	public int getCorrect() {
		return getWordsTried() - c_incorrect;
	}

	public int getTries() {
		return c_tries;
	}
	
}
