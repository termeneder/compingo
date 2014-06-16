package bram.lingo.importer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import bram.lingo.resultobjects.ResultQuery;
import bram.lingo.resultobjects.Results;

public class ImportResults {

	private static final String READ_LOCATION = "src/main/resources/result/OTTUE";
	private static final String WRITE_LOCATION = "src/main/resources/result/OTTUExml/OTTUE.xml"; 
	private static final String WORD_SET = "OTTUE";
	
	public static void main(String[] args) throws IOException {
		File inputMap = new File(READ_LOCATION);
		List<File> fileList = getFilesRecursively(inputMap);
		Results results = new Results();
		for (File file : fileList) {
			addFileToResults(file, results);
		}
		writeResults(results);
	}

	

	



	private static List<File> getFilesRecursively(File file) {
		if (file.isDirectory()) {
			List<File> fileList = new ArrayList<File>();
			for (File subFile : file.listFiles()) {
				fileList.addAll(getFilesRecursively(subFile));
			}
			return fileList;
		} else {
			List<File> fileList = new ArrayList<File>();
			fileList.add(file);
			return fileList;
		}
	}
	
	private static void addFileToResults(File file, Results results) throws IOException {
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String headerLine = br.readLine();
		String[] headerLineSplit = headerLine.split("(( \\()|(\\)))");
		String startingLetter = headerLineSplit[0];
		Integer totalAmountOfWords = Integer.parseInt(headerLineSplit[1]);
		System.out.println(startingLetter + ": " + totalAmountOfWords);
		String line = br.readLine();
		while (line != null) {
			ResultQuery query = new ResultQuery();
			addDataToQuery(query, startingLetter, totalAmountOfWords, line);
			results.resultList.add(query);
			line = br.readLine();
		}
		br.close();
	}



	private static void addDataToQuery(ResultQuery query,
			String startingLetter, Integer totalAmountOfWords, String line) {
		query.printvalue = line;
		query.startingletter = startingLetter;
		query.wordset = WORD_SET;
	}
	
	private static void writeResults(Results results) {
		try {
			File file = new File(WRITE_LOCATION);
			JAXBContext jaxbContext = JAXBContext.newInstance(Results.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			jaxbMarshaller.marshal(results, file);
		} catch(JAXBException e) {
			throw new RuntimeException("Couldn't write root node.", e);
		}
	}
}
