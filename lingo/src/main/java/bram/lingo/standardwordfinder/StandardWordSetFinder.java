package bram.lingo.standardwordfinder;

public abstract class StandardWordSetFinder implements IStandardWordSetFinder{

	private static final int DEFAULT_SUBSET_SIZE = 3;
	
	private int c_subsetSize = DEFAULT_SUBSET_SIZE;
	
	@Override
	public void setSubsetSize(int subsetSize) {
		c_subsetSize = subsetSize;
	}
	
	@Override
	public int getSubsetSize() {
		return c_subsetSize;
	}
	
	@Override
	public String toString() {
		String str = getCode() + ") " + getDescription() + ", " ;
		if (getSubsetSize() == 1) {
			str += getSubsetSize() + " word";
		} else {
			str += getSubsetSize() + " words";
		}
		return str;
	}
	
}
