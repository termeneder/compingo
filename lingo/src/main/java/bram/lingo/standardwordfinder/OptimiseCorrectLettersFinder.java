package bram.lingo.standardwordfinder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bram.lingo.standardwordfinder.OptimalWordSets.SortOrder;
import bram.lingo.words.Alphabet;
import bram.lingo.words.Letter;
import bram.lingo.words.Word;
import bram.lingo.words.wordSets.WordSet;
import bram.lingo.words.wordSets.WordSubsetIterable;
import bram.lingo.words.wordSets.WordSubsetIterable.SubsetIterator;

public class OptimiseCorrectLettersFinder implements StandardWordSetFinder{

	private List<Map<Letter, Integer>> c_amountOfLettersAtPositionMap;
	private SortOrder c_order;
	private int c_sizeOfOptimalSet;
	
	public OptimiseCorrectLettersFinder(WordSet preprocessWordSet, int sizeOfOptimalSet, SortOrder order) {
		c_sizeOfOptimalSet = sizeOfOptimalSet;
		c_order = order;
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
		WordSubsetIterable iterable = new WordSubsetIterable(set, c_sizeOfOptimalSet);
		OptimalWordSets optimalSets = new OptimalWordSets(c_order);
		for (WordSet subset : iterable) {
			double value = valuateSubset(subset);
			optimalSets.tryNewWordSet(subset, value);
		}
		return null;
	}

	private double valuateSubset(WordSet subset) {
		long value = 0;
		for (int letterPosition = 0 ; letterPosition < c_amountOfLettersAtPositionMap.size() ; letterPosition++) {
			
		}
		return 0;
	}

}
