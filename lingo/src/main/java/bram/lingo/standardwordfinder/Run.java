package bram.lingo.standardwordfinder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import bram.lingo.standardwordfinder.OptimalWordSets.SortOrder;
import bram.lingo.standardwordfinder.valuator.AverageDifferentiationGroupsValuator;
import bram.lingo.standardwordfinder.valuator.BiggestDifferentiationGroupValuator;
import bram.lingo.standardwordfinder.valuator.CorrectLettersValuator;
import bram.lingo.standardwordfinder.valuator.CountPossibleWordsValuator;
import bram.lingo.standardwordfinder.valuator.InformationAboutLettersValuator;
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
	
	public static void main(String[] args) {
		
		WordSet fiveLetterWords = SixLetterWords.getInstance().getWordsStartingWith(Letter.b);
		SortedMap<Letter, WordSet> wordSetMap = WordSetUtils.splitOnStartLetter(fiveLetterWords);
		
		String filename = "6LettersB_OpenTaal_"+dateToString()+".txt";
		StringBuffer output = new StringBuffer();
		
		for (Entry <Letter, WordSet> entry : wordSetMap.entrySet()) {
			
			WordSet letterSet = entry.getValue();
			Letter letter = entry.getKey();
			List<IStandardWordSetFinder> finderAlgorithms = finderAlgorithms(letterSet);
			System.out.println(letter + " ("+letterSet.size()+"):");
			output.append(letter + " ("+letterSet.size()+"):\n");
			for (IStandardWordSetFinder algorithm : finderAlgorithms) {
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
		try {
			PrintWriter out = new PrintWriter(FILE_LOCATION + filename);
			out.println(buffer.toString());
			out.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Couldn't write file. " + e, e);
		}
	}
	
	public static void removeFile(String filename) {
		File file = new File(FILE_LOCATION + filename);
		file.delete();
	}
	
	
	private static List<IStandardWordSetFinder> finderAlgorithms(WordSet letterSet) {
		List<IStandardWordSetFinder> list = new ArrayList<IStandardWordSetFinder>();
		
		//WordSetValuator correctWordSetValuator =  new CorrectLettersValuator();
		//list = addBurteForceAlgoritmsForInputLengths(list, correctWordSetValuator, SortOrder.ASC);
		
		
		list.add(new OptimiseCorrectLettersFinder(letterSet, 1, SortOrder.ASC));
		list.add(new OptimiseCorrectLettersFinder(letterSet, 2, SortOrder.ASC));
		list.add(new OptimiseCorrectLettersFinder(letterSet, 3, SortOrder.ASC));
		
		list.add(new OptimiseAvailableLettersFinder(letterSet, 1, SortOrder.ASC));
		list.add(new OptimiseAvailableLettersFinder(letterSet, 2, SortOrder.ASC));
		list.add(new OptimiseAvailableLettersFinder(letterSet, 3, SortOrder.ASC));
		
		WordSetValuator biggestDifferentiationGroupValuator =  new BiggestDifferentiationGroupValuator();
		list = addBurteForceAlgoritmsForInputLengths(list, biggestDifferentiationGroupValuator, SortOrder.DESC);
		
		WordSetValuator amountOfDifferationGroups = new AverageDifferentiationGroupsValuator();
		list = addBurteForceAlgoritmsForInputLengths(list, amountOfDifferationGroups, SortOrder.DESC);
		
		WordSetValuator countPossibleWords = new CountPossibleWordsValuator();
		list = addBurteForceAlgoritmsForInputLengths(list, countPossibleWords, SortOrder.DESC);

		WordSetValuator minimisePossibleWords = new MaximumPossibleWordsValuator();
		list = addBurteForceAlgoritmsForInputLengths(list, minimisePossibleWords, SortOrder.DESC);
		
		
		return list;
	}
	
	private static List<IStandardWordSetFinder> addBurteForceAlgoritmsForInputLengths(
			List<IStandardWordSetFinder> list,
			WordSetValuator valuator,  
			SortOrder order) {
		BruteForceComparativeFinder oneWordFinder = new BruteForceComparativeFinder(valuator, 1, order);
		list.add(oneWordFinder);
		
		BruteForceComparativeFinder twoWordsFinder = new BruteForceComparativeFinder(valuator, 2, order);
		list.add(twoWordsFinder);
		
		BruteForceComparativeFinder threeWordsFinder = new BruteForceComparativeFinder(valuator, 3, order);
		list.add(threeWordsFinder);
		
		/*StandardWordSetFinder fourWordsFinder = new StandardWordSetFinder(valuator, 4, order);
		map.put(description + ", 4 words",fourWordsFinder);
		*/
		
		return list;
	}
	
}
