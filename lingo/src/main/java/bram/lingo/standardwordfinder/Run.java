package bram.lingo.standardwordfinder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;

import vincent.lingo.revisited.VincentRevisited;
import bram.lingo.standardwordfinder.genetic.GeneticComparativeFinder;
import bram.lingo.standardwordfinder.genetic.GeneticConfiguration;
import bram.lingo.standardwordfinder.valuator.AverageDifferentiationGroupsValuator;
import bram.lingo.standardwordfinder.valuator.PositiveAveragePossibleWordsValuator;
import bram.lingo.standardwordfinder.valuator.BiggestDifferentiationGroupValuator;
import bram.lingo.standardwordfinder.valuator.CorrectLetters3Valuator;
import bram.lingo.standardwordfinder.valuator.AveragePossibleWordsValuator;
import bram.lingo.standardwordfinder.valuator.InformationAboutLetters3Valuator;
import bram.lingo.standardwordfinder.valuator.MaximumPossibleWordsValuator;
import bram.lingo.standardwordfinder.valuator.PositiveMaximumPossibleWordsValuator;
import bram.lingo.standardwordfinder.valuator.WordSetValuator;
import bram.lingo.words.Letter;
import bram.lingo.words.wordSets.NLetterWords;

import bram.lingo.words.wordSets.Source;
import bram.lingo.words.wordSets.WordSet;
import bram.lingo.words.wordSets.WordSetUtils;

public class Run {

	private static final String FILE_LOCATION = "src/main/resources/result/";
	private static final String RUNNING_PREFIX = "running_";
	private static final String DESCRIPTION_PREFIX = "";
	private static final int WORD_LENGTH = 6;
	private static final Source SOURCE = Source.OTTUE;
	private static final boolean PRINT_TO_FILE = true;
	private static final boolean APPEND_TIMESTAMP_TO_FILENAME = false;
	private static final int MIN_SUBSET_SIZE = 1;
	private static final int MAX_SUBSET_SIZE = 3;
	
	public static void main(String[] args) {
		
		WordSet words = NLetterWords.getInstance(WORD_LENGTH, SOURCE);
		SortedMap<Letter, WordSet> wordSetMap = WordSetUtils.splitOnStartLetter(words);
		for (Entry <Letter, WordSet> entry : wordSetMap.entrySet()) {
			runAlgorithmsForLetter(entry.getKey(), entry.getValue());

		}
		
	}
	
	private static void runAlgorithmsForLetter(Letter letter, WordSet letterSet) {
		String filename = createFilename(letter);
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
	
	private static String createFilename(Letter letter) {
		String filename = DESCRIPTION_PREFIX + WORD_LENGTH + "_"  + letter + "_" + SOURCE;
		if (APPEND_TIMESTAMP_TO_FILENAME) {
			filename += "_" +dateToString();
		}
		filename += ".txt";
		return filename;
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
		
		GeneticConfiguration configLong = new GeneticConfiguration();
		configLong.amountOfSetKept = 100;
		configLong.generations = 1000;
		configLong.newSets = 50;
		configLong.mutations = 25;
		configLong.recombinations = 25;
		
		
		GeneticConfiguration configShort = new GeneticConfiguration();
		configShort.amountOfSetKept = 100;
		configShort.generations = 10;
		configShort.newSets = 50;
		configShort.mutations = 25;
		configShort.recombinations = 25;
		
		WordSetValuator a3Valuator =  new CorrectLetters3Valuator(letterSet);
		list.add(new ExhaustiveComparativeFinder(a3Valuator, SortOrder.ASC));
		
		WordSetValuator b3Valuator = new InformationAboutLetters3Valuator(letterSet);
		list.add(new ExhaustiveComparativeFinder(b3Valuator, SortOrder.ASC));
		/*
		WordSetValuator biggestDifferentiationGroupValuator =  new BiggestDifferentiationGroupValuator();
		list.add(new GeneticComparativeFinder(biggestDifferentiationGroupValuator, SortOrder.DESC, configLong));
		//list.add(new ExhaustiveComparativeFinder(biggestDifferentiationGroupValuator, SortOrder.DESC));
		
		WordSetValuator amountOfDifferationGroups = new AverageDifferentiationGroupsValuator();
		//list.add(new GeneticComparativeFinder(amountOfDifferationGroups, SortOrder.DESC, configLong));
		list.add(new ExhaustiveComparativeFinder(amountOfDifferationGroups, SortOrder.DESC));
		
		WordSetValuator countPossibleWords = new AveragePossibleWordsValuator();
		list.add(new GeneticComparativeFinder(countPossibleWords, SortOrder.DESC, configShort));
		//list.add(new ExhaustiveComparativeFinder(countPossibleWords, SortOrder.DESC));
		
		WordSetValuator minimisePossibleWords = new MaximumPossibleWordsValuator();
		list.add(new GeneticComparativeFinder(minimisePossibleWords, SortOrder.DESC, configShort));
		//list.add(new ExhaustiveComparativeFinder(minimisePossibleWords, SortOrder.DESC));
		
		WordSetValuator g1Valuator = new PositiveAveragePossibleWordsValuator();
		list.add(new GeneticComparativeFinder(g1Valuator, SortOrder.DESC, configShort));
		//list.add(new ExhaustiveComparativeFinder(g1Valuator, SortOrder.DESC));
		
		WordSetValuator h1Valuator = new PositiveMaximumPossibleWordsValuator();
		list.add(new GeneticComparativeFinder(h1Valuator, SortOrder.DESC, configShort));
		//list.add(new ExhaustiveComparativeFinder(h1Valuator, SortOrder.DESC));
		*/
		list.add(new VincentRevisited(SortOrder.ASC));
		return list;
	}
	
	
}
