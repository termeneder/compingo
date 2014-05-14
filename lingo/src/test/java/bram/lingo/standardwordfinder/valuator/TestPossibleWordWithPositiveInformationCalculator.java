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
	private Word bad = new Word("bad");
	private Word bbb = new Word("bbb");
	private Word dad = new Word("dad");
	private Word dbd = new Word("dbd");
	private Word ddd = new Word("ddd");
	private Word def = new Word("def");
	private Word aabb = new Word("aabb");
	private Word bbaa = new Word("bbaa");
	private Word bbac = new Word("bbac");
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
		assertThat(calculator.calculate(), is(2107l));
	}
	
	@Test
	public void testMultipleMissedInformation() {
		WordInformation wordInformation1 = new WordInformation(abc);
		wordInformation1.addProbeWord(acb);
		PossibleWordWithPositiveInformationCalculator calculator1 = new PossibleWordWithPositiveInformationCalculator(wordInformation1);
		assertThat(calculator1.calculate(), is(2l));
		
		WordInformation wordInformation2 = new WordInformation(abc);
		wordInformation2.addProbeWord(bad);
		PossibleWordWithPositiveInformationCalculator calculator2 = new PossibleWordWithPositiveInformationCalculator(wordInformation2);
		assertThat(calculator2.calculate(), is(156l));
		
	}
	
	@Test
	public void testMultipleInstancesOfIdenticalMissedLetter() {
		WordInformation wordInformation1 = new WordInformation(aabb);
		wordInformation1.addProbeWord(bbcc);
		PossibleWordWithPositiveInformationCalculator calculator1 = new PossibleWordWithPositiveInformationCalculator(wordInformation1);
		assertThat(calculator1.calculate(), is(4161l));
		
		WordInformation wordInformation2 = new WordInformation(aabb);
		wordInformation2.addProbeWord(bbaa);
		PossibleWordWithPositiveInformationCalculator calculator2 = new PossibleWordWithPositiveInformationCalculator(wordInformation2);
		assertThat(calculator2.calculate(), is(6l));
		
		WordInformation wordInformation3 = new WordInformation(aabb);
		wordInformation3.addProbeWord(bbac);
		PossibleWordWithPositiveInformationCalculator calculator3 = new PossibleWordWithPositiveInformationCalculator(wordInformation3);
		assertThat(calculator3.calculate(), is(310l));
	}
}
