package bram.lingo.standardwordfinder.genetic;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import bram.lingo.words.wordSets.WordSet;

public class TopResultSet {

	private int c_setSize;
	private SortedMap<Double, List<WordSet>> c_bestWords;
	private int size;
	private double worstValueInTop;
	
	public TopResultSet(int setSize) {
		c_setSize = setSize;
		c_bestWords = new TreeMap<Double,List<WordSet>>();
	}
	
	public void addIfInTop(WordSet wordSet) {
		
	}
	
	
}
