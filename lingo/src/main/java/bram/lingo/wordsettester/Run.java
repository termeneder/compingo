package bram.lingo.wordsettester;

import java.io.IOException;

public class Run {

	public static void main(String[] args) throws IOException {
		int amountOfTests = 50;
		WordSetTester tester = new WordSetTester("src/main/resources/wordsettest/5_z_OTTUE.csv");
		tester.runTests(amountOfTests);
		tester.printResults();
	}

	
}
