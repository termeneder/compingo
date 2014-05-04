package bram.lingo.importer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import bram.lingo.words.wordSets.WordSet;

public class FileToWordSetImporter {

	private File c_file;
	
	public FileToWordSetImporter(File file) {
		c_file = file;
	}
	
	public FileToWordSetImporter(String filelocation) {
		c_file = new File(filelocation);
	}
	
	public WordSet read() {
		WordSet wordSet = new WordSet();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(c_file));
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Couldn't read file: " + e, e);
		}
	    try {
	        String line = br.readLine();
	        while (line != null) {
	            wordSet.addWord(line);
	            line = br.readLine();
	        }
	        
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
	        try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
		return wordSet;
	}
}
