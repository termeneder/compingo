package bram.lingo.standardwordfinder.valuator;

import bram.lingo.words.Word;
import bram.lingo.words.wordSets.WordSet;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

public class TestCountPossibleWordsValuator {

	private Word aaa = new Word("aaa");
	private Word abc = new Word("abc");
	private Word abd = new Word("abd");
	private Word adc = new Word("adc");
	private Word add = new Word("add");
	private Word bbb = new Word("bbb");
	private Word dad = new Word("dad");
	private Word dbd = new Word("dbd");
	private Word ddd = new Word("ddd");
	private Word def = new Word("def");

	
	@Test
	public void correctWordIsChecked()  {
		AveragePossibleWordsValuator valuator = new AveragePossibleWordsValuator();

		WordSet totalSet = new WordSet();
		totalSet.addWord(abc);
		WordSet subset = new WordSet();
		subset.addWord(abc);
	
		double value = valuator.value(totalSet, subset);
		
		assertThat(value, is(1d));
	}

	@Test
	public void wordWithAllCorrectInfoIsChecked() {
		AveragePossibleWordsValuator valuator = new AveragePossibleWordsValuator();

		WordSet totalSet = new WordSet();
		totalSet.addWord(abc);
		WordSet subset = new WordSet();
		subset.addWord(abd);
		subset.addWord(adc);
	
		double value = valuator.value(totalSet, subset);
		
		assertThat(value, is(1d));
	}
	
	@Test
	public void removalOfLettersIsCounted() {
		AveragePossibleWordsValuator valuator = new AveragePossibleWordsValuator();

		WordSet totalSet = new WordSet();
		totalSet.addWord(abc);
		WordSet subset = new WordSet();
		subset.addWord(ddd);
	
		double value = valuator.value(totalSet, subset);
		
		assertThat(value, is(17576d));
		
		WordSet subset2 = new WordSet();
		subset2.addWord(def);
	
		double value2 = valuator.value(totalSet, subset2);
		
		assertThat(value2, is(13824d));
	}
	
	@Test
	public void correctLetterIsAdded() {
		AveragePossibleWordsValuator valuator = new AveragePossibleWordsValuator();

		WordSet totalSet = new WordSet();
		totalSet.addWord(abc);
		WordSet subset = new WordSet();
		subset.addWord(aaa);
	
		double value = valuator.value(totalSet, subset);
		
		assertThat(value, is(676d));
		
		WordSet subset2 = new WordSet();
		subset2.addWord(bbb);
	
		double value2 = valuator.value(totalSet, subset2);
		
		assertThat(value2, is(676d));
	}
	
	@Test
	public void missedLetterIsUsed() {
		AveragePossibleWordsValuator valuator = new AveragePossibleWordsValuator();

		WordSet totalSet = new WordSet();
		totalSet.addWord(abc);
		WordSet subset = new WordSet();
		subset.addWord(dad);
	
		double value = valuator.value(totalSet, subset);
		
		assertThat(value, is(1275d));
	}
	
	@Test
	public void correctLetterIsUsed() {
		AveragePossibleWordsValuator valuator = new AveragePossibleWordsValuator();

		WordSet totalSet = new WordSet();
		totalSet.addWord(abc);
		WordSet subset = new WordSet();
		subset.addWord(add);
	
		double value = valuator.value(totalSet, subset);
		
		assertThat(value, is(676d));
	}
	
	@Test
	public void wordInformationIsMergedUsed() {
		AveragePossibleWordsValuator valuator = new AveragePossibleWordsValuator();

		WordSet totalSet = new WordSet();
		totalSet.addWord(abc);
		WordSet subset = new WordSet();
		subset.addWord(add);
		subset.addWord(dbd);
	
		double value = valuator.value(totalSet, subset);
		
		assertThat(value, is(26d));
	}
	
	@Test
	public void wordWithRepetitiveLettersChecked() {
		AveragePossibleWordsValuator valuator = new AveragePossibleWordsValuator();

		WordSet totalSet = new WordSet();
		totalSet.addWord(aaa);
		WordSet subset = new WordSet();
		subset.addWord(add);
	
		double value = valuator.value(totalSet, subset);
		
		assertThat(value, is(676d));
	}
	
	@Test
	public void multipleWordValuesAreAveraged() {
		AveragePossibleWordsValuator valuator = new AveragePossibleWordsValuator();

		WordSet totalSet = new WordSet();
		totalSet.addWord(abc);
		totalSet.addWord(ddd);
		WordSet subset = new WordSet();
		subset.addWord(add);
		subset.addWord(dbd);
	
		double value = valuator.value(totalSet, subset);
		
		assertThat(value, is(13.5d));
	}
}
