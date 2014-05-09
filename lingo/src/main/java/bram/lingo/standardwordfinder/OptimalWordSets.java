package bram.lingo.standardwordfinder;

import java.util.ArrayList;
import java.util.List;

import bram.lingo.words.wordSets.WordSet;

public class OptimalWordSets {
	public enum SortOrder {ASC, DESC}
	private List<WordSet> c_wordSets;
	private double c_optimalValue;
	private SortOrder c_order;
	
	public OptimalWordSets(SortOrder order) {
		c_order = order;
		c_wordSets = new ArrayList<WordSet>();
		c_optimalValue = getWorstPossibleValue();
	}
	
	public void tryNewWordSet(WordSet set, double value) {
		if (isBetterThanOptimal(value)) {
			c_wordSets = new ArrayList<WordSet>();
			c_wordSets.add(set);
			c_optimalValue = value;
		} else if (equalsOptimal(value)) {
			c_wordSets.add(set);
		}
	}
	
	private double getWorstPossibleValue() {
		if (c_order == SortOrder.ASC) {
			return Double.MIN_VALUE;
		} else {
			return Double.MAX_VALUE;
		}
	}
	
	private boolean isBetterThanOptimal(double value) {
		if (c_order == SortOrder.ASC) {
			if (c_optimalValue < value) {
				return true;
			} else {
				return false;
			}
		} else {
			if (value < c_optimalValue ) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	private boolean equalsOptimal(double value) {
		if (c_optimalValue == value) {
			return true;
		} else {
			return false;
		}
	}
	
	public String toString() {
		String str = "";
		boolean isFirst = true;
		for (WordSet set : c_wordSets) {
			if (isFirst) {
				isFirst = false;
			} else {
				str += " || ";
			}
			str += set.toString();
		}
		str += " (" + c_optimalValue + ")";
		return str;
	}
	
	public double getOptimalValue() {
		return c_optimalValue;
	}
}