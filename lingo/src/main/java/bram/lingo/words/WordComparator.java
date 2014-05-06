package bram.lingo.words;

import java.util.Comparator;

public class WordComparator implements Comparator<Word> {

	@Override
	public int compare(Word word1, Word word2) {
		if (word1.length() < word2.length()) {
			return -1;
		} else if (word2.length() < word1.length()) {
			return 1;
		} else {
			for (int i = 0 ; i < word1.length() ; i++) {
				Letter letter1 = word1.getLetter(i);
				Letter letter2 = word2.getLetter(i);
				if (letter1.compareTo(letter2) != 0) {
					return letter1.compareTo(letter2);
				}
			}
		}
		return 0;
	}

}
