package bram.lingo.importer;

import java.io.File;

import bram.lingo.exporter.WordSetToFileExporter;
import bram.lingo.words.Word;
import bram.lingo.words.wordSets.WordSet;

public class ImportTUe {

	private static final String EXPORT_LOCATION = "src/main/resources/wordlists/tue/";
	private static final String OPENTAAL_LIST_LOCATION = "src/main/resources/wordlists/tue/woorden.med";
	private static final int SHORTEST_WORD = 5;
	private static final int LONGEST_WORD = 8;
	
	
	public static void main(String[] args) {
		WordSet tueWordSet = readWordSet();
		WordSet filteredSet = filterWordSet(tueWordSet);
		for (int wordLength = SHORTEST_WORD ; wordLength < LONGEST_WORD + 1 ; wordLength++) {
			WordSet wordsOfLength = filterWordLength(filteredSet, wordLength);
			String exportFilename = Integer.toString(wordLength) + ".txt";
			exportWordList(wordsOfLength, exportFilename);
		}
	}

	
	private static void exportWordList(WordSet wordSet,
			String exportFilename) {
		File exportMap = new File(EXPORT_LOCATION);
		WordSetToFileExporter exporter = new WordSetToFileExporter(wordSet, exportMap, exportFilename);
		exporter.write();
	}


	private static WordSet filterWordLength(WordSet totalSet, int wordLength) {
		WordSet wordSetOfLength = new WordSet();
		for (Word word : totalSet) {
			if (word.length() == wordLength) {
				wordSetOfLength.addWord(word);
			}
		}
		return wordSetOfLength;
	}


	private static WordSet filterWordSet(WordSet unfilteredSet) {
		WordSet filteredSet = new WordSet();
		for (Word word : unfilteredSet) {
			if (word.isCorrectLingoWord()) {
				filteredSet.addWord(word);
			}
		}
		return filteredSet;
	}

	private static WordSet readWordSet() {
		File file = new File(OPENTAAL_LIST_LOCATION);
		FileToWordSetImporter importer = new FileToWordSetImporter(file);
		return importer.read();
	}
	
}
