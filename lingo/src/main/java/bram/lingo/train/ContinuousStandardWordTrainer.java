package bram.lingo.train;

public class ContinuousStandardWordTrainer {

	static final int AMOUNT_BETWEEN_FEEDBACK = 25;
	
	public static void main(String[] args) {
		System.out.println("Train");
		train();
	}

	private static void train() {
		StandardWordSet wordSet = new StandardWordSet();
		int amountOfTupels = 0;
		while (true) {
			System.out.println();
			StandardWordTupel tupel = wordSet.getTupel();
			tupel.train();
			amountOfTupels++;
			if (amountOfTupels%AMOUNT_BETWEEN_FEEDBACK == 0) {
				wordSet.printFeedback();
			}
		}
	}
	
}
