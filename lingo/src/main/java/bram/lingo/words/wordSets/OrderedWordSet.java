package bram.lingo.words.wordSets;

import java.util.Collections;

import bram.lingo.words.Word;
import bram.lingo.words.WordComparator;

/**
 * Forces word list to be lexicographical sorted.
 * Higher cost of adding words,
 *
 */
public class OrderedWordSet extends WordSet {

	public OrderedWordSet(WordSet set) {
		super(set);
		orderList();
	}
	
	@Override
	public void addWord(Word word) {
		super.addWord(word);
		orderList();
	}

	
	private void orderList() {
		Collections.sort(c_wordList, new WordComparator());
	}
	
}
