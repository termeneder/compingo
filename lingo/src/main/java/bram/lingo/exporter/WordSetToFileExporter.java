package bram.lingo.exporter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import bram.lingo.words.Word;
import bram.lingo.words.wordSets.WordSet;

public class WordSetToFileExporter {

	private final WordSet c_wordSet;
	private final File c_targetLocation;
	private final String c_filename;
	
	
	public WordSetToFileExporter(WordSet wordSet, File targetLocation, String filename) {
		c_wordSet = wordSet;
		c_targetLocation = targetLocation;
		c_filename = filename;
	}
	
	
	public void write() {
		try {
			PrintWriter out = new PrintWriter(c_targetLocation.getAbsolutePath() + "/"+  c_filename);
			for (Word word : c_wordSet) {
				out.println(word.toString());
			}
			out.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Couldn't write file. " + e, e);
		}
	}
}
