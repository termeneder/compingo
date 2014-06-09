package bram.lingo.standardwordfinder.partialexhaustivefinder;


public class RunPartialExhaustiveFinder {

	public static void main(String[] args) {
		if (args.length != 1) {
			throw new RuntimeException("RunPartialExhaustiveFinder needs 1 parameter");
		}
		String location = args[0];
		PartialExhaustiveFinder finder = new PartialExhaustiveFinder(location);
		finder.run();
	}
	
}
