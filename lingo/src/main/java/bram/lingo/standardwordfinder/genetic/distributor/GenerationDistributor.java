package bram.lingo.standardwordfinder.genetic.distributor;

public interface GenerationDistributor {

	public enum IndividualType {RANDOM, MUTATION, RECOMBINATION}
	
	public int getAmount(IndividualType individualType);
	
	public void addFeedback(IndividualType individualType, boolean succesful);
	
	public void updateDistribution();
	
}
