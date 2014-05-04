package bram.lingo.words.wordSets;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import bram.lingo.words.Word;



public class WordSet implements Iterable<Word> {

	protected List<Word> c_wordList;
	
	
	public WordSet() {
		c_wordList = new ArrayList<Word>();
	}
	
	
	public void addWord(Word word) {
		c_wordList.add(word);
	}
	
	
	public void addWord(String word) {
		Word newWord = new Word(word);
		c_wordList.add(newWord);
	}

	
	public Iterator<Word> iterator() {
		return c_wordList.iterator();
	}

	
	public Word get(int i) {
		return c_wordList.get(i);
	}
	
	public WordSet getWordsStartingWith(String firstLetter) {
		WordSet wordsStartingWithLetter = new WordSet();
		for (Word word : c_wordList) {
			if (word.startsWith(firstLetter)) {
				wordsStartingWithLetter.addWord(word);
			}
		}
		return wordsStartingWithLetter;
	}
	
	public WordSet getWordsOfSize(int size) {
		WordSet wordsWithSize = new WordSet();
		for (Word word : c_wordList) {
			if (word.length() == size) {
				wordsWithSize.addWord(word);
			}
		}
		return wordsWithSize;
	}
	
	public int size() {
		return c_wordList.size();
	}
	
	public boolean contains(Word word) {
		for (Word wordInList : c_wordList) {
			if (word.equals(wordInList)) {
				return true;
			}
		}
		return false;
	}
	
	public String toString() {
		boolean isFirst = true;
		String str = "";
		for (Word word : c_wordList) {
			if (isFirst) {
				isFirst = false;
			} else {
				str += ",";
			}
			str += word.toString();
		}
		return str;
	}
	
}
