package bram.lingo.resultobjects;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "results")
public class PartialResults {
	
	public PartialResults() {
		running = new Running();
		finished = new Finished();
	}
	
	public Running running;
	
	public Finished finished;

	public void moveQueryToFinished(RunningQuery query) {
		FinishedQuery finishedQuery = new FinishedQuery(query);
		finished.add(finishedQuery);
		running.remove(query);
	}

}
