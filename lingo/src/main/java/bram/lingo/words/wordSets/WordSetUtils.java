package bram.lingo.words.wordSets;

import java.util.SortedMap;
import java.util.TreeMap;

import bram.lingo.words.Letter;
import bram.lingo.words.Word;

public class WordSetUtils {

	public static SortedMap<Letter, WordSet> splitOnStartLetter(WordSet set) {
		SortedMap<Letter, WordSet> map = new TreeMap<Letter, WordSet>();
		for (Word word : set) {
			WordSet letterSet;
			Letter letter = word.getLetter(0);
			if (map.containsKey(letter)) {
				letterSet = map.get(letter);
			} else {
				letterSet = new WordSet();
				map.put(letter, letterSet);
			}
			letterSet.addWord(word);
		}
		
		return map;
	}
	
}
