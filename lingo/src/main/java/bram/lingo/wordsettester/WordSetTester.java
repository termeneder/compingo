package bram.lingo.wordsettester;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import bram.lingo.words.wordSets.WordSet;

public class WordSetTester {

	private WordSet c_totalWordSet;
	private List<TestWordSet> c_testWordSets;
	
	public WordSetTester(WordSet totalWordSet) {
		c_totalWordSet = totalWordSet;
		c_testWordSets = new ArrayList<TestWordSet>();
	}

	public WordSetTester(FileReader csv) throws IOException {
		BufferedReader br = new BufferedReader(csv);
		String firstLine = br.readLine();
		readHeaderLine(firstLine);
		//String line = br.r
		
	}
	
	private void readHeaderLine(String line) {
		//TODO
	}
	
	public void addTestSet(String testSetName, WordSet testSet) {
		TestWordSet newTestWordSet = new TestWordSet(testSetName, testSet, c_totalWordSet);
		c_testWordSets.add(newTestWordSet);
	}


	public void runTests(int amountOfTests) {
		for (int i = 0 ; i < amountOfTests ; i++) {
			runSingleTest();
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
		Collections.sort(c_testWordSets);
		for (TestWordSet set : c_testWordSets) {
			System.out.println(set);
		}
	}

	
	
}
