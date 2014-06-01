package bram.lingo.words.wordSets;

public enum Source {
	OPEN_TAAL, TUE, OTTUE;
	
	public static Source StringToSource(String str) {
		switch(str) {
		case "OPEN_TAAL" : return OPEN_TAAL;
		case "TUE" : return TUE;
		case "OTTUE" : return OTTUE;
		default : return null;
		}
	}
	
	
}
