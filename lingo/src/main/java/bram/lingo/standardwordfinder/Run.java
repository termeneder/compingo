package bram.lingo.standardwordfinder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;

import bram.lingo.standardwordfinder.OptimalWordSets.SortOrder;
import bram.lingo.standardwordfinder.valuator.AverageDifferentiationGroupsValuator;
import bram.lingo.standardwordfinder.valuator.PositiveAveragePossibleWordsValuator;
import bram.lingo.standardwordfinder.valuator.BiggestDifferentiationGroupValuator;
import bram.lingo.standardwordfinder.valuator.CorrectLetters3Valuator;
import bram.lingo.standardwordfinder.valuator.AveragePossibleWordsValuator;
import bram.lingo.standardwordfinder.valuator.InformationAboutLetters3Valuator;
import bram.lingo.standardwordfinder.valuator.MaximumPossibleWordsValuator;
import bram.lingo.standardwordfinder.valuator.WordSetValuator;
import bram.lingo.words.Letter;
import bram.lingo.words.wordSets.EightLetterWords;
import bram.lingo.words.wordSets.FiveLetterWords;
import bram.lingo.words.wordSets.SevenLetterWords;
import bram.lingo.words.wordSets.SixLetterWords;
import bram.lingo.words.wordSets.WordSet;
import bram.lingo.words.wordSets.WordSetUtils;

public class Run {

	private static final String FILE_LOCATION = "src/main/resources/result/";
	private static final String RUNNING_PREFIX = "running_";
	private static final boolean PRINT_TO_FILE = true;
	private static final int MIN_SUBSET_SIZE = 1;
	private static final int MAX_SUBSET_SIZE = 3;
	
	public static void main(String[] args) {
		
		WordSet fiveLetterWords = SixLetterWords.getInstance().getWordsStartingWith(Letter.e, Letter.f, Letter.g, Letter.h);
		SortedMap<Letter, WordSet> wordSetMap = WordSetUtils.splitOnStartLetter(fiveLetterWords);
		for (Entry <Letter, WordSet> entry : wordSetMap.entrySet()) {
			runAlgorithmsForLetter(entry.getKey(), entry.getValue());

		}
		
	}
	
	private static void runAlgorithmsForLetter(Letter letter, WordSet letterSet) {
		String filename = "6Letters_"+letter+"_OpenTaal_"+dateToString()+".txt";
		StringBuffer output = new StringBuffer();
		List<IStandardWordSetFinder> finderAlgorithms = prepareFinderAlgorithms(letterSet);
		System.out.println(letter + " ("+letterSet.size()+"):");
		output.append(letter + " ("+letterSet.size()+"):\n");
		for (IStandardWordSetFinder algorithm : finderAlgorithms) {
			for (int subsetSize = MIN_SUBSET_SIZE ; subsetSize <= MAX_SUBSET_SIZE ; subsetSize++) {
				algorithm.setSubsetSize(subsetSize);
				OptimalWordSets optimalWordSets = algorithm.findOptimal(letterSet);
				output.append("\t"+algorithm  +": " + optimalWordSets + "\n");
				writeToFile(output, RUNNING_PREFIX + filename);
				System.out.println("\t"+algorithm  +": " + optimalWordSets);
			}
		}
		writeToFile(output, filename);
		removeFile(RUNNING_PREFIX + filename);
	}
	
	
	private static String dateToString() {
		Date date = new Date();
		String dateString = Long.toString(date.getTime());
		return dateString;
	}

	public static void writeToFile(StringBuffer buffer, String filename) {
		if (PRINT_TO_FILE) {
			try {
				PrintWriter out = new PrintWriter(FILE_LOCATION + filename);
				out.println(buffer.toString());
				out.close();
			} catch (FileNotFoundException e) {
				throw new RuntimeException("Couldn't write file. " + e, e);
			}
		}
	}
	
	public static void removeFile(String filename) {
		File file = new File(FILE_LOCATION + filename);
		file.delete();
	}
	
	
	private static List<IStandardWordSetFinder> prepareFinderAlgorithms(WordSet letterSet) {
		List<IStandardWordSetFinder> list = new ArrayList<IStandardWordSetFinder>();
		
		WordSetValuator a3Valuator =  new CorrectLetters3Valuator(letterSet);
		//list.add(new BruteForceComparativeFinder(a3Valuator, SortOrder.ASC));
		
		WordSetValuator b3Valuator = new InformationAboutLetters3Valuator(letterSet);
		//list.add(new BruteForceComparativeFinder(b3Valuator, SortOrder.ASC));
		
		WordSetValuator biggestDifferentiationGroupValuator =  new BiggestDifferentiationGroupValuator();
		//list.add(new BruteForceComparativeFinder(biggestDifferentiationGroupValuator, SortOrder.DESC));
		
		WordSetValuator amountOfDifferationGroups = new AverageDifferentiationGroupsValuator();
		//list.add(new BruteForceComparativeFinder(amountOfDifferationGroups, SortOrder.DESC));
		
		WordSetValuator countPossibleWords = new AveragePossibleWordsValuator();
		list.add(new BruteForceComparativeFinder(countPossibleWords, SortOrder.DESC));

		WordSetValuator minimisePossibleWords = new MaximumPossibleWordsValuator();
		//list.add(new BruteForceComparativeFinder(minimisePossibleWords, SortOrder.DESC));
		
		WordSetValuator g1Valuator = new PositiveAveragePossibleWordsValuator();
		list.add(new BruteForceComparativeFinder(g1Valuator, SortOrder.DESC));
		
		return list;
	}
	
	
}
