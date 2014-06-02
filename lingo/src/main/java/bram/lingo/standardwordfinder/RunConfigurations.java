package bram.lingo.standardwordfinder;

import java.util.HashMap;
import java.util.Map;

import bram.lingo.words.Letter;
import bram.lingo.words.wordSets.Source;

public class RunConfigurations {

	public enum Algorithm {A3,B3,C1,C2,D2,E1,F1,G1,H1,I1,J2}
	public enum RunType {None, Default, Exhaustive, GeneticLong, GeneticShort}
	
	public final String fileLocation = "src/main/resources/result/";
	public final String runningPrefix = "running_";
	public final String descriptionPrefix = "J_Algorithm_";
	public final int wordLength = 7;
	public final Source source = Source.OTTUE;
	public final boolean printToFile = true;
	public final boolean appendTimestampToFilename = false;
	public final boolean printTime = true;
	public final int minSubsetSize = 1;
	public final int maxSubsetSize = 3;
	public final Select select = Select.BEST;
	public final boolean runAllLetters = false;
	public final Letter[] lettersToRun = {Letter.ij, Letter.j, Letter.q, Letter.x, Letter.y};
	public final Map<Algorithm, RunType> algorithms = setAlgorithms();
	
	
	private Map<Algorithm, RunType> setAlgorithms() {
		Map<Algorithm, RunType> algorithmMap = new HashMap<Algorithm, RunType>();
		algorithmMap.put(Algorithm.A3, RunType.Exhaustive);
		algorithmMap.put(Algorithm.B3, RunType.Exhaustive);
		algorithmMap.put(Algorithm.C1, RunType.None);
		algorithmMap.put(Algorithm.C2, RunType.Exhaustive);
		algorithmMap.put(Algorithm.D2, RunType.Exhaustive);
		algorithmMap.put(Algorithm.E1, RunType.Exhaustive);
		algorithmMap.put(Algorithm.F1, RunType.Exhaustive);
		algorithmMap.put(Algorithm.G1, RunType.Exhaustive);
		algorithmMap.put(Algorithm.H1, RunType.Exhaustive);
		algorithmMap.put(Algorithm.I1, RunType.Exhaustive);
		algorithmMap.put(Algorithm.J2, RunType.Exhaustive);
		return algorithmMap;
	}
}
