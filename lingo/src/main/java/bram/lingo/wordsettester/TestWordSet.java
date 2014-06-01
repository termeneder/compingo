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
	

	public TestWordSet(String csvString, WordSet totalWordSet) {
		c_totalWordSet = totalWordSet;
		String[] strings = csvString.split(",");
		c_name = strings[0];
		c_tries = Integer.parseInt(strings[3]);
		c_correct = Integer.parseInt(strings[2]);
		c_wordSet = new WordSet();
		for (int index = 4 ; index < strings.length ; index++) {
			c_wordSet.addWord(strings[index]);
		}
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
	
	public int getTries() {
		return c_tries;
	}
	
	public String toCSV() {
		String csv = c_name;
		csv += "," + getRatio();
		csv += "," + c_correct;
		csv += "," + c_tries;
		for (Word word : c_wordSet) {
			csv += "," + word;
		}
		return csv;
			
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
