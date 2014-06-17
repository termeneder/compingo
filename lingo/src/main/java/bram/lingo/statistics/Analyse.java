package bram.lingo.statistics;

import bram.lingo.resultobjects.ResultQuery;
import bram.lingo.resultobjects.Results;
import bram.lingo.resultobjects.ResultQuery.CalculationType;

public class Analyse {

	public static void main(String[] args) {
		Results results = Results.materialiseSavedInstance();
		trysomething(results);
	}

	private static void trysomething(Results results) {
		Results filteredResults = new ResultFilter()
				.keepWordLength(5)
				.keepCalculationType(CalculationType.Exhaustive)
				.keepAlgorithm("G1")
				.keepSubsetSize(3)
				.filter(results);
		Results filteredResults2 = new ResultFilter()
			.keepWordLength(5)
			.keepCalculationType(CalculationType.Exhaustive)
			.keepAlgorithm("G1")
			.keepSubsetSize(2)
			.filter(results);
		for (ResultQuery q : filteredResults2) {
			System.out.println(q.algorithm + "," + q.wordlength + "," + q.bestscore);
		}
		System.out.println();
		for (ResultQuery q : filteredResults) {
			System.out.println(q.algorithm + "," + q.wordlength + "," + q.bestscore);
		}
	}
	
}
