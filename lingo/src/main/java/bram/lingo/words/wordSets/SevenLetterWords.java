package bram.lingo.words.wordSets;

import bram.lingo.importer.FileToWordSetImporter;

public class SevenLetterWords extends StaticWordSet {
	
	private static final String LOCATION = "src/main/resources/wordlists/opentaal/7.txt";
	private static SevenLetterWords singleton;
	
	
	private SevenLetterWords(WordSet set) {
		super(set);
	}

	
	public static SevenLetterWords getInstance() {
		if (singleton == null) {
			WordSet set = new FileToWordSetImporter(LOCATION).read();
			singleton = new SevenLetterWords(set);
		}
		return singleton;
	}
	
}
