package bram.lingo.standardwordfinder.partialexhaustivefinder.xmlobjects;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "results")
public class Results {
	
	public Results() {
		running = new Running();
		finished = new Finished();
	}
	
	public Running running;
	
	public Finished finished;

	public void moveQueryToFinished(Query query) {
		FinishedQuery finishedQuery = new FinishedQuery(query);
		finished.add(finishedQuery);
		running.remove(query);
	}

}
