package bram.lingo.resultobjects;

import jochem.lingo.valuators.revisited.AverageAmbiguousGroupSizeValuator;
import jochem.lingo.valuators.revisited.AverageAmbiguousGroupSizeValuatorBram;
import jochem.lingo.valuators.revisited.NonAmbiguityValuator;
import bram.lingo.standardwordfinder.Select;
import bram.lingo.standardwordfinder.SortOrder;
import bram.lingo.standardwordfinder.valuator.AveragePossibleWordsValuator;
import bram.lingo.standardwordfinder.valuator.BiggestDifferentiationGroupValuator;
import bram.lingo.standardwordfinder.valuator.CorrectLetters3Valuator;
import bram.lingo.standardwordfinder.valuator.InformationAboutLetters3Valuator;
import bram.lingo.standardwordfinder.valuator.MaximumPossibleWordsValuator;
import bram.lingo.standardwordfinder.valuator.PositiveAveragePossibleWordsValuator;
import bram.lingo.standardwordfinder.valuator.PositiveMaximumPossibleWordsValuator;
import bram.lingo.standardwordfinder.valuator.WordSetValuator;
import bram.lingo.words.LetterUtils;
import bram.lingo.words.Word;
import bram.lingo.words.wordSets.NLetterWords;
import bram.lingo.words.wordSets.Source;
import bram.lingo.words.wordSets.WordSet;

public class RunningQuery extends Query{
	public int allocation;
	
	public Indices indices;
	
	private transient int c_maxindex;
	private transient WordSet c_totalWordSet;
	private transient WordSetValuator c_valuator;
	private transient SortOrder c_order;
	
	private static final boolean PRINT_BEST_IMPROVED = true;
	private static final boolean PRINT_BEST_TIED = false;
	
	public void update() {
		prepare();
		for (int iteration = 0 ; iteration < allocation ; iteration++) {
			checkWordSet();
			if (indices.hasNext(c_maxindex)) {
				indices.nextIndices(c_maxindex);
			} else {
				break;
			}
		}
		
	}
	
	private void prepare() {
		if (indices == null || indices.getAmountOfIndices() == 0) {
			indices = new Indices(subsetsize);
		}
		setWordSet();
		setAlgorithm();
	}

	private void setWordSet() {
		Source source = getSource();
		c_totalWordSet = NLetterWords.getInstance(wordlength, source).getWordsStartingWith(LetterUtils.StringToLetter(startingletter));
		c_maxindex = c_totalWordSet.size();
	}
	
	private Source getSource() {
		switch (wordset) {
		case "ottue" : return Source.OTTUE;
		case "open_taal" : return Source.OPEN_TAAL;
		case "tue" : return Source.TUE;
		}
		return null;
	}

	private void setAlgorithm() {
		switch(algorithm) {
		case "A3" : c_valuator = new CorrectLetters3Valuator(c_totalWordSet); break;
		case "B3" : c_valuator = new InformationAboutLetters3Valuator(c_totalWordSet); break;
		case "C1" : c_valuator = new BiggestDifferentiationGroupValuator(); break;
		case "C2" : c_valuator = new NonAmbiguityValuator(c_totalWordSet); break;
		case "D2" : c_valuator = new AverageAmbiguousGroupSizeValuatorBram(c_totalWordSet); break;
		case "E1" : c_valuator = new AveragePossibleWordsValuator(); break;
		case "F1" : c_valuator = new MaximumPossibleWordsValuator(); break;
		case "G1" : c_valuator = new PositiveAveragePossibleWordsValuator(); break;
		case "H1" : c_valuator = new PositiveMaximumPossibleWordsValuator(); break;
		case "J2" : c_valuator = new AverageAmbiguousGroupSizeValuator(c_totalWordSet); break;
		default : throw new RuntimeException("Unknown algorithm " + algorithm + " in " + getClass());
		}
		
		switch (sortorder) {
		case "best" : c_order = SortOrder.getSortOrderFromSelectAndBest(Select.BEST, c_valuator.getSortOrderForBest()); break;
		case "worst" : c_order = SortOrder.getSortOrderFromSelectAndBest(Select.WORST, c_valuator.getSortOrderForBest()); break;
		default : throw new RuntimeException("Unknown sortorder " + sortorder + " in " + getClass());
		}
	}
	
	private void checkWordSet() {
		WordSet subwordset = getWordSet();
		double value = valuateWordSet(subwordset);
		checkValue(subwordset, value);
		
	}
	
	private void checkValue(WordSet subwordset, double value) {
		if (bestsets == null || bestsets.size() == 0) {
			setNewBestScore(subwordset, value);
		} else if (isBetter(value, bestscore)) {
			setNewBestScore(subwordset, value);
		} else if (value == bestscore) {
			addNewBestScore(subwordset);
		}
	}

	

	private boolean isBetter(double thisValue, double otherValue) {
		if (c_order == SortOrder.ASC) {
			return thisValue > otherValue;
		} else if (c_order == SortOrder.DESC) {
			return thisValue < otherValue;
		}
		return false;
	}

	private void setNewBestScore(WordSet subwordset, double value) {
		bestscore = value;
		bestsets = new BestSets();
		addNewBestScore(subwordset);
		if (PRINT_BEST_IMPROVED && ! PRINT_BEST_TIED) {
			System.out.println(wordlength + "," + startingletter );
			System.out.println(printvalue);
		}
	}

	
	private void addNewBestScore(WordSet subwordset) {
		Set thisSet = wordsetToSet(subwordset);
		bestsets.add(thisSet);
		printvalue = createPrintValue(false);
		if (PRINT_BEST_TIED) {
			System.out.println(wordlength + "," + startingletter );
			System.out.println(printvalue);
		}
	}

	public String createPrintValue(boolean finished) {
		
		StringBuffer valueBuffer = new StringBuffer();
		valueBuffer.append(c_valuator.getCode() + (finished?"":"p") + ") " + c_valuator.getDescription() + ", ");
		valueBuffer.append(subsetsize + " word" + (subsetsize==1?"":"s") + ": ");
		boolean isFirstSet = true;
		for (Set set : bestsets) {
			if (isFirstSet) {
				isFirstSet = false;
			} else {
				valueBuffer.append(" || ");
			}
			boolean isFirstWord = true;
			for (String word : set.wordList) {
				if (isFirstWord) {
					isFirstWord = false;
				} else {
					valueBuffer.append(",");
				}
				valueBuffer.append(word);
			}
		}
		valueBuffer.append(" (" + bestscore + ")");
		return valueBuffer.toString();
	}

	private Set wordsetToSet(WordSet wordset) {
		Set set = new Set();
		for (Word word : wordset) {
			set.addWord(word.toString());
		}
		return set;
	}
	
	private WordSet getWordSet() {
		WordSet subwordset = new WordSet();
		for (int index : indices) {
			subwordset.addWord(c_totalWordSet.get(index));
		}
		return subwordset;
	}

	private double valuateWordSet(WordSet subwordset) {
		return c_valuator.value(c_totalWordSet, subwordset);
	}
	
	public boolean finished() {
		return ! indices.hasNext(c_maxindex);
	}

}
