package bram.lingo.input;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ManualInput implements Input {

	public static final String NEW_LINE_SIGNAL = "> ";
	
	public String getLine() {
		System.out.print(NEW_LINE_SIGNAL);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = null;
		try {
			input = br.readLine();
		} catch (IOException e) {
			System.out.println("WHOAAAA, AN ERROR OCCURRED AND YOU DID NOT WRITE SOMETHING FOR IT");
		}
		return input;
	}
	
}
