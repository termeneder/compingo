package bram.lingo.wordsettester;

import java.util.Random;

import bram.lingo.play.PlayWord;
import bram.lingo.words.Word;
import bram.lingo.words.wordSets.WordSet;

public class TestWordSet implements Comparable<TestWordSet>{

	private final WordSet c_wordSet;
	private final WordSet c_totalWordSet;
	private final String c_name;
	private int c_tries;
	private int c_correct;
	
	public TestWordSet(String name, WordSet wordSet, WordSet totalWordSet) {
		c_name = name;
		c_wordSet = wordSet;
		c_totalWordSet = totalWordSet;
		c_tries = 0;
		c_correct = 0;
	}

	public void test() {
		Word randomWord = getRandomWord();
		PlayWord playword = new PlayWord(randomWord, c_totalWordSet, new WordSetInput(c_wordSet));
		if (playword.play()) {
			c_correct++;
		}
		c_tries++;
	}

	private Word getRandomWord() {
		Random rand = new Random();
		int randomIndex = rand.nextInt(c_totalWordSet.size());
		return c_totalWordSet.get(randomIndex);
	}
	
	public double getRatio() {
		if (c_tries == 0) {
			return 0d;
		}
		return (double)c_correct / (double) c_tries;
	}
	 
	public String toString() {
		
		return c_name + ": " + c_correct + "/" + c_tries + "=" + getRatio();
	}

	@Override
	public int compareTo(TestWordSet otherSet) {
		if (getRatio() == otherSet.getRatio()) {
			return 0;
		} else if (getRatio() < otherSet.getRatio()) {
			return 1;
		} else {
			return -1;
		}
	}
	
}
