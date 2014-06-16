package bram.lingo.resultobjects;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "results")
public class Results {

	public Results() {
		resultList = new ArrayList<ResultQuery>();
	}
	
	@XmlElement(name = "result")
	public List<ResultQuery> resultList;
	
}
