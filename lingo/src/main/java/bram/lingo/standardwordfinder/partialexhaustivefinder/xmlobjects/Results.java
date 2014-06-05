package bram.lingo.standardwordfinder.partialexhaustivefinder.xmlobjects;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "results")
public class Results {
	
	public Running running;
	
	public Finished finished;

}
