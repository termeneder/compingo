package bram.lingo.standardwordfinder.genetic.distributor;

import java.util.HashMap;
import java.util.Map;

import bram.lingo.standardwordfinder.genetic.GeneticConfiguration;

public class BalancedGenerationDistributor implements GenerationDistributor {

	private final int MINIMAL_SIZE = 10;
	private final int TOTAL_SIZE;
	private final int REMEMBER_LAST = 1000;
	
	private Map<IndividualType, Integer> c_amounts;
	private Map<IndividualType, IndividualMemory> c_lastIndividuals;
	
	public BalancedGenerationDistributor(GeneticConfiguration config) {
		TOTAL_SIZE = config.amountOfSetKept;
		
		c_amounts = new HashMap<IndividualType, Integer>();
		c_amounts.put(IndividualType.RANDOM, config.newSets);
		c_amounts.put(IndividualType.MUTATION, config.mutations);
		c_amounts.put(IndividualType.RECOMBINATION, config.recombinations);
		
		c_lastIndividuals = new HashMap<IndividualType, IndividualMemory>();
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
		int totalCorrect = getTotalCorrect();
		if (totalCorrect == 0) {
			assignEqualAmounts();
		} else {
			int surplus = TOTAL_SIZE - (MINIMAL_SIZE * c_amounts.size());
			for (IndividualType type : c_amounts.keySet()) {
				int newAmount = calculateNewAmount(type, totalCorrect, surplus);
				c_amounts.put(type, newAmount);
			}
		}
		flattenAmounts();
	}

	
	private int calculateNewAmount(IndividualType type, int totalCorrect, int surplus) {
		int amountCorrect = c_lastIndividuals.get(type).getCorrect();
		double ratio = (double)amountCorrect/(double)totalCorrect;
		int newAmount = (int)(ratio*(double)surplus) + MINIMAL_SIZE;
		return newAmount;
		
	}

	private void assignEqualAmounts() {
		int average = TOTAL_SIZE/c_amounts.size();
		for (IndividualType type : c_amounts.keySet()) {
			c_amounts.put(type,average);
		}
	}
	
	private void flattenAmounts() {
		int totalAmount = getTotalAmount();
		
		for (IndividualType type : c_amounts.keySet()) {
			if (totalAmount == TOTAL_SIZE) {
				break;
			} else if (totalAmount < TOTAL_SIZE) {
				c_amounts.put(type,c_amounts.get(type)+1);
				totalAmount++;
			} else {
				c_amounts.put(type,c_amounts.get(type)-1);
				totalAmount--;
			}
			
		}
	}

	private int getTotalAmount() {
		int totalAmount = 0;
		for (Integer amount : c_amounts.values()) {
			totalAmount+=amount;
		}
		return totalAmount;
	}
	
	
	private int getTotalCorrect() {
		int totalCorrect = 0;
		for (IndividualMemory memory : c_lastIndividuals.values()) {
			totalCorrect += memory.getCorrect();
		}
		return totalCorrect;
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
