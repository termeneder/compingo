package bram.lingo.statistics;

import java.util.ArrayList;
import java.util.List;

import bram.lingo.resultobjects.ResultQuery;
import bram.lingo.resultobjects.ResultQuery.CalculationType;
import bram.lingo.resultobjects.Results;

public class ResultFilter {

	private List<String> c_algorithmList;
	private List<Integer> c_wordlengthList;
	private List<Integer> c_subsetSizeList;
	private List<CalculationType> c_calculationTypeList;

	public ResultFilter() {
		
	}
	
	public ResultFilter keepAlgorithm(String ... algorithmList) {
		if (c_algorithmList == null) {
			c_algorithmList = new ArrayList<String>();
		}
		for (String algorithm : algorithmList) {
			c_algorithmList.add(algorithm);
		}
		return this;
	}
	
	public ResultFilter keepWordLength(Integer ... wordlengthList) {
		if (c_wordlengthList == null) {
			c_wordlengthList = new ArrayList<Integer>();
		}
		for (Integer wordlength : wordlengthList) {
			c_wordlengthList.add(wordlength);
		}
		return this;
	}
	
	public ResultFilter keepSubsetSize(int ... subsetSizeList) {
		if (c_subsetSizeList == null) {
			c_subsetSizeList = new ArrayList<Integer>();
		}
		for (Integer subsetSize : subsetSizeList) {
			c_subsetSizeList.add(subsetSize);
		}
		return this;
	}
	
	public ResultFilter keepCalculationType(CalculationType ... calculationTypeList) {
		if (c_calculationTypeList == null) {
			c_calculationTypeList = new ArrayList<CalculationType>();
		}
		for (CalculationType calculationType : calculationTypeList) {
			c_calculationTypeList.add(calculationType);
		}
		return this;
	}
	
	public Results filter(Results unfiltered) {
		Results filtered = new Results();
		for (ResultQuery query : unfiltered) {
			if (keep(query)){
				filtered.add(query);
			}
		}
		return filtered;
	}

	private boolean keep(ResultQuery query) {
		if (c_algorithmList != null && !c_algorithmList.contains(query.algorithm)) {
			return false;
		}
		if (c_wordlengthList != null && !c_wordlengthList.contains(query.wordlength)) {
			return false;
		}
		if (c_subsetSizeList != null && !c_subsetSizeList.contains(query.subsetsize)) {
			return false;
		}
		if (c_calculationTypeList != null && c_calculationTypeList.contains(query.calculationParameter)) {
			return false;
		}
		return true;
	}

}
