package bram.lingo.standardwordfinder.partialexhaustivefinder.xmlobjects;

import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class Running implements Iterable<Query>{

	@XmlElement(name = "query")
	public List<Query> queryList;

	@Override
	public Iterator<Query> iterator() {
		return queryList.iterator();
	}
	
}
