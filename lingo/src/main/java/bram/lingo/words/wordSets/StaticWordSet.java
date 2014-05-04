package bram.lingo.words.wordSets;

import java.util.ArrayList;

import bram.lingo.words.Word;

public class StaticWordSet extends WordSet {

	
	public StaticWordSet(WordSet set) {
		c_wordList = new ArrayList<Word>();
		for (Word word : set) {
			c_wordList.add(word);
		}
	}
	
	
	
	@Override
	public void addWord(String word) {
		// do nothing
	}
	
	
	@Override
	public void addWord(Word word) {
		// do nothing
	}
	
	
}
