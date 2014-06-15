package bram.lingo.resultobjects;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "results")
public class Results {

	@XmlElement(name = "result")
	public List<ResultQuery> resultList;
	
}
