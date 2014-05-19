package bram.lingo.standardwordfinder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bram.lingo.words.Alphabet;
import bram.lingo.words.Letter;
import bram.lingo.words.Word;
import bram.lingo.words.wordSets.WordSet;
import bram.lingo.words.wordSets.WordSubsetIterable;

public class OptimiseCorrectLettersFinder extends StandardWordSetFinder{

	private List<Map<Letter, Integer>> c_amountOfLettersAtPositionMap;
	private SortOrder c_order;
	
	public OptimiseCorrectLettersFinder(WordSet preprocessWordSet, SortOrder order) {
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
		WordSubsetIterable iterable = new WordSubsetIterable(set, getSubsetSize());
		OptimalWordSets optimalSets = new OptimalWordSets(c_order);
		for (WordSet subset : iterable) {
			double value = valuateSubset(subset, set.size());
			optimalSets.tryNewWordSet(subset, value);
		}
		return optimalSets;
	}

	private double valuateSubset(WordSet subset, int amountOfWordsInSuperset) {
		long value = 0;
		for (int letterPosition = 0 ; letterPosition < c_amountOfLettersAtPositionMap.size() ; letterPosition++) {
			Map<Letter, Integer> amountOfLettersForPosition = c_amountOfLettersAtPositionMap.get(letterPosition);
			List<Letter> usedLettersForPosition = new ArrayList<Letter>();
			for(Word wordInSubset : subset) {
				Letter letterAtPosition = wordInSubset.getLetter(letterPosition);
				if (!usedLettersForPosition.contains(letterAtPosition)) {
					value += amountOfLettersForPosition.get(letterAtPosition);
					usedLettersForPosition.add(letterAtPosition);
				}
			}
		} 
		return (double)value/(double)amountOfWordsInSuperset;
	}

	@Override
	public String getCode() {
		return "A2";
	}

	@Override
	public String getDescription() {
		return "Optimise correct letters";
	}

}
