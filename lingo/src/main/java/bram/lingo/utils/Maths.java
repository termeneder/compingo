package bram.lingo.utils;

public class Maths {

	public static long choose(long n, long m) {
	
		return faculty(n)/(faculty(m)*faculty(n-m));
		
	}
	
	public static long faculty(long n) {
		long nFaculty = 1;
		for (int i = 2 ; i <= n ; i++) {
			nFaculty*=i;
		}
		return nFaculty;
	}

	public static long pow(long n, long m) {
		long nToThePowerM = 1;
		for (long i = 0 ; i < m ; i++) {
			nToThePowerM*=n;
		}
		return nToThePowerM;
	}
	
}
