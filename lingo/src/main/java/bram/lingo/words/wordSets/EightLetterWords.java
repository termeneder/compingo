package bram.lingo.words.wordSets;

import bram.lingo.importer.FileToWordSetImporter;

public class EightLetterWords extends StaticWordSet {
	
	private static final String OT_LOCATION = "src/main/resources/wordlists/opentaal/8.txt";
	private static EightLetterWords otSingleton;
	private static final String TUE_LOCATION = "src/main/resources/wordlists/tue/8.txt";
	private static EightLetterWords tueSingleton;
	private static final String OTTUE_LOCATION = "src/main/resources/wordlists/ottue/8.txt";
	private static EightLetterWords otTueSingleton;
	private static final Source defaultSource = Source.OPEN_TAAL;
	
	private EightLetterWords(WordSet set) {
		super(set);
	}
	
	public static EightLetterWords getInstance() {
		return getInstance(defaultSource);
	}
	
	public static EightLetterWords getInstance(Source source) {
		switch (source) {
		case OPEN_TAAL : return getOrGenerateInstance(otSingleton,OT_LOCATION);
		case TUE : return getOrGenerateInstance(tueSingleton,TUE_LOCATION);
		case OTTUE : return getOrGenerateInstance(otTueSingleton,OTTUE_LOCATION);
		default : return null;
		}
	}
	
	private static EightLetterWords getOrGenerateInstance(EightLetterWords instance, String location) {
		if (instance == null) {
			WordSet set = new FileToWordSetImporter(location).read();
			instance = new EightLetterWords(set);
		}
		return instance;
	}
	
}
