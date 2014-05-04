package bram.lingo.input;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Input {

	public static String getLine() {
		System.out.print("> ");
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
