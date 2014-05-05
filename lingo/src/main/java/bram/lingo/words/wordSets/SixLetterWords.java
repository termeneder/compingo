package bram.lingo.words.wordSets;

import bram.lingo.importer.FileToWordSetImporter;

public class SixLetterWords extends StaticWordSet {
	
	private static final String LOCATION = "src/main/resources/wordlists/opentaal/6.txt";
	private static SixLetterWords singleton;
	
	
	private SixLetterWords(WordSet set) {
		super(set);
	}

	
	public static SixLetterWords getInstance() {
		if (singleton == null) {
			WordSet set = new FileToWordSetImporter(LOCATION).read();
			singleton = new SixLetterWords(set);
		}
		return singleton;
	}
	
}
