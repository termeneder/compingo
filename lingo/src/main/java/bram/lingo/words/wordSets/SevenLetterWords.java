package bram.lingo.words.wordSets;

import bram.lingo.importer.FileToWordSetImporter;

public class SevenLetterWords extends StaticWordSet {
	
	private static final String OT_LOCATION = "src/main/resources/wordlists/opentaal/7.txt";
	private static SevenLetterWords otSingleton;
	private static final String TUE_LOCATION = "src/main/resources/wordlists/tue/7.txt";
	private static SevenLetterWords tueSingleton;
	private static final String OTTUE_LOCATION = "src/main/resources/wordlists/ottue/7.txt";
	private static SevenLetterWords otTueSingleton;
	private static final Source defaultSource = Source.OPEN_TAAL;
	
	private SevenLetterWords(WordSet set) {
		super(set);
	}
	
	public static SevenLetterWords getInstance() {
		return getInstance(defaultSource);
	}
	
	public static SevenLetterWords getInstance(Source source) {
		switch (source) {
		case OPEN_TAAL : return getOrGenerateInstance(otSingleton,OT_LOCATION);
		case TUE : return getOrGenerateInstance(tueSingleton,TUE_LOCATION);
		case OTTUE : return getOrGenerateInstance(otTueSingleton,OTTUE_LOCATION);
		default : return null;
		}
	}
	
	private static SevenLetterWords getOrGenerateInstance(SevenLetterWords instance, String location) {
		if (instance == null) {
			WordSet set = new FileToWordSetImporter(location).read();
			instance = new SevenLetterWords(set);
		}
		return instance;
	}
	
}
