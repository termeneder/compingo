package bram.lingo.words.wordSets;

import java.util.Collections;

import bram.lingo.words.Word;
import bram.lingo.words.WordComparator;

/**
 * Forces word list to be lexicographical sorted.
 * Higher cost of adding words,
 * lower cost of finding words.
 *
 */
public class OrderedWordSet extends WordSet {

	public OrderedWordSet(WordSet set) {
		c_wordList = set.c_wordList;
		orderList();
	}
	
	@Override
	public void addWord(Word word) {
		super.addWord(word);
		orderList();
	}
	
	@Override
	public boolean contains(Word word) {
		int lowerBound = -1;
		int upperBound = c_wordList.size();
		WordComparator comparator = new WordComparator();
		while (lowerBound + 1 <= upperBound) {
			int probeIndex = (lowerBound + upperBound)/2;
			Word probeWord = get(probeIndex);
			int comparisson = comparator.compare(word, probeWord);
			if (comparisson == 0) {
				return true;
			} else if (0 < comparisson) {
				lowerBound = probeIndex;
			} else if (comparisson < 0) {
				upperBound = probeIndex;
			}
		}
		return false;
	}
	
	
	private void orderList() {
		Collections.sort(c_wordList, new WordComparator());
	}
	
}
