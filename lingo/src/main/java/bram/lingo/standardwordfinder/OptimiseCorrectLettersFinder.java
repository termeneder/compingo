package bram.lingo.standardwordfinder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bram.lingo.words.Alphabet;
import bram.lingo.words.Letter;
import bram.lingo.words.Word;
import bram.lingo.words.wordSets.WordSet;

public class OptimiseCorrectLettersFinder implements StandardWordSetFinder{

	private List<Map<Letter, Integer>> c_amountOfLettersAtPositionMap;
	private int c_sizeOfOptimalSet;
	
	public OptimiseCorrectLettersFinder(WordSet preprocessWordSet, int sizeOfOptimalSet) {
		c_sizeOfOptimalSet = sizeOfOptimalSet;
		preprocess(preprocessWordSet);
	}
	
	private void preprocess(WordSet preprocessWordSet) {
		int wordLength = preprocessWordSet.get(0).length();
		initialiseMap(wordLength);
		for (Word word : preprocessWordSet) {
			processWord(word);
		}
	}
	
	private void processWord(Word word) {
		for (int letterPosition = 0 ; letterPosition < word.length() ; letterPosition++) {
			Letter letter = word.getLetter(letterPosition);
			Map<Letter, Integer> positionMap = c_amountOfLettersAtPositionMap.get(letterPosition);
			Integer occurrencesOfLetterAtPosition = positionMap.get(letter);
			occurrencesOfLetterAtPosition++;
			positionMap.put(letter, occurrencesOfLetterAtPosition);
		}
	}

	private void initialiseMap(int wordLength) {
		c_amountOfLettersAtPositionMap = new ArrayList<Map<Letter, Integer>>();
		for (int letterIndex = 0 ; letterIndex < wordLength ; letterIndex++) {
			Map<Letter, Integer> positionMap = new HashMap<Letter, Integer>();
			c_amountOfLettersAtPositionMap.add(letterIndex, positionMap);
			for (Letter letter : Alphabet.getInstance()) {
				positionMap.put(letter, 0);
			}
		}
	}

	@Override
	public OptimalWordSets findOptimal(WordSet set) {
		// TODO Auto-generated method stub
		return null;
	}

}
