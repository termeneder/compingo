package bram.lingo.standardwordfinder;

public enum SortOrder {
	
	ASC, DESC;
	
	public static SortOrder getSortOrderFromSelectAndBest(Select select, SortOrder best) {
		if (select == Select.WORST) {
			if (best == SortOrder.ASC) {
				return SortOrder.DESC;
			} else {
				return SortOrder.ASC;
			}
		} else {
			return best;
		}
	}
	

}