package bram.lingo.importer;

import java.io.File;

import bram.lingo.exporter.WordSetToFileExporter;
import bram.lingo.words.Word;
import bram.lingo.words.wordSets.OrderedWordSet;
import bram.lingo.words.wordSets.WordSet;

public class ImportOTTUe {

	private static final String EXPORT_LOCATION = "src/main/resources/wordlists/ottue/";
	private static final String OPENTAAL_LIST_LOCATION = "src/main/resources/wordlists/opentaal/OpenTaal-210G-basis-gekeurd.txt";
	private static final String TUE_LIST_LOCATION = "src/main/resources/wordlists/tue/woorden.med";
	private static final int[] WORD_LENGTH_LIST = {5,6,7,8,10,12};

	
	
	public static void main(String[] args) {
		WordSet tueWordSet = readWordSet(TUE_LIST_LOCATION);
		WordSet filteredTueSet = filterWordSet(tueWordSet);
		WordSet otWordSet = readWordSet(OPENTAAL_LIST_LOCATION);
		WordSet filteredOtSet = filterWordSet(otWordSet);
		for (int wordLength : WORD_LENGTH_LIST) {
			WordSet tueWordsOfLength = filterWordLength(filteredTueSet, wordLength);
			WordSet otWordsOfLength = filterWordLength(filteredOtSet, wordLength);
			WordSet wordsOfLength = mergeWordSets(tueWordsOfLength, otWordsOfLength);
			String exportFilename = Integer.toString(wordLength) + ".txt";
			exportWordList(wordsOfLength, exportFilename);
		}
	}

	
	private static WordSet mergeWordSets(WordSet wordSet1,
			WordSet wordSet2) {
		WordSet mergedSet = new OrderedWordSet();
		for (Word word : wordSet1) {
			if (!mergedSet.contains(word)) {
				mergedSet.addWord(word);
			}
		}
		for (Word word : wordSet2) {
			if (!mergedSet.contains(word)) {
				mergedSet.addWord(word);
			}
		}
		return mergedSet;
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

	private static WordSet readWordSet(String location) {
		File file = new File(location);
		FileToWordSetImporter importer = new FileToWordSetImporter(file);
		return importer.read();
	}
	
}
