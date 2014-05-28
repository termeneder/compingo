package bram.lingo.standardwordfinder.genetic;

import bram.lingo.standardwordfinder.genetic.distributor.DistributionFactory.DistributionType;

public class GeneticConfiguration {

	public int generations = 100;
	public int newSets = 50;
	public int mutations = 25;
	public int recombinations = 25;
	public int amountOfSetKept = 100;
	public DistributionType type = DistributionType.STATIC;
	
}
