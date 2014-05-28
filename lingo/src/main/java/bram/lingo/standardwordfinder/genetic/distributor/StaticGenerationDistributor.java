package bram.lingo.standardwordfinder.genetic.distributor;

import java.util.HashMap;
import java.util.Map;

import bram.lingo.standardwordfinder.genetic.GeneticConfiguration;

public class StaticGenerationDistributor implements GenerationDistributor {

	private Map<IndividualType, Integer> c_amounts;

	
	public StaticGenerationDistributor(GeneticConfiguration config) {
		c_amounts = new HashMap<IndividualType, Integer>();
		c_amounts.put(IndividualType.RANDOM, config.newSets);
		c_amounts.put(IndividualType.MUTATION, config.mutations);
		c_amounts.put(IndividualType.RECOMBINATION, config.recombinations);
	}

	@Override
	public int getAmount(IndividualType individualType) {
		return c_amounts.get(individualType);
	}

	@Override
	public void addFeedback(IndividualType geneType, boolean succesful) {
		// Don't update
	}

	@Override
	public void updateDistribution() {
		// Don't update
		
	}


}
