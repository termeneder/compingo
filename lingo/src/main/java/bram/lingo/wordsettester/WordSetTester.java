package bram.lingo.wordsettester;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import bram.lingo.words.Letter;
import bram.lingo.words.LetterUtils;
import bram.lingo.words.wordSets.NLetterWords;
import bram.lingo.words.wordSets.Source;
import bram.lingo.words.wordSets.WordSet;

public class WordSetTester {

	private WordSet c_totalWordSet;
	private List<TestWordSet> c_testWordSets;
	private String c_csvLocation;
	private String c_headerLine;
	public WordSetTester(String csvLocation) throws IOException {
		c_csvLocation = csvLocation;
		FileReader csv = new FileReader(csvLocation);
		BufferedReader br = new BufferedReader(csv);
		String firstLine = br.readLine();
		readHeaderLine(firstLine);
		
		c_testWordSets = new ArrayList<TestWordSet>();
		String line = br.readLine();
		while (line != null) {
			TestWordSet testSet = new TestWordSet(line, c_totalWordSet);
			c_testWordSets.add(testSet);
			line = br.readLine();
		}
		br.close();
	}
	
	private void readHeaderLine(String line) {
		c_headerLine = line;
		String[] headerArguments = line.split(",");
		int wordSize = Integer.parseInt(headerArguments[0]);
		Letter startingLetter = LetterUtils.StringToLetter(headerArguments[1]);
		Source source = Source.StringToSource(headerArguments[2]);
		c_totalWordSet = NLetterWords.getInstance(wordSize, source).getWordsStartingWith(startingLetter);
	}
	
	
	public void runTests(int amountOfTests) {
		for (int i = 0 ; i < amountOfTests ; i++) {
			runSingleTest();
			Collections.sort(c_testWordSets);
			writeToCsv();
		}
		
	}


	private void runSingleTest() {
		TestWordSet randomSet = getRandomSet();
		randomSet.test();
	}


	private TestWordSet getRandomSet() {
		Random rand = new Random();
		int randomIndex = rand.nextInt(c_testWordSets.size());
		return c_testWordSets.get(randomIndex);
	}


	public void printResults() {
		
		for (TestWordSet set : c_testWordSets) {
			System.out.println(set);
		}
	}

	private void writeToCsv() {
		try {
			FileWriter writer = new FileWriter(c_csvLocation);
			BufferedWriter bufferedWriter = new BufferedWriter(writer);
			bufferedWriter.append(c_headerLine);
			for (TestWordSet set : c_testWordSets) {
				bufferedWriter.write("\n"+set.toCSV());
			}
			bufferedWriter.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
