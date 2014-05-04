package bram.lingo.standardwordfinder.valuator;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import bram.lingo.lingo.LingoComparator;
import bram.lingo.lingo.LingoCompareValue;
import bram.lingo.words.Word;
import bram.lingo.words.wordSets.WordSet;

public class Differentiation {

	private Map<String, WordSet> c_differiated;
	
	public Differentiation(WordSet superset, WordSet subset) {
		differentiate(superset, subset);
	}

	private void differentiate(WordSet superset, WordSet subset) {
		c_differiated = new HashMap<String, WordSet>();
		for (Word word : superset) {
			String key = wordToKey(word, subset);
			if (c_differiated.containsKey(key)) {
				c_differiated.get(key).addWord(word);
			} else {
				WordSet newWordSet = new WordSet();
				newWordSet.addWord(word);
				c_differiated.put(key, newWordSet);
			}
		}
		
	}

	private String wordToKey(Word word, WordSet subset) {
		LingoComparator comparator = new LingoComparator(word);
		String key = "";
		for (Word subsetWord : subset) {
			LingoCompareValue[] values = comparator.compare(subsetWord);
			String keyPart = valuesToKeyPart(values);
			key += keyPart;
		}
		return key;
	}

	private String valuesToKeyPart(LingoCompareValue[] values) {
		String keyPart = "";
		for (LingoCompareValue value : values) {
			switch (value) {
			case Correct : keyPart += "c"; break;
			case Incorrect : keyPart += "i"; break;
			case Miss : keyPart += "m"; break;
			}
		}
		return keyPart;
	}
	
	public Collection<WordSet> getWordSetCollection() {
		return c_differiated.values();
	}
	
}

