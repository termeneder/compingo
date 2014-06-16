package bram.lingo.resultobjects;

public class FinishedQuery extends Query{
	
	private static final boolean PRINT_FINISHED = true;
	
	
	public FinishedQuery(RunningQuery query) {
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
		if (PRINT_FINISHED) {
			System.out.println(wordlength + "," + startingletter );
			System.out.println(printvalue);
		}
	}
	
	

}
