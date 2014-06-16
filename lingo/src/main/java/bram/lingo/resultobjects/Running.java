package bram.lingo.resultobjects;

import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class Running implements Iterable<RunningQuery>{

	@XmlElement(name = "query")
	public List<RunningQuery> queryList;

	@Override
	public Iterator<RunningQuery> iterator() {
		return queryList.iterator();
	}

	public void remove(Query query) {
		queryList.remove(query);
		
	}

	public int size() {
		return queryList.size();
	}
	
}
