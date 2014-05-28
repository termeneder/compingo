package bram.lingo.standardwordfinder.genetic.distributor;

import bram.lingo.standardwordfinder.genetic.GeneticConfiguration;

public class DistributionFactory {

	public enum DistributionType {STATIC, BALANCED}
	
	public static GenerationDistributor get(GeneticConfiguration config) {
		switch (config.type) {
		case STATIC : return new StaticGenerationDistributor(config);
		case BALANCED : return new BalancedGenerationDistributor(config);
		default: return null;
		}
	}
	
}
