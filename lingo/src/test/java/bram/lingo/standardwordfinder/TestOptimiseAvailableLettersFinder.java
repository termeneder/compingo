package bram.lingo.standardwordfinder;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

import bram.lingo.standardwordfinder.OptimalWordSets.SortOrder;
import bram.lingo.words.wordSets.WordSet;

public class TestOptimiseAvailableLettersFinder {

	@Test
	public void simpleCaseWorks() {
		WordSet superset = new WordSet();
		superset.addWord("abc");
		superset.addWord("def");
		superset.addWord("ghi");
		
		OptimiseAvailableLettersFinder finder = new OptimiseAvailableLettersFinder(superset, 1, SortOrder.ASC);
		
		OptimalWordSets optimal = finder.findOptimal(superset);
		
		assertThat(optimal.getOptimalValue(), is(1d));
	}
	
	@Test
	public void caseWithDoubleLetters() {
		WordSet superset = new WordSet();
		superset.addWord("abc");
		superset.addWord("aef");
		superset.addWord("ghi");
		
		OptimiseAvailableLettersFinder finder = new OptimiseAvailableLettersFinder(superset, 1, SortOrder.ASC);
		
		OptimalWordSets optimal = finder.findOptimal(superset);
		
		assertThat(optimal.getOptimalValue(), is(4d/3d));
	}
	
	@Test
	public void caseWithDoubleLettersInOneWord() {
		WordSet superset = new WordSet();
		superset.addWord("aac");
		superset.addWord("aef");
		superset.addWord("aeg");
		
		OptimiseAvailableLettersFinder finder = new OptimiseAvailableLettersFinder(superset, 1, SortOrder.ASC);
		
		OptimalWordSets optimal = finder.findOptimal(superset);
		assertThat(optimal.getOptimalValue(), is(2d));
	}
}
