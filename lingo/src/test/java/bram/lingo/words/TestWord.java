package bram.lingo.words;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

public class TestWord {

	@Test
	public void ijIsCountedAsOneLetter() {
		Word word = new Word("pijn");
		Integer wordLength = word.length();
		assertThat(wordLength, is(3));
	}
	
	@Test
	public void ijIsCountedAsOneLetterAtBeginningOfWord() {
		Word word = new Word("ijsvogel");
		Integer wordLength = word.length();
		assertThat(wordLength, is(7));
	}
	
	@Test
	public void ijIsCountedAsOneLetterAtEndOfWord() {
		Word word = new Word("abdij");
		Integer wordLength = word.length();
		assertThat(wordLength, is(4));
	}
	
	@Test
	public void ijIsCountedAsOneLetterMultipleTimes() {
		Word word = new Word("vrijblijvend");
		Integer wordLength = word.length();
		assertThat(wordLength, is(10));
	}
	
	@Test
	public void ijIsCountedAsOneLetterWhileCapitalised() {
		Word word = new Word("IJsland");
		Integer wordLength = word.length();
		assertThat(wordLength, is(6));
	}
	
	@Test
	public void correctWordToLetters() {
		Word word = new Word("kees");
		Letter[] letters = word.toLetters();
		assertThat(letters[0], is(Letter.k));
		assertThat(letters[1], is(Letter.e));
		assertThat(letters[2], is(Letter.e));
		assertThat(letters[3], is(Letter.s));
	}
	
	@Test
	public void correctWordToLettersWithIJ() {
		Word word = new Word("schijnheilig");
		Letter[] letters = word.toLetters();
		assertThat(letters[0], is(Letter.s));
		assertThat(letters[1], is(Letter.c));
		assertThat(letters[2], is(Letter.h));
		assertThat(letters[3], is(Letter.ij));
		assertThat(letters[4], is(Letter.n));
		assertThat(letters[5], is(Letter.h));
		assertThat(letters[6], is(Letter.e));
		assertThat(letters[7], is(Letter.i));
		assertThat(letters[8], is(Letter.l));
		assertThat(letters[9], is(Letter.i));
		assertThat(letters[10], is(Letter.g));
		
		Word word2 = new Word("ijsvrij");
		Letter[] letters2 = word2.toLetters();
		assertThat(letters2[0], is(Letter.ij));
		assertThat(letters2[1], is(Letter.s));
		assertThat(letters2[2], is(Letter.v));
		assertThat(letters2[3], is(Letter.r));
		assertThat(letters2[4], is(Letter.ij));
		
	}
	
}
