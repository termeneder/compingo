package bram.lingo.standardwordfinder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;

import jochem.lingo.valuators.revisited.AverageAmbiguousGroupSizeValuator;
import jochem.lingo.valuators.revisited.AverageAmbiguousGroupSizeValuatorBram;
import jochem.lingo.valuators.revisited.NonAmbiguityValuator;
import vincent.lingo.revisited.VincentRevisited;
import bram.lingo.standardwordfinder.RunConfigurations.Algorithm;
import bram.lingo.standardwordfinder.RunConfigurations.RunType;
import bram.lingo.standardwordfinder.genetic.GeneticComparativeFinder;
import bram.lingo.standardwordfinder.genetic.GeneticConfiguration;
import bram.lingo.standardwordfinder.genetic.distributor.DistributionFactory.DistributionType;
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
import bram.lingo.words.wordSets.WordSet;
import bram.lingo.words.wordSets.WordSetUtils;

public class Run {

	private static RunConfigurations c_config;
	
	public static void main(String[] args) {
		c_config = new RunConfigurations();
		WordSet words= NLetterWords.getInstance(c_config.wordLength, c_config.source);
		if (!c_config.runAllLetters) {
			words = words.getWordsStartingWith(c_config.lettersToRun);
		}
			
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
			for (int subsetSize = c_config.minSubsetSize ; subsetSize <= c_config.maxSubsetSize ; subsetSize++) {
				output.append(runAlgorithm(algorithm, subsetSize, letterSet));
				writeToFile(output, c_config.runningPrefix + filename);
			}
		}
		writeToFile(output, filename);
		removeFile(c_config.runningPrefix + filename);
	}
	
	private static String runAlgorithm(IStandardWordSetFinder algorithm, int subsetSize, WordSet letterSet) {
		algorithm.setSubsetSize(subsetSize);
		Timer timer = new Timer();
		OptimalWordSets optimalWordSets = algorithm.findOptimal(letterSet);
		System.out.println("\t"+algorithm  +": " + optimalWordSets);
		if (c_config.printTime) {
			System.out.println("\t"+timer);
		}
		return "\t"+algorithm  +": " + optimalWordSets + "\n";
	}
	
	private static String createFilename(Letter letter) {
		String filename = c_config.descriptionPrefix + c_config.wordLength + "_"  + letter + "_" + c_config.source;
		if (c_config.appendTimestampToFilename) {
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
		if (c_config.printToFile) {
			try {
				PrintWriter out = new PrintWriter(c_config.fileLocation + filename);
				out.println(buffer.toString());
				out.close();
			} catch (FileNotFoundException e) {
				throw new RuntimeException("Couldn't write file. " + e, e);
			}
		}
	}
	
	public static void removeFile(String filename) {
		File file = new File(c_config.fileLocation + filename);
		file.delete();
	}
	
	
	private static List<IStandardWordSetFinder> prepareFinderAlgorithms(WordSet letterSet) {
		List<IStandardWordSetFinder> list = new ArrayList<IStandardWordSetFinder>();


		if (c_config.algorithms.get(Algorithm.A3) != RunType.None) {
			WordSetValuator a3Valuator =  new CorrectLetters3Valuator(letterSet);
			list.add(wrapValuator(a3Valuator, c_config.algorithms.get(Algorithm.A3)));
		}
		if (c_config.algorithms.get(Algorithm.B3) != RunType.None) {
			WordSetValuator b3Valuator =  new InformationAboutLetters3Valuator(letterSet);
			list.add(wrapValuator(b3Valuator, c_config.algorithms.get(Algorithm.B3)));
		}
		if (c_config.algorithms.get(Algorithm.C1) != RunType.None) {
			WordSetValuator c1Valuator =  new BiggestDifferentiationGroupValuator();
			list.add(wrapValuator(c1Valuator, c_config.algorithms.get(Algorithm.C1)));
		}
		if (c_config.algorithms.get(Algorithm.C2) != RunType.None) {
			WordSetValuator c2Valuator =  new NonAmbiguityValuator(letterSet);
			list.add(wrapValuator(c2Valuator, c_config.algorithms.get(Algorithm.C2)));
		}
		if (c_config.algorithms.get(Algorithm.D2) != RunType.None) {
			WordSetValuator d3Valuator =  new AverageAmbiguousGroupSizeValuatorBram(letterSet);
			list.add(wrapValuator(d3Valuator, c_config.algorithms.get(Algorithm.D2)));
		}
		if (c_config.algorithms.get(Algorithm.E1) != RunType.None) {
			WordSetValuator e1Valuator = new AveragePossibleWordsValuator();
			list.add(wrapValuator(e1Valuator, c_config.algorithms.get(Algorithm.E1)));
		}
		if (c_config.algorithms.get(Algorithm.F1) != RunType.None) {
			WordSetValuator f1Valuator = new MaximumPossibleWordsValuator();
			list.add(wrapValuator(f1Valuator, c_config.algorithms.get(Algorithm.F1)));
		}
		if (c_config.algorithms.get(Algorithm.G1) != RunType.None) {
			WordSetValuator g1Valuator = new PositiveAveragePossibleWordsValuator();
			list.add(wrapValuator(g1Valuator, c_config.algorithms.get(Algorithm.G1)));
		}
		if (c_config.algorithms.get(Algorithm.H1) != RunType.None) {
			WordSetValuator h1Valuator = new PositiveMaximumPossibleWordsValuator();
			list.add(wrapValuator(h1Valuator, c_config.algorithms.get(Algorithm.H1)));
		}
		if (c_config.algorithms.get(Algorithm.I1) != RunType.None) {
			list.add(new VincentRevisited(c_config.select));
		}
		if (c_config.algorithms.get(Algorithm.J2) != RunType.None) {
			WordSetValuator j2Valuator = new AverageAmbiguousGroupSizeValuator(letterSet);
			list.add(wrapValuator(j2Valuator, c_config.algorithms.get(Algorithm.J2)));
		}

		return list;
	}
	
	private static IStandardWordSetFinder wrapValuator(WordSetValuator valuator, RunType type) {
		
		switch(type) {
		case Exhaustive : return new ExhaustiveComparativeFinder(valuator, c_config.select);
		case Genetic1K : return new GeneticComparativeFinder(valuator, c_config.select, c_config.geneticConfig1K);
		case Genetic100K : return new GeneticComparativeFinder(valuator, c_config.select, c_config.geneticConfig100K);
		case Genetic1M : return new GeneticComparativeFinder(valuator, c_config.select, c_config.geneticConfig1M);
		default : throw new RuntimeException("Can't find wrapper for run time: " + type);
		}
	}
	
}
