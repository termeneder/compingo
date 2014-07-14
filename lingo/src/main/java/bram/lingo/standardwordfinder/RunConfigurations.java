package bram.lingo.standardwordfinder;

import java.util.HashMap;
import java.util.Map;

import bram.lingo.standardwordfinder.genetic.GeneticConfiguration;
import bram.lingo.standardwordfinder.genetic.distributor.DistributionFactory.DistributionType;
import bram.lingo.words.Letter;
import bram.lingo.words.wordSets.Source;

public class RunConfigurations {

	public enum Algorithm {A3,B3,C1,C2,D2,E1,F1,G1,H1,I1,J2}
	public enum RunType {None, Default, Exhaustive, Genetic1K, Genetic100K, Genetic1M}
	
	public final String fileLocation = "src/main/resources/result/";
	public final String runningPrefix = "running_";
	public final String descriptionPrefix = "I_Algorithm_";
	public final int wordLength = 6;
	public final Source source = Source.OTTUE;
	public final boolean printToFile = false;
	public final boolean appendTimestampToFilename = true;
	public final boolean printTime = false;
	public final int minSubsetSize = 1;
	public final int maxSubsetSize = 4;
	public final Select select = Select.BEST;
	public final boolean runAllLetters = true;
	public final Letter[] lettersToRun = {Letter.a, Letter.b, Letter.c, Letter.d
			, Letter.e, Letter.f, Letter.g, Letter.h, Letter.i, Letter.k, Letter.l
			, Letter.m, Letter.n, Letter.o, Letter.p
			, Letter.r, Letter.s, Letter.t, Letter.u, Letter.v, Letter.w
			,Letter.z};
	public final Map<Algorithm, RunType> algorithms = setAlgorithms();
	
	public GeneticConfiguration geneticConfig1K = createConfigN(1000);
	public GeneticConfiguration geneticConfig100K = createConfigN(100000);
	public GeneticConfiguration geneticConfig1M = createConfigN(1000000);
	private GeneticConfiguration createConfigN(int i) {
		GeneticConfiguration config = new GeneticConfiguration();
		config.amountOfSetKept = 100;
		config.generations = i/100;
		config.newSets = 50;
		config.mutations = 25;
		config.recombinations = 25;
		config.type = DistributionType.BALANCED;
		return config;
	}
	
	
	private Map<Algorithm, RunType> setAlgorithms() {
		Map<Algorithm, RunType> algorithmMap = new HashMap<Algorithm, RunType>();
		algorithmMap.put(Algorithm.A3, RunType.None);
		algorithmMap.put(Algorithm.B3, RunType.None);
		algorithmMap.put(Algorithm.C1, RunType.None);
		algorithmMap.put(Algorithm.C2, RunType.None);
		algorithmMap.put(Algorithm.D2, RunType.None);
		algorithmMap.put(Algorithm.E1, RunType.None);
		algorithmMap.put(Algorithm.F1, RunType.None);
		algorithmMap.put(Algorithm.G1, RunType.None);
		algorithmMap.put(Algorithm.H1, RunType.None);
		algorithmMap.put(Algorithm.I1, RunType.Exhaustive);
		algorithmMap.put(Algorithm.J2, RunType.None);
		return algorithmMap;
	}
	
}
