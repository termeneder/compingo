package bram.lingo.words.wordSets;

import bram.lingo.importer.FileToWordSetImporter;

public class EightLetterWords extends StaticWordSet {
	
	private static final String LOCATION = "src/main/resources/wordlists/8.txt";
	private static EightLetterWords singleton;
	
	
	private EightLetterWords(WordSet set) {
		super(set);
	}

	
	public static EightLetterWords getInstance() {
		if (singleton == null) {
			WordSet set = new FileToWordSetImporter(LOCATION).read();
			singleton = new EightLetterWords(set);
		}
		return singleton;
	}
	
}
