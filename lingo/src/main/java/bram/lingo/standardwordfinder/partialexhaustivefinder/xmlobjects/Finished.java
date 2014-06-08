package bram.lingo.standardwordfinder.partialexhaustivefinder.xmlobjects;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class Finished {

	public Finished() {
		finishedqueryList = new ArrayList<FinishedQuery>();
	}
	
	@XmlElement(name = "query")
	public List<FinishedQuery> finishedqueryList;
	
	public void add(FinishedQuery query) {
		finishedqueryList.add(query);
	}
	
}
