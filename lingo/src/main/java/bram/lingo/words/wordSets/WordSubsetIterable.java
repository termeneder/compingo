package bram.lingo.words.wordSets;

import java.util.Iterator;


public class WordSubsetIterable implements Iterable<WordSet> {

	private final WordSet c_superset;
	private final int c_subsetSize;
	
	
	
	public WordSubsetIterable(WordSet superset, int subsetSize) {
		c_superset = superset;
		c_subsetSize = subsetSize;
	}
	

	public Iterator<WordSet> iterator() {
		
		return new SubsetIterator(c_superset, c_subsetSize);
	}

	
	public static class SubsetIterator implements Iterator<WordSet> {

		private WordSet c_superset;
		private int[] c_subsetIndices;
		private int c_subsetSize;
		
		private SubsetIterator(WordSet superset, int subsetSize) {
			c_superset = superset;
			c_subsetSize = subsetSize;	
			makeInitialSubsetIndices();
		}
				
		private void makeInitialSubsetIndices() {
			if ( c_superset.size() < c_subsetSize) {
				c_subsetIndices = null;
			} else {
				c_subsetIndices = new int[c_subsetSize];
				for (int index = 0 ; index < c_subsetSize ; index++) {
					c_subsetIndices[index] = index;
				}
			}
		}

		public boolean hasNext() {
			
			return c_subsetIndices != null;
		}

		public WordSet next() {
			WordSet subset = new WordSet();
			for (int i = 0 ; i < c_subsetIndices.length ; i++) {
				int index = c_subsetIndices[i];
				subset.addWord(c_superset.get(index));
			}
			nextSubsetIndices();
			return subset;
		}

		private void nextSubsetIndices() {
			int lastIndex = c_subsetIndices[c_subsetSize-1];
			if (lastIndex != c_superset.size()-1) {
				c_subsetIndices[c_subsetSize - 1] = lastIndex+1;
			} else {
				int resetLastNValues = 0;
				for (int position = c_subsetSize - 1 ; 0 <= position ; position--) {
					int valueAtPosition = c_subsetIndices[position];
					
					int valueToReset = c_superset.size() - c_subsetSize + position;
					if (valueAtPosition == valueToReset) {
						resetLastNValues++;
					} else {
						break;
					}
				}
				
				if (resetLastNValues == c_subsetSize) {
					c_subsetIndices = null;
				} else {
					int lastNotResetedIndex = c_subsetSize - 1 - resetLastNValues;
					int valueOfLastNotResetedIndex = c_subsetIndices[lastNotResetedIndex];
					for (int i = 0 ; i < resetLastNValues + 1 ; i++) {
						c_subsetIndices[lastNotResetedIndex+i] = valueOfLastNotResetedIndex + i + 1;
					}
					
				}
				
			}
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
		
	}
	
}
