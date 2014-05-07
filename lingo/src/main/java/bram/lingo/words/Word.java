package bram.lingo.words;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Word {

	private final String c_wordString;
	private Letter[] letters;
	
	public Word(String wordString) {
		c_wordString = wordString.trim();
	}
	
	
	public Word(Word word) {
		this(word.toString());
	}


	public String toString() {
		return c_wordString;
	}

	public boolean equals(Object o) {
		if (o instanceof Word) {
			Word w = (Word) o;
			if (w.toString().equals(this.toString())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public boolean isCorrectLingoWord() {
		String correctLingoWordRegex = "[a-z]+";
		return c_wordString.matches(correctLingoWordRegex);
	}
	
	
	public int length() {
		int stringLength = c_wordString.length();
		Pattern ijRegex = Pattern.compile("[Ii][Jj]");
		Matcher ijMatcher = ijRegex.matcher(c_wordString);
		int occurrencesOfIJ = 0;
		while (ijMatcher.find()){
			occurrencesOfIJ ++;
	    }
		return stringLength - occurrencesOfIJ;
	}


	public boolean startsWith(Letter firstLetter) {
		return firstLetter == getLetter(0);
	}


	public Letter[] toLetters() {
		if (letters == null) {
			letters = wordToLetters(c_wordString, length());
		}
		return letters;
	}
	
	public Letter getLetter(int i) {
		return toLetters()[i];
	}
	
	private static Letter[] wordToLetters(String word, int length) {
		Letter[] letters = new Letter[length];
		int index = 0;
		while (word.length() > 0) {
			if (word.startsWith("ij")) {
				letters[index] = Letter.ij;
				word = word.substring(2);
			} else {
				String firstLetter = word.substring(0,1);
				switch(firstLetter) {
				case "a": letters[index] = Letter.a; break;
				case "b": letters[index] = Letter.b; break;
				case "c": letters[index] = Letter.c; break;
				case "d": letters[index] = Letter.d; break;
				case "e": letters[index] = Letter.e; break;
				case "f": letters[index] = Letter.f; break;
				case "g": letters[index] = Letter.g; break;
				case "h": letters[index] = Letter.h; break;
				case "i": letters[index] = Letter.i; break;
				case "j": letters[index] = Letter.j; break;
				case "k": letters[index] = Letter.k; break;
				case "l": letters[index] = Letter.l; break;
				case "m": letters[index] = Letter.m; break;
				case "n": letters[index] = Letter.n; break;
				case "o": letters[index] = Letter.o; break;
				case "p": letters[index] = Letter.p; break;
				case "q": letters[index] = Letter.q; break;
				case "r": letters[index] = Letter.r; break;
				case "s": letters[index] = Letter.s; break;
				case "t": letters[index] = Letter.t; break;
				case "u": letters[index] = Letter.u; break;
				case "v": letters[index] = Letter.v; break;
				case "w": letters[index] = Letter.w; break;
				case "x": letters[index] = Letter.x; break;
				case "y": letters[index] = Letter.y; break;
				case "z": letters[index] = Letter.z; break;
				}
				word = word.substring(1);
			}
			index++;
		}
		return letters;
	}



}
