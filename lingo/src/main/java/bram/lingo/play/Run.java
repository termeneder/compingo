package bram.lingo.play;

import java.util.Random;

import bram.lingo.words.Word;
import bram.lingo.words.wordSets.FiveLetterWords;
import bram.lingo.words.wordSets.WordSet;

public class Run {

	public static void main(String[] args) {
		int played = 0;
		int correct = 0;
		while (true) {
			WordSet wordSet = FiveLetterWords.getInstance();
			Word randomWord = getRandomWord(wordSet);
			WordSet correctWordSet = getCorrectWordSet(randomWord, wordSet);
			PlayWord playWord = new PlayWord(randomWord, correctWordSet);
			if (playWord.play()) {
				correct++;
			}
			played++;
			int percentage = (int)(((double) correct/ (double) played)*100d);
			System.out.println(correct + "/" + played + " (" + percentage + "%) goed.");
		}
	}
	
	private static WordSet getCorrectWordSet(Word word,
			WordSet originalWordSet) {
		int wordLength = word.length();
		String firstLetter = word.getLetter(0).toString();
		WordSet filteredWordSet = originalWordSet.getWordsOfSize(wordLength);
		filteredWordSet = filteredWordSet.getWordsStartingWith(firstLetter);
		return filteredWordSet;
	}

	private static Word getRandomWord(WordSet set) {
		Random rand = new Random();
		int randomIndex = rand.nextInt(set.size());
		return set.get(randomIndex);
	}
	
}
