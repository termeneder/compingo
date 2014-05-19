package bram.lingo.standardwordfinder.genetic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

import bram.lingo.standardwordfinder.OptimalWordSets;
import bram.lingo.standardwordfinder.SortOrder;
import bram.lingo.words.wordSets.WordSet;

public class TopResultSet {

	private int c_maxSize;
	private SortedMap<Double, List<WordSet>> c_bestWords;
	private int c_size;
	private SortOrder c_sortOrder;
	
	public TopResultSet(int setSize, SortOrder order) {
		c_maxSize = setSize;
		c_bestWords = new TreeMap<Double,List<WordSet>>();
		c_sortOrder = order;
		c_size = 0;
	}
	
	public void addIfInTop(double value, WordSet wordSet) {
		if (!alreadyAdded(value, wordSet)) {
			if (size() < c_maxSize) {
				add(value, wordSet);
			} else if (inTop(value)) {
				add(value, wordSet);
				trimSet();
			}
		}
	}
	
	private boolean alreadyAdded(double value, WordSet wordSet) {
		if ( ! c_bestWords.containsKey(value)) {
			return false; 
		}
		List<WordSet> setsWithValue = c_bestWords.get(value);
		
		for (WordSet alreadyAddedSet : setsWithValue) {
			if (alreadyAddedSet.isEquivalent(wordSet)) {
				return true;
			}
		}
		return false;
	}

	private boolean inTop(double value) {
		if (c_sortOrder == SortOrder.ASC) {
			if (c_bestWords.firstKey() <= value) {
				return true;
			} else {
				return false;
			}
		} else if (c_sortOrder == SortOrder.DESC) {
			if (value <= c_bestWords.lastKey()) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
	
	private void add(double value, WordSet wordSet) {
		if (!c_bestWords.containsKey(value)) {
			List<WordSet> newSet = new ArrayList<WordSet>();
			c_bestWords.put(value, newSet);
		}
		List<WordSet> valueSet = c_bestWords.get(value);
		valueSet.add(wordSet);
		c_size++;
	}

	private void trimSet() {
		Double worstValue = null;
		if (c_sortOrder == SortOrder.ASC) {
			worstValue = c_bestWords.firstKey();
		} else if (c_sortOrder == SortOrder.DESC) {
			worstValue = c_bestWords.lastKey();
		}
		List<WordSet> worstSetList = c_bestWords.get(worstValue);
		if (c_maxSize <= size() - worstSetList.size()) {
			c_bestWords.remove(worstValue);
			c_size-=worstSetList.size();
		}
	}

	public int size() {
		return c_size;
	}
	
	public WordSet getRandom() {
		Random rand = new Random();
		int randomIndex = rand.nextInt(size());
		for (List<WordSet> setList : c_bestWords.values()) {
			if (randomIndex < setList.size()) {
				return setList.get(randomIndex);
			}
			randomIndex -= setList.size();
		}
		return null;
	}
	
	public OptimalWordSets getBest() {
		Double bestValue = null;
		if (c_sortOrder == SortOrder.ASC) {
			bestValue = c_bestWords.lastKey();
		} else if (c_sortOrder == SortOrder.DESC) {
			bestValue = c_bestWords.firstKey();
		}
		List<WordSet> bestSetList = c_bestWords.get(bestValue);
		OptimalWordSets optimal = new OptimalWordSets(c_sortOrder);
		for (WordSet set : bestSetList) {
			optimal.tryNewWordSet(set, bestValue);
		}
		return optimal;
	}
	
}
