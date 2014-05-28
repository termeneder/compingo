package bram.lingo.standardwordfinder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;

import jochem.lingo.valuators.revisited.MinCorrectCountValuator;
import jochem.lingo.valuators.revisited.NonAmbiguityValuator;
import vincent.lingo.revisited.VincentRevisited;
import bram.lingo.standardwordfinder.genetic.GeneticComparativeFinder;
import bram.lingo.standardwordfinder.genetic.GeneticConfiguration;
import bram.lingo.standardwordfinder.genetic.distributor.DistributionFactory.DistributionType;
import bram.lingo.standardwordfinder.valuator.AverageDifferentiationGroupsValuator;
import bram.lingo.standardwordfinder.valuator.AveragePossibleWordsValuator;
import bram.lingo.standardwordfinder.valuator.BiggestDifferentiationGroupValuator;
import bram.lingo.standardwordfinder.valuator.CorrectLetters3Valuator;
import bram.lingo.standardwordfinder.valuator.InformationAboutLetters3Valuator;
import bram.lingo.standardwordfinder.valuator.MaximumPossibleWordsValuator;
import bram.lingo.standardwordfinder.valuator.PositiveAveragePossibleWordsValuator;
import bram.lingo.standardwordfinder.valuator.PositiveMaximumPossibleWordsValuator;
import bram.lingo.standardwordfinder.valuator.WordSetValuator;
import bram.lingo.utils.Timer;
import bram.lingo.words.Letter;
import bram.lingo.words.wordSets.NLetterWords;
import bram.lingo.words.wordSets.Source;
import bram.lingo.words.wordSets.WordSet;
import bram.lingo.words.wordSets.WordSetUtils;

public class Run {

	
	// TODO move this to config
	private static final String FILE_LOCATION = "src/main/resources/result/";
	private static final String RUNNING_PREFIX = "running_";
	private static final String DESCRIPTION_PREFIX = "";
	private static final int WORD_LENGTH = 5;
	private static final Source SOURCE = Source.OTTUE;
	private static final boolean PRINT_TO_FILE = true;
	private static final boolean APPEND_TIMESTAMP_TO_FILENAME = true;
	private static final boolean PRINT_TIME = true;
	private static final int MIN_SUBSET_SIZE = 1;
	private static final int MAX_SUBSET_SIZE = 3;
	private static final Select SELECT = Select.BEST;
	
	public static void main(String[] args) {
		WordSet words = NLetterWords.getInstance(WORD_LENGTH, SOURCE);
		SortedMap<Letter, WordSet> wordSetMap = WordSetUtils.splitOnStartLetter(words);
		for (Entry <Letter, WordSet> entry : wordSetMap.entrySet()) {
			runAllAlgorithmsForLetter(entry.getKey(), entry.getValue());
		}
	}
	
	private static void runAllAlgorithmsForLetter(Letter letter, WordSet letterSet) {
		String filename = createFilename(letter);
		StringBuffer output = new StringBuffer();
		List<IStandardWordSetFinder> finderAlgorithms = prepareFinderAlgorithms(letterSet);
		System.out.println(letter + " ("+letterSet.size()+"):");
		output.append(letter + " ("+letterSet.size()+"):\n");
		for (IStandardWordSetFinder algorithm : finderAlgorithms) {
			for (int subsetSize = MIN_SUBSET_SIZE ; subsetSize <= MAX_SUBSET_SIZE ; subsetSize++) {
				output.append(runAlgorithm(algorithm, subsetSize, letterSet));
				writeToFile(output, RUNNING_PREFIX + filename);
			}
		}
		writeToFile(output, filename);
		removeFile(RUNNING_PREFIX + filename);
	}
	
	private static String runAlgorithm(IStandardWordSetFinder algorithm, int subsetSize, WordSet letterSet) {
		algorithm.setSubsetSize(subsetSize);
		Timer timer = new Timer();
		OptimalWordSets optimalWordSets = algorithm.findOptimal(letterSet);
		System.out.println("\t"+algorithm  +": " + optimalWordSets);
		if (PRINT_TIME) {
			System.out.println("\t"+timer);
		}
		return "\t"+algorithm  +": " + optimalWordSets + "\n";
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
		boolean useGeneticForLongCalculations = false;
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
		/*
		WordSetValuator a3Valuator =  new CorrectLetters3Valuator(letterSet);
		list.add(new ExhaustiveComparativeFinder(a3Valuator, SELECT));
		
		WordSetValuator b3Valuator = new InformationAboutLetters3Valuator(letterSet);
		list.add(new ExhaustiveComparativeFinder(b3Valuator, SELECT));
		
		WordSetValuator c1Valuator =  new BiggestDifferentiationGroupValuator();
		if (useGeneticForLongCalculations) {
			list.add(new GeneticComparativeFinder(c1Valuator, SELECT, configShort));
		} else {
			list.add(new ExhaustiveComparativeFinder(c1Valuator, SELECT));
		}
		
		WordSetValuator d1Valuator = new AverageDifferentiationGroupsValuator();
		if (useGeneticForLongCalculations) {
			list.add(new GeneticComparativeFinder(d1Valuator, SELECT, configShort));
		} else {
			list.add(new ExhaustiveComparativeFinder(d1Valuator, SELECT));
		}
		
		WordSetValuator e1Valuator = new AveragePossibleWordsValuator();
		if (useGeneticForLongCalculations) {
			list.add(new GeneticComparativeFinder(e1Valuator, SELECT, configShort));
		} else {
			list.add(new ExhaustiveComparativeFinder(e1Valuator, SELECT));
		}
		
		WordSetValuator f1Valuator = new MaximumPossibleWordsValuator();
		if (useGeneticForLongCalculations) {
			list.add(new GeneticComparativeFinder(f1Valuator, SELECT, configShort));
		} else {
			list.add(new ExhaustiveComparativeFinder(f1Valuator, SELECT));
		}
		*/
		WordSetValuator g1Valuator = new PositiveAveragePossibleWordsValuator();
		if (useGeneticForLongCalculations) {
			list.add(new GeneticComparativeFinder(g1Valuator, SELECT, configShort));
		} else {
			list.add(new ExhaustiveComparativeFinder(g1Valuator, SELECT));
		}
		/*
		WordSetValuator h1Valuator = new PositiveMaximumPossibleWordsValuator();
		if (useGeneticForLongCalculations) {
			list.add(new GeneticComparativeFinder(h1Valuator, SELECT, configShort));
		} else {
			list.add(new ExhaustiveComparativeFinder(h1Valuator, SELECT));
		}
		
		list.add(new VincentRevisited(SELECT));*/
		/*
		GeneticConfiguration configA = new GeneticConfiguration();
		configA.amountOfSetKept = 100;
		configA.generations = 100;
		configA.newSets = 100;
		configA.mutations = 0;
		configA.recombinations = 0;
		
		
		GeneticConfiguration configB = new GeneticConfiguration();
		configB.amountOfSetKept = 100;
		configB.generations = 100;
		configB.newSets = 50;
		configB.mutations = 25;
		configB.recombinations = 25;
		
		GeneticConfiguration configC = new GeneticConfiguration();
		configC.amountOfSetKept = 100;
		configC.generations = 100;
		configC.newSets = 50;
		configC.mutations = 25;
		configC.recombinations = 25;
		configC.type = DistributionType.BALANCED;
		
		WordSetValuator d1Valuator = new AverageDifferentiationGroupsValuator();
		list.add(new GeneticComparativeFinder(d1Valuator, SELECT, configA));
		list.add(new GeneticComparativeFinder(d1Valuator, SELECT, configB));

		*/
		return list;
	}
	
	
}
