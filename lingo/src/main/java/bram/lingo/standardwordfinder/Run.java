package bram.lingo.standardwordfinder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;

import bram.lingo.standardwordfinder.genetic.GeneticComparativeFinder;
import bram.lingo.standardwordfinder.genetic.GeneticConfiguration;
import bram.lingo.standardwordfinder.valuator.AverageDifferentiationGroupsValuator;
import bram.lingo.standardwordfinder.valuator.CorrectLettersValuator;
import bram.lingo.standardwordfinder.valuator.PositiveAveragePossibleWordsValuator;
import bram.lingo.standardwordfinder.valuator.BiggestDifferentiationGroupValuator;
import bram.lingo.standardwordfinder.valuator.CorrectLetters3Valuator;
import bram.lingo.standardwordfinder.valuator.AveragePossibleWordsValuator;
import bram.lingo.standardwordfinder.valuator.InformationAboutLetters3Valuator;
import bram.lingo.standardwordfinder.valuator.MaximumPossibleWordsValuator;
import bram.lingo.standardwordfinder.valuator.PositiveMaximumPossibleWordsValuator;
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
	private static final String DESCRIPTION_PREFIX = "MonteCarlo_Genetic_Comparer";
	private static final boolean PRINT_TO_FILE = false;
	private static final int MIN_SUBSET_SIZE = 3;
	private static final int MAX_SUBSET_SIZE = 3;
	
	public static void main(String[] args) {
		
		WordSet fiveLetterWords = FiveLetterWords.getInstance();
		SortedMap<Letter, WordSet> wordSetMap = WordSetUtils.splitOnStartLetter(fiveLetterWords);
		for (Entry <Letter, WordSet> entry : wordSetMap.entrySet()) {
			runAlgorithmsForLetter(entry.getKey(), entry.getValue());

		}
		
	}
	
	private static void runAlgorithmsForLetter(Letter letter, WordSet letterSet) {
		String filename = DESCRIPTION_PREFIX+"_"+letter+"_"+dateToString()+".txt";
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
		
		/*
		WordSetValuator a3Valuator =  new CorrectLetters3Valuator(letterSet);
		list.add(new ExhaustiveComparativeFinder(a3Valuator, SortOrder.ASC));
		
		WordSetValuator b3Valuator = new InformationAboutLetters3Valuator(letterSet);
		list.add(new ExhaustiveComparativeFinder(b3Valuator, SortOrder.ASC));
		
		WordSetValuator biggestDifferentiationGroupValuator =  new BiggestDifferentiationGroupValuator();
		list.add(new ExhaustiveComparativeFinder(biggestDifferentiationGroupValuator, SortOrder.DESC));
		
		WordSetValuator amountOfDifferationGroups = new AverageDifferentiationGroupsValuator();
		list.add(new ExhaustiveComparativeFinder(amountOfDifferationGroups, SortOrder.DESC));
		
		WordSetValuator countPossibleWords = new AveragePossibleWordsValuator();
		list.add(new ExhaustiveComparativeFinder(countPossibleWords, SortOrder.DESC));

		WordSetValuator minimisePossibleWords = new MaximumPossibleWordsValuator();
		list.add(new ExhaustiveComparativeFinder(minimisePossibleWords, SortOrder.DESC));
		
		WordSetValuator g1Valuator = new PositiveAveragePossibleWordsValuator();
		list.add(new ExhaustiveComparativeFinder(g1Valuator, SortOrder.DESC));
		
		WordSetValuator h1Valuator = new PositiveMaximumPossibleWordsValuator();
		list.add(new ExhaustiveComparativeFinder(h1Valuator, SortOrder.DESC));
		
		*/

		WordSetValuator a3Valuator =  new CorrectLetters3Valuator(letterSet);
		list.add(new ExhaustiveComparativeFinder(a3Valuator, SortOrder.ASC));
		for (int i = 10000 ; i <= 10000 ; i*=10) {
			list.add(new MonteCarloComparativeFinder(a3Valuator, SortOrder.ASC, i*100));
			GeneticConfiguration config = new GeneticConfiguration();
			config.amountOfSetKept = 100;
			config.generations = i;
			config.newSets = 50;
			config.mutations = 25;
			config.recombinations = 25;
			list.add(new GeneticComparativeFinder(a3Valuator, SortOrder.ASC, config));
		}
		
		return list;
	}
	
	
}
