package bram.lingo.standardwordfinder.partialexhaustivefinder.xmlobjects;

import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class Indices implements Iterable<Integer>{

	@XmlElement(name = "index")
	public List<Integer> indexList;

	@Override
	public Iterator<Integer> iterator() {
		return indexList.iterator();
	}
	
}
