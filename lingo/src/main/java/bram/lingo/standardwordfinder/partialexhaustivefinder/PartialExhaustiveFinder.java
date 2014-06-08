package bram.lingo.standardwordfinder.partialexhaustivefinder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import bram.lingo.standardwordfinder.partialexhaustivefinder.xmlobjects.Query;
import bram.lingo.standardwordfinder.partialexhaustivefinder.xmlobjects.Results;

public class PartialExhaustiveFinder {

	private Results c_results;
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
		List<Query> finishedQueries = new ArrayList<Query>();
		for (Query query : c_results.running) {
			query.update();
			if (query.finished()) {
				finishedQueries.add(query);
			}
			setRoot(c_location, c_results, "");
			setRoot(c_location, c_results, ".backup");
		}
		for (Query query : finishedQueries) {
			c_results.moveQueryToFinished(query);
		}
	}


	public Results getResults() {
		return c_results;
		
	}


	public boolean hasRunning() {
		return c_results.running.size() > 0;
	}
	
	
	private Results getRoot(String location) {
		try {
			File file = new File(location);
			JAXBContext jaxbContext = JAXBContext.newInstance(Results.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Results root = (Results) jaxbUnmarshaller.unmarshal(file);
			return root;
		} catch(JAXBException e) {
			throw new RuntimeException("Couldn't read root node.", e);
		}
	}
	
	private void setRoot(String location, Results results, String postfix) {
		try {
			File file = new File(location + postfix);
			JAXBContext jaxbContext = JAXBContext.newInstance(Results.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			jaxbMarshaller.marshal(results, file);
		} catch(JAXBException e) {
			throw new RuntimeException("Couldn't write root node.", e);
		}
	}

	
}
