package bram.lingo.standardwordfinder.partialexhaustivefinder.xmlobjects;

public class FinishedQuery {
	
	FinishedQuery() {
		
	}
	
	public FinishedQuery(Query query) {
		startingletter = query.startingletter;
		wordset = query.wordset;
		wordlength = query.wordlength;
		algorithm = query.algorithm;
		subsetsize = query.subsetsize;
		sortorder = query.sortorder;
		bestscore = query.bestscore;
		bestsets = query.bestsets;
		System.out.println("FINISHED");
		printvalue = query.createPrintValue(true);
		System.out.println(printvalue);
	}
	public String startingletter;
	public String wordset;
	public int wordlength;
	public String algorithm;
	public int subsetsize;
	public String sortorder;
	
	public Double bestscore;
	public BestSets bestsets;
	public String printvalue;
	

}
