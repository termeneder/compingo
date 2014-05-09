package bram.lingo.standardwordfinder;

public abstract class StandardWordSetFinder implements IStandardWordSetFinder{

	private int c_subsetSize;
	
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
