package bram.lingo.standardwordfinder.partialexhaustivefinder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import bram.lingo.resultobjects.PartialResults;
import bram.lingo.resultobjects.RunningQuery;

public class PartialExhaustiveFinder {

	private PartialResults c_results;
	private String c_location;
	
	public PartialExhaustiveFinder(String location) {
		c_results = getRoot(location);
		c_location = location;
	}

	public void run() {
		while(hasRunning()) {
			runIteration();

		}
	}
	
	public void runIteration() {
		List<RunningQuery> finishedQueries = new ArrayList<RunningQuery>();
		for (RunningQuery query : c_results.running) {
			if (0 < query.allocation) {
				query.update();
			}
			if (query.finished()) {
				finishedQueries.add(query);
			}
			setRoot(c_location, c_results, "");
			setRoot(c_location, c_results, ".backup");
		}
		for (RunningQuery query : finishedQueries) {
			c_results.moveQueryToFinished(query);
		}
		setRoot(c_location, c_results, "");
		setRoot(c_location, c_results, ".backup");
	}
	
	public PartialResults getResults() {
		return c_results;	
	}


	public boolean hasRunning() {
		for (RunningQuery running : c_results.running) {
			if (running.allocation > 0) {
				return true;
			}
		}
		return false;
	}
	
	
	private PartialResults getRoot(String location) {
		try {
			File file = new File(location);
			JAXBContext jaxbContext = JAXBContext.newInstance(PartialResults.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			PartialResults root = (PartialResults) jaxbUnmarshaller.unmarshal(file);
			return root;
		} catch(JAXBException e) {
			throw new RuntimeException("Couldn't read root node.", e);
		}
	}
	
	private void setRoot(String location, PartialResults results, String postfix) {
		try {
			File file = new File(location + postfix);
			JAXBContext jaxbContext = JAXBContext.newInstance(PartialResults.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			jaxbMarshaller.marshal(results, file);
		} catch(JAXBException e) {
			throw new RuntimeException("Couldn't write root node.", e);
		}
	}

}