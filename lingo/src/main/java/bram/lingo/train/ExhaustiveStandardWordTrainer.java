package bram.lingo.train;

public class ExhaustiveStandardWordTrainer {

	private static final int RECORD = 349;
	
	public static void main(String[] args) {
		System.out.println("Train Exhaustive");
		train();
	}

	private static void train() {
		StandardWordSet wordSet = new StandardWordSet();
		int totalAmount = 0;
		int amountIncorrect = 0;
		while (!wordSet.isEmpty()) {
			System.out.println();
			StandardWordTupel tupel = wordSet.getRandomTupel();
			boolean correct = tupel.train();
			if (correct) {
				wordSet.removeTupel(tupel);
				System.out.println("Nog " + wordSet.size());
			} else {
				amountIncorrect++;
			}
			totalAmount++;
		}
		double ratio = (double) amountIncorrect / (double) totalAmount;
		System.out.println("Allemaal in " + totalAmount + " met een ratio van " + ratio);
		if (totalAmount < RECORD) {
			System.out.println("Nieuw record!");
		} else {
			System.out.println("Record: " + RECORD);
		}
	}
	
}
