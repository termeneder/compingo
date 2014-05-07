package bram.lingo.standardwordfinder;

import java.util.Map;

import bram.lingo.words.Letter;
import bram.lingo.words.wordSets.WordSet;

public class OptimiseCorrectLettersFinder implements StandardWordSetFinder{

	private int c_sizeOfOptimalSet;
	
	public OptimiseCorrectLettersFinder(WordSet preprocessWordSet, int sizeOfOptimalSet) {
		c_sizeOfOptimalSet = sizeOfOptimalSet;
		preprocess(preprocessWordSet);
	}
	
	private void preprocess(WordSet preprocessWordSet) {
		int wordLength = preprocessWordSet.get(0).length();
	//	Map<Letter, Integer>[] amountOfLettersOnPositionMap = new Map<Letter, Integer>[wordLength];
		
	}

	@Override
	public OptimalWordSets findOptimal(WordSet set) {
		// TODO Auto-generated method stub
		return null;
	}

}
