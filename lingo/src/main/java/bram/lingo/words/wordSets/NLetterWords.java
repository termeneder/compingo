package bram.lingo.words.wordSets;


public class NLetterWords {
	
	public static StaticWordSet getInstance(int n, Source source) {
		switch (n) {
		case 5 : return FiveLetterWords.getInstance(source);
		case 6 : return SixLetterWords.getInstance(source);
		case 7 : return SevenLetterWords.getInstance(source);
		case 8 : return EightLetterWords.getInstance(source);
		default : return null;
		}
	}
	


}
