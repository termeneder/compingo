package bram.lingo.standardwordfinder.partialexhaustivefinder;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import bram.lingo.standardwordfinder.partialexhaustivefinder.xmlobjects.Query;
import bram.lingo.standardwordfinder.partialexhaustivefinder.xmlobjects.Results;

public class RunPartialExhaustiveFinder {

	public static void main(String[] args) {
		if (args.length != 1) {
			throw new RuntimeException("RunPartialExhaustiveFinder needs 1 parameter");
		}
		Results results = getRoot(args[0]);
		for (Query query : results.running) {
			System.out.println(query.startingletter + " " + query.wordset + " " + query.wordlength
					+ " " + query.algorithm + " " + query.subsetsize + " " + query.sortorder  
					+ " " + query.allocation );
			System.out.println(query.indices);
		}
	}
	
	private static Results getRoot(String location) {
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
	
}
