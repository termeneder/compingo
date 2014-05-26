package bram.lingo.utils;

public class Timer {

	private final long startTime;
	
	public Timer() {
		startTime = System.nanoTime();
	}
	
	public String toString() {
		long currentTime = System.nanoTime();
		long differenceNS = currentTime-startTime;
		long differenceS = differenceNS/1000000000l;
		long seconds = differenceS%60;
		long differenceM = differenceS/60;
		long minutes = differenceM % 60;
		long differenceH = differenceM / 60;
		long hours = differenceH;
		String str = 
				hours + ":" +
				(minutes<10?"0":"") + minutes + ":" +
				(seconds<10?"0":"") + seconds;
		
		return str;
	}
	
	
}
