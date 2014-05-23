package bram.lingo.play;

import java.util.Random;

import bram.lingo.input.ManualInput;
import bram.lingo.words.Letter;
import bram.lingo.words.Word;
import bram.lingo.words.wordSets.NLetterWords;
import bram.lingo.words.wordSets.Source;
import bram.lingo.words.wordSets.WordSet;

public class Run {

	public static void main(String[] args) {
		int played = 0;
		int correct = 0;
		while (true) {
			WordSet wordSet = NLetterWords.getInstance(6, Source.OTTUE).getWordsStartingWith(Letter.a, Letter.b, Letter.c);
			Word randomWord = getRandomWord(wordSet);
			WordSet correctWordSet = getCorrectWordSet(randomWord, wordSet);
			PlayWord playWord = new PlayWord(randomWord, correctWordSet, new ManualInput());
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
		Letter firstLetter = word.getLetter(0);
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
