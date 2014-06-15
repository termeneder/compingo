package bram.lingo.resultobjects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class Set implements Iterable<String>{

	public Set() {
		wordList = new ArrayList<String>();
	}
	
	@XmlElement(name = "word")
	public List<String> wordList;

	@Override
	public Iterator<String> iterator() {
		return wordList.iterator();
	}

	public void addWord(String word) {
		wordList.add(word);
	}
}
