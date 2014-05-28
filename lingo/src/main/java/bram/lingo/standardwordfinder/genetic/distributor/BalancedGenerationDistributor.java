package bram.lingo.standardwordfinder.genetic.distributor;

import java.util.HashMap;
import java.util.Map;

import bram.lingo.standardwordfinder.genetic.GeneticConfiguration;
import bram.lingo.standardwordfinder.genetic.distributor.GenerationDistributor.IndividualType;

public class BalancedGenerationDistributor implements GenerationDistributor {

	private static final int MINIMAL_SIZE = 10;
	private static final int REMEMBER_LAST = 0;
	
	private Map<IndividualType, Integer> c_amounts;
	private Map<IndividualType, IndividualMemory> c_lastIndividuals;
	
	public BalancedGenerationDistributor(GeneticConfiguration config) {
		c_amounts = new HashMap<IndividualType, Integer>();
		c_amounts.put(IndividualType.RANDOM, config.newSets);
		c_amounts.put(IndividualType.MUTATION, config.mutations);
		c_amounts.put(IndividualType.RECOMBINATION, config.recombinations);
		
		c_lastIndividuals.put(IndividualType.RANDOM, new IndividualMemory(REMEMBER_LAST));
		c_lastIndividuals.put(IndividualType.MUTATION, new IndividualMemory(REMEMBER_LAST));
		c_lastIndividuals.put(IndividualType.RECOMBINATION, new IndividualMemory(REMEMBER_LAST));
	}

	@Override
	public int getAmount(IndividualType individualType) {
		return c_amounts.get(individualType);
	}

	@Override
	public void addFeedback(IndividualType individualType,
			boolean succesful) {
		c_lastIndividuals.get(individualType).addIndividual(succesful);
	}
	
	@Override
	public void updateDistribution() {
		
	}

	
	private class IndividualMemory {
		
		private int c_correct;
		private int c_index;
		private boolean[] c_memory;
		public IndividualMemory (int memorySize) {
			c_correct = 0;
			c_index = 0;
			c_memory = new boolean[memorySize];
		}
		
		public void addIndividual(boolean individual) {
			if (individual) {
				c_correct++;
			}
			if (c_memory[c_index]) {
				c_correct--;
			}
			c_memory[c_index] = individual;
			c_index = (c_index+1)%c_memory.length;
		}
		
		public int getCorrect() {
			return c_correct;
		}
	}
}
