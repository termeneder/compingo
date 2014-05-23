package bram.lingo.wordsettester;

import bram.lingo.input.Input;
import bram.lingo.input.ManualInput;
import bram.lingo.words.wordSets.WordSet;

public class WordSetInput implements Input {

	private Input c_manualInput;
	private WordSet c_wordSet;
	private int c_index;
	
	public WordSetInput(WordSet wordSet) {
		c_wordSet = wordSet;
		c_manualInput = new ManualInput();
		c_index = 0;
	}
	
	@Override
	public String getLine() {
		String line;
		if (c_index < c_wordSet.size()) {
			System.out.println(ManualInput.NEW_LINE_SIGNAL + c_wordSet.get(c_index));
			line = c_wordSet.get(c_index).toString();
		} else {
			line = c_manualInput.getLine();
		}
		c_index++;
		return line;
	}

}
