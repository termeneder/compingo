package bram.lingo.lingo;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

import bram.lingo.exception.IncorrectWordLengthException;
import bram.lingo.words.Word;

public class TestLingoComparator {

	@Test
	public void testSameWord() {
		Word correctWord = new Word("yolo");
		LingoComparator compare = new LingoComparator(correctWord);
		LingoCompareValue[] values = compare.compare(correctWord);
		assertThat(values[0], is(LingoCompareValue.Correct));
		assertThat(values[1], is(LingoCompareValue.Correct));
		assertThat(values[2], is(LingoCompareValue.Correct));
		assertThat(values[3], is(LingoCompareValue.Correct));
		
	}
	
	@Test
	public void testTotallyIncorrectWord() {
		Word correctWord = new Word("yolo");
		LingoComparator compare = new LingoComparator(correctWord);
		Word differentWord = new Word("apen");
		LingoCompareValue[] values = compare.compare(differentWord);
		assertThat(values[0], is(LingoCompareValue.Incorrect));
		assertThat(values[1], is(LingoCompareValue.Incorrect));
		assertThat(values[2], is(LingoCompareValue.Incorrect));
		assertThat(values[3], is(LingoCompareValue.Incorrect));
	}
	
	@Test
	public void testPartiallyIncorrectWord() {
		Word correctWord = new Word("yolo");
		LingoComparator compare = new LingoComparator(correctWord);
		Word differentWord = new Word("yala");
		LingoCompareValue[] values = compare.compare(differentWord);
		assertThat(values[0], is(LingoCompareValue.Correct));
		assertThat(values[1], is(LingoCompareValue.Incorrect));
		assertThat(values[2], is(LingoCompareValue.Correct));
		assertThat(values[3], is(LingoCompareValue.Incorrect));
	}
	
	@Test
	public void testWordWithMissedLetter() {
		Word correctWord = new Word("yolo");
		LingoComparator compare = new LingoComparator(correctWord);
		Word differentWord = new Word("ayan");
		LingoCompareValue[] values = compare.compare(differentWord);
		assertThat(values[0], is(LingoCompareValue.Incorrect));
		assertThat(values[1], is(LingoCompareValue.Miss));
		assertThat(values[2], is(LingoCompareValue.Incorrect));
		assertThat(values[3], is(LingoCompareValue.Incorrect));
	}
	
	@Test
	public void testWordWithMultipleMissedLetters() {
		Word correctWord = new Word("yolo");
		LingoComparator compare = new LingoComparator(correctWord);
		Word differentWord = new Word("ayal");
		LingoCompareValue[] values = compare.compare(differentWord);
		assertThat(values[0], is(LingoCompareValue.Incorrect));
		assertThat(values[1], is(LingoCompareValue.Miss));
		assertThat(values[2], is(LingoCompareValue.Incorrect));
		assertThat(values[3], is(LingoCompareValue.Miss));
	}
	
	@Test
	public void testWordWithIdenticalMissedLetters() {
		Word correctWord = new Word("yolo");
		LingoComparator compare = new LingoComparator(correctWord);
		Word differentWord = new Word("odor");
		LingoCompareValue[] values = compare.compare(differentWord);
		assertThat(values[0], is(LingoCompareValue.Miss));
		assertThat(values[1], is(LingoCompareValue.Incorrect));
		assertThat(values[2], is(LingoCompareValue.Miss));
		assertThat(values[3], is(LingoCompareValue.Incorrect));
	}
	
	@Test
	public void testWordWithMoreIdenticalLettersInCorrectThanInGuessed() {
		Word correctWord = new Word("yolo");
		LingoComparator compare = new LingoComparator(correctWord);
		Word differentWord = new Word("ogen");
		LingoCompareValue[] values = compare.compare(differentWord);
		assertThat(values[0], is(LingoCompareValue.Miss));
		assertThat(values[1], is(LingoCompareValue.Incorrect));
		assertThat(values[2], is(LingoCompareValue.Incorrect));
		assertThat(values[3], is(LingoCompareValue.Incorrect));
	}
	
	@Test
	public void testWordWithLessIdenticalLettersInCorrectThanInGuessed() {
		Word correctWord = new Word("yole");
		LingoComparator compare = new LingoComparator(correctWord);
		Word differentWord = new Word("ogon");
		LingoCompareValue[] values = compare.compare(differentWord);
		assertThat(values[0], is(LingoCompareValue.Miss));
		assertThat(values[1], is(LingoCompareValue.Incorrect));
		assertThat(values[2], is(LingoCompareValue.Incorrect));
		assertThat(values[3], is(LingoCompareValue.Incorrect));
	}
	
	@Test
	public void testWordWithIdenticalLettersInCorrectPlaceAndMiss() {
		Word correctWord = new Word("yolo");
		LingoComparator compare = new LingoComparator(correctWord);
		Word differentWord = new Word("hoor");
		LingoCompareValue[] values = compare.compare(differentWord);
		assertThat(values[0], is(LingoCompareValue.Incorrect));
		assertThat(values[1], is(LingoCompareValue.Correct));
		assertThat(values[2], is(LingoCompareValue.Miss));
		assertThat(values[3], is(LingoCompareValue.Incorrect));
		
		Word differentWord2 = new Word("oost");
		LingoCompareValue[] values2 = compare.compare(differentWord2);
		assertThat(values2[0], is(LingoCompareValue.Miss));
		assertThat(values2[1], is(LingoCompareValue.Correct));
		assertThat(values2[2], is(LingoCompareValue.Incorrect));
		assertThat(values2[3], is(LingoCompareValue.Incorrect));
	}
	
	@Test
	public void testIncorrectLengthOfWord() {
		Word correctWord = new Word("yolo");
		LingoComparator compare = new LingoComparator(correctWord);
		Word differentWord = new Word("lmfao");
		boolean exceptionThrown = false;
		try {
			compare.compare(differentWord);
		} catch (IncorrectWordLengthException e) {
			exceptionThrown = true;
		}
		assertTrue(exceptionThrown);
	}
}


