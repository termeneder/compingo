package bram.lingo.words.wordSets;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;


import org.junit.Test;

import bram.lingo.words.Word;
public class TestOrderedWordSet {

	@Test
	public void testCreation() {
		WordSet unorderedSet = createUnorderedSet();
		OrderedWordSet orderedSet = new OrderedWordSet(unorderedSet);
		assertThat(orderedSet.get(0), is(new Word("xyz")));
		assertThat(orderedSet.get(1), is(new Word("abcde")));
		assertThat(orderedSet.get(2), is(new Word("defgh")));
		assertThat(orderedSet.get(3), is(new Word("mnopq")));
		assertThat(orderedSet.get(4), is(new Word("ijsbijl")));
		assertThat(orderedSet.get(5), is(new Word("abcdef")));
	}
	
	@Test
	public void testAddition() {
		WordSet unorderedSet = createUnorderedSet();
		OrderedWordSet orderedSet = new OrderedWordSet(unorderedSet);
		orderedSet.addWord(new Word("hgfed"));
		orderedSet.addWord("cdefg");
		assertThat(orderedSet.get(0), is(new Word("xyz")));
		assertThat(orderedSet.get(1), is(new Word("abcde")));
		assertThat(orderedSet.get(2), is(new Word("cdefg")));
		assertThat(orderedSet.get(3), is(new Word("defgh")));
		assertThat(orderedSet.get(4), is(new Word("hgfed")));
		assertThat(orderedSet.get(5), is(new Word("mnopq")));
		assertThat(orderedSet.get(6), is(new Word("ijsbijl")));
		assertThat(orderedSet.get(7), is(new Word("abcdef")));
	}
	
	@Test
	public void testContains() {
		WordSet unorderedSet = createUnorderedSet();
		OrderedWordSet orderedSet = new OrderedWordSet(unorderedSet);
		assertTrue(orderedSet.contains(new Word("abcde")));
		assertTrue(orderedSet.contains(new Word("mnopq")));
		assertTrue(orderedSet.contains(new Word("abcdef")));
		assertTrue(orderedSet.contains(new Word("xyz")));
		assertFalse(orderedSet.contains(new Word("abc")));
		assertFalse(orderedSet.contains(new Word("bcdef")));
	}
	

	private WordSet createUnorderedSet() {
		WordSet unorderedSet = new WordSet();
		unorderedSet.addWord("mnopq");
		unorderedSet.addWord("abcde");
		unorderedSet.addWord("defgh");
		unorderedSet.addWord("ijsbijl");
		unorderedSet.addWord("abcdef");
		unorderedSet.addWord("xyz");
		return unorderedSet;
	}
	
	
}
