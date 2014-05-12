package bram.lingo.standardwordfinder.valuator;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

import bram.lingo.words.Word;
public class TestPossibleWordWithPositiveInformationCalculator {

	private Word aaa = new Word("aaa");
	private Word abc = new Word("abc");
	private Word acb = new Word("acb");
	private Word abd = new Word("abd");
	private Word adc = new Word("adc");
	private Word add = new Word("add");
	private Word bbb = new Word("bbb");
	private Word dad = new Word("dad");
	private Word dbd = new Word("dbd");
	private Word ddd = new Word("ddd");
	private Word def = new Word("def");
	private Word aabb = new Word("aabb");
	private Word bbcc = new Word("bbcc");
	
	@Test
	public void testSimpeExample() {
		WordInformation wordInformation = new WordInformation(abc);
		wordInformation.addProbeWord(abc);
		PossibleWordWithPositiveInformationCalculator calculator = new PossibleWordWithPositiveInformationCalculator(wordInformation);
		assertThat(calculator.calculate(), is(1l));
	}
	
	@Test
	public void testMissingInformation() {
		WordInformation wordInformation = new WordInformation(abc);
		wordInformation.addProbeWord(abd);
		PossibleWordWithPositiveInformationCalculator calculator = new PossibleWordWithPositiveInformationCalculator(wordInformation);
		assertThat(calculator.calculate(), is(27l));
	}
	
	@Test
	public void testMissedInformation() {
		WordInformation wordInformation = new WordInformation(abc);
		wordInformation.addProbeWord(dad);
		PossibleWordWithPositiveInformationCalculator calculator = new PossibleWordWithPositiveInformationCalculator(wordInformation);
		assertThat(calculator.calculate(), is(2187l));
	}
	
	@Test
	public void testMultipleMissedInformation() {
		WordInformation wordInformation = new WordInformation(abc);
		wordInformation.addProbeWord(acb);
		PossibleWordWithPositiveInformationCalculator calculator = new PossibleWordWithPositiveInformationCalculator(wordInformation);
		assertThat(calculator.calculate(), is(2l));
	}
	
	@Test
	public void testMultipleInstancesOfIdenticalMissedLetter() {
		WordInformation wordInformation = new WordInformation(aabb);
		wordInformation.addProbeWord(bbcc);
		PossibleWordWithPositiveInformationCalculator calculator = new PossibleWordWithPositiveInformationCalculator(wordInformation);
		assertThat(calculator.calculate(), is(4161l));
	}
}
