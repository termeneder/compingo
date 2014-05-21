package bram.lingo.words.wordSets;

import bram.lingo.importer.FileToWordSetImporter;

public class SixLetterWords extends StaticWordSet {
	
	private static final String OT_LOCATION = "src/main/resources/wordlists/opentaal/6.txt";
	private static SixLetterWords otSingleton;
	private static final String TUE_LOCATION = "src/main/resources/wordlists/tue/6.txt";
	private static SixLetterWords tueSingleton;
	private static final String OTTUE_LOCATION = "src/main/resources/wordlists/ottue/6.txt";
	private static SixLetterWords otTueSingleton;
	private static final Source defaultSource = Source.OPEN_TAAL;
	
	private SixLetterWords(WordSet set) {
		super(set);
	}
	
	public static SixLetterWords getInstance() {
		return getInstance(defaultSource);
	}
	
	public static SixLetterWords getInstance(Source source) {
		switch (source) {
		case OPEN_TAAL : return getOrGenerateInstance(otSingleton,OT_LOCATION);
		case TUE : return getOrGenerateInstance(tueSingleton,TUE_LOCATION);
		case OTTUE : return getOrGenerateInstance(otTueSingleton,OTTUE_LOCATION);
		default : return null;
		}
	}
	
	private static SixLetterWords getOrGenerateInstance(SixLetterWords instance, String location) {
		if (instance == null) {
			WordSet set = new FileToWordSetImporter(location).read();
			instance = new SixLetterWords(set);
		}
		return instance;
	}
}
