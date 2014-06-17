package bram.lingo.resultobjects;



public class ResultQuery extends Query{

	public enum CalculationType {Exhaustive, Genetic, MonteCarlo, Partial}
	
	public CalculationType calculationtype;
	public String calculationParameter;
	
	public Integer totalAmountOfWords;
	
	
}
