package bram.lingo.words.wordSets;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.Iterator;

import org.junit.Test;


public class WordSubsetTest {

	@Test
	public void simpleSubsetIsDerived() {
		WordSet superset = new WordSet();
		superset.addWord("a");
		superset.addWord("b");
		superset.addWord("c");
		superset.addWord("d");
		WordSubsetIterable subsetList = new WordSubsetIterable(superset, 2);
		Iterator<WordSet> subsetIterator = subsetList.iterator();
		WordSet subset1 = subsetIterator.next();
		assertThat(subset1.toString(), is("a,b"));
		WordSet subset2 = subsetIterator.next();
		assertThat(subset2.toString(), is("a,c"));
		WordSet subset3 = subsetIterator.next();
		assertThat(subset3.toString(), is("a,d"));
		WordSet subset4 = subsetIterator.next();
		assertThat(subset4.toString(), is("b,c"));
		WordSet subset5 = subsetIterator.next();
		assertThat(subset5.toString(), is("b,d"));
		WordSet subset6 = subsetIterator.next();
		assertThat(subset6.toString(), is("c,d"));
		assertFalse(subsetIterator.hasNext());
		
	}
	
	
}
