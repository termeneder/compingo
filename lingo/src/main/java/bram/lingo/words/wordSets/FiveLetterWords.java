package bram.lingo.words.wordSets;

import bram.lingo.importer.FileToWordSetImporter;

public class FiveLetterWords extends StaticWordSet {
	
	private static final String LOCATION = "src/main/resources/wordlists/5.txt";
	private static FiveLetterWords singleton;
	
	
	private FiveLetterWords(WordSet set) {
		super(set);
	}

	
	public static FiveLetterWords getInstance() {
		if (singleton == null) {
			WordSet set = new FileToWordSetImporter(LOCATION).read();
			singleton = new FiveLetterWords(set);
		}
		return singleton;
	}
	
}
