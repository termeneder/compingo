package bram.lingo.words.wordSets;

import bram.lingo.importer.FileToWordSetImporter;

public class FiveLetterWords extends StaticWordSet {
	
	private static final String OT_LOCATION = "src/main/resources/wordlists/opentaal/5.txt";
	private static FiveLetterWords otSingleton;
	private static final String TUE_LOCATION = "src/main/resources/wordlists/tue/5.txt";
	private static FiveLetterWords tueSingleton;
	private static final String OTTUE_LOCATION = "src/main/resources/wordlists/ottue/5.txt";
	private static FiveLetterWords otTueSingleton;
	private static final Source defaultSource = Source.OPEN_TAAL;
	
	private FiveLetterWords(WordSet set) {
		super(set);
	}
	
	public static FiveLetterWords getInstance() {
		return getInstance(defaultSource);
	}
	
	public static FiveLetterWords getInstance(Source source) {
		switch (source) {
		case OPEN_TAAL : return getOrGenerateInstance(otSingleton,OT_LOCATION);
		case TUE : return getOrGenerateInstance(tueSingleton,TUE_LOCATION);
		case OTTUE : return getOrGenerateInstance(otTueSingleton,OTTUE_LOCATION);
		default : return null;
		}
	}
	
	private static FiveLetterWords getOrGenerateInstance(FiveLetterWords instance, String location) {
		if (instance == null) {
			WordSet set = new FileToWordSetImporter(location).read();
			instance = new FiveLetterWords(set);
		}
		return instance;
	}
	

}
