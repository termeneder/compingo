package bram.lingo.resultobjects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class BestSets implements Iterable<Set>{

	public BestSets() {
		setList = new ArrayList<Set>();
	}
	
	@XmlElement(name = "set")
	public List<Set> setList;

	@Override
	public Iterator<Set> iterator() {
		return setList.iterator();
	}

	
	public int size() {
		return setList.size();
	}


	public void add(Set newSet) {
		setList.add(newSet);
	}
}
