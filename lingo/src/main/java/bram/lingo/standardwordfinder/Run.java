package bram.lingo.standardwordfinder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import bram.lingo.standardwordfinder.StandardWordSetFinder.SortOrder;
import bram.lingo.standardwordfinder.valuator.AmountOfDifferentiationGroupsValuator;
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
		
		WordSet fiveLetterWords = FiveLetterWords.getInstance().getWordsStartingWith("c");
		SortedMap<Letter, WordSet> wordSetMap = WordSetUtils.splitOnStartLetter(fiveLetterWords);
		SortedMap<String, StandardWordSetFinder> finderAlgorithms = finderAlgorithms();
		String filename = "5LetterFinderResultsC_"+dateToString()+".txt";
		StringBuffer output = new StringBuffer();
		
		for (Entry <Letter, WordSet> entry : wordSetMap.entrySet()) {
			WordSet letterSet = entry.getValue();
			Letter letter = entry.getKey();
			System.out.println(letter + " ("+letterSet.size()+"):");
			output.append(letter + " ("+letterSet.size()+"):\n");
			for (Entry <String, StandardWordSetFinder> algorithmEntry : finderAlgorithms.entrySet()) {
				StandardWordSetFinder finder = algorithmEntry.getValue();
				String finderName = algorithmEntry.getKey();
				OptimalWordSets optimalWordSets = finder.findOptimal(letterSet);
				output.append("\t"+finderName+": " + optimalWordSets + "\n");
				writeToFile(output, RUNNING_PREFIX + filename);
				System.out.println("\t"+finderName+": " + optimalWordSets );
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
	
	
	private static SortedMap<String, StandardWordSetFinder> finderAlgorithms() {
		SortedMap<String, StandardWordSetFinder> map = new TreeMap<String, StandardWordSetFinder>();
		
		WordSetValuator correctLettersValuator =  new CorrectLettersValuator();
		map = addAlgoritmsForInputLengths(map, correctLettersValuator, "A) Correct letters", SortOrder.ASC);
		
		WordSetValuator informationAvailableValuator =  new InformationAboutLettersValuator();
		map = addAlgoritmsForInputLengths(map, informationAvailableValuator, "B) Information about letters", SortOrder.ASC);
		
		WordSetValuator biggestDifferentiationGroupValuator =  new BiggestDifferentiationGroupValuator();
		map = addAlgoritmsForInputLengths(map, biggestDifferentiationGroupValuator, "C) Minimise biggest group", SortOrder.DESC);
		
		WordSetValuator amountOfDifferationGroups = new AmountOfDifferentiationGroupsValuator();
		map = addAlgoritmsForInputLengths(map, amountOfDifferationGroups, "D) Get most groups", SortOrder.ASC);
		
		WordSetValuator countPossibleWords = new CountPossibleWordsValuator();
		map = addAlgoritmsForInputLengths(map, countPossibleWords, "E) Count possible words", SortOrder.DESC);

		WordSetValuator minimisePossibleWords = new MaximumPossibleWordsValuator();
		map = addAlgoritmsForInputLengths(map, minimisePossibleWords, "F) Minimise possible words", SortOrder.DESC);
		
		return map;
	}
	
	private static SortedMap<String, StandardWordSetFinder> addAlgoritmsForInputLengths(
			SortedMap<String, StandardWordSetFinder> map,
			WordSetValuator valuator, 
			String description, 
			SortOrder order) {
		StandardWordSetFinder oneWordFinder = new StandardWordSetFinder(valuator, 1, order);
		map.put(description + ", 1 word", oneWordFinder);
		
		StandardWordSetFinder twoWordsFinder = new StandardWordSetFinder(valuator, 2, order);
		map.put(description + ", 2 words",twoWordsFinder);
		
		StandardWordSetFinder threeWordsFinder = new StandardWordSetFinder(valuator, 3, order);
		map.put(description + ", 3 words",threeWordsFinder);
		
		/*StandardWordSetFinder fourWordsFinder = new StandardWordSetFinder(valuator, 4, order);
		map.put(description + ", 4 words",fourWordsFinder);
		*/
		
		return map;
	}
	
}
