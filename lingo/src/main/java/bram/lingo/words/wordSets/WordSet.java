package bram.lingo.words.wordSets;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import bram.lingo.words.Letter;
import bram.lingo.words.Word;



public class WordSet implements Iterable<Word> {

	protected List<Word> c_wordList;
	
	public WordSet(WordSet set) {
		this();
		for (Word wordInSet : set) {
			addWord(new Word(wordInSet));
		}
	}
	
	public WordSet() {
		c_wordList = new ArrayList<Word>();
	}
	
	
	public void addWord(Word word) {
		c_wordList.add(word);
	}
	
	
	public void addWord(String word) {
		Word newWord = new Word(word);
		addWord(newWord);
	}

	
	public Iterator<Word> iterator() {
		return c_wordList.iterator();
	}

	
	public Word get(int i) {
		return c_wordList.get(i);
	}
	
	public WordSet getWordsStartingWith(Letter ... firstLetterList) {
		WordSet wordsStartingWithLetter = new WordSet();
		for (Word word : c_wordList) {
			for (Letter firstLetter : firstLetterList) {
				if (word.startsWith(firstLetter)) {
					wordsStartingWithLetter.addWord(word);
				}
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
		return c_wordList.contains(word);
	}
	
	@Override
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
	
	public boolean isEquivalent(WordSet otherSet) {
		if (this.size() != otherSet.size()) {
			return false;
		}
		for (Word wordInOtherSet : otherSet) {
			if (!this.contains(wordInOtherSet)) {
				return false;
			}
		}
		return true;
	}
}
