package bram.lingo.standardwordfinder.partialexhaustivefinder;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import bram.lingo.standardwordfinder.partialexhaustivefinder.xmlobjects.Results;

public class RunPartialExhaustiveFinder {

	public static void main(String[] args) {
		if (args.length != 1) {
			throw new RuntimeException("RunPartialExhaustiveFinder needs 1 parameter");
		}
		String location = args[0];
		PartialExhaustiveFinder finder = new PartialExhaustiveFinder(location);
		finder.run();
	}
	
}
