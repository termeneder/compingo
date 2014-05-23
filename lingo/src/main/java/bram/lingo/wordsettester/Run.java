package bram.lingo.wordsettester;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import bram.lingo.words.Letter;
import bram.lingo.words.wordSets.NLetterWords;
import bram.lingo.words.wordSets.Source;
import bram.lingo.words.wordSets.WordSet;

public class Run {

	public static void main(String[] args) throws IOException {
		WordSet totalWordSet = NLetterWords.getInstance(5, Source.OTTUE).getWordsStartingWith(Letter.z);
		int amountOfTests = 50;
		//WordSetTester tester = new WordSetTester(totalWordSet);
		WordSetTester tester = new WordSetTester(new FileReader("src/main/resources/wordsettest/5_z_OTTUE.csv"));
		//tester = addTestSets(tester);
		tester.runTests(amountOfTests);
		tester.printResults();
	}

	private static WordSetTester addTestSets(WordSetTester tester) {
		tester.addTestSet("A_1", new WordSet("zweet"));
		tester.addTestSet("A_2", new WordSet("zenen", "zwelt"));
		tester.addTestSet("A_3", new WordSet("zeelt", "zonen", "zware"));
		tester.addTestSet("B_1", new WordSet("zaten"));
		tester.addTestSet("B_2", new WordSet("zelen", "zowat"));
		tester.addTestSet("B_3", new WordSet("zanik", "zorgt", "zweel"));
		tester.addTestSet("C_1", new WordSet("zeten"));
		tester.addTestSet("C_2", new WordSet("zanik", "zoete"));
		tester.addTestSet("C_3", new WordSet("zadel", "zemig", "zonet"));
		tester.addTestSet("D_1", new WordSet("zenit"));
		tester.addTestSet("D_2", new WordSet("zingt", "zoele"));
		tester.addTestSet("D_3", new WordSet("zanik", "zemel", "zorgt"));
		tester.addTestSet("E_1", new WordSet("zaten"));
		tester.addTestSet("E_2", new WordSet("zogen", "zwakt"));
		tester.addTestSet("E_3", new WordSet("zelen", "zowat", "zurig"));
		tester.addTestSet("F_1", new WordSet("zacht"));
		tester.addTestSet("F_2", new WordSet("zacht", "zenuw"));
		tester.addTestSet("F_3", new WordSet("zedig", "zoals", "zwijnt"));
		tester.addTestSet("G_1", new WordSet("zaten"));
		tester.addTestSet("G_2", new WordSet("zegel", "zowat"));
		tester.addTestSet("G_3", new WordSet("zelen", "zowat", "zurig"));
		tester.addTestSet("H_1", new WordSet("zaagt"));
		tester.addTestSet("H_2", new WordSet("zaagt", "zenuw"));
		tester.addTestSet("H_3", new WordSet("zaden", "zoute", "zwilk"));
		tester.addTestSet("I_1", new WordSet("zaten"));
		tester.addTestSet("I_2", new WordSet("zaten", "zwolg"));
		tester.addTestSet("I_3", new WordSet("zaten", "zwolg", "zurig"));
		return tester;
	}
	
}
