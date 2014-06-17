package bram.lingo.importer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.lang.StringUtils;

import bram.lingo.resultobjects.ResultQuery;
import bram.lingo.resultobjects.ResultQuery.CalculationType;
import bram.lingo.resultobjects.Results;

public class ImportResults {

	private static final String READ_LOCATION = "src/main/resources/result/OTTUE";
	private static final String WRITE_LOCATION = "src/main/resources/result/OTTUExml/OTTUE.xml"; 

	
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
		String title = file.getName();
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String headerLine = br.readLine();
		
		String line = br.readLine();
		while (line != null) {
			
			line = line.replace("\t", "");
			if (StringUtils.isNotBlank(line)) {
				ResultQuery query = createQuery(title, headerLine, line);
				results.resultList.add(query);
			}
			line = br.readLine();
		}
		br.close();
	}



	private static ResultQuery createQuery(String fileTitle,
			String headerLine, String line) {
		ResultQuery query = new ResultQuery();
		addHeaderData(query, headerLine);
		addFileTitleData(query, fileTitle);
		addLineData(query, line);
		return query;
	}
	
	


	


	private static void addHeaderData(ResultQuery query, String headerLine) {
		String[] headerLineSplit = headerLine.split("(( \\()|(\\)))");
		String startingLetter = headerLineSplit[0];
		Integer totalAmountOfWords = Integer.parseInt(headerLineSplit[1]);
		query.startingletter = startingLetter;
		query.totalAmountOfWords = totalAmountOfWords;
	}
	
	private static void addFileTitleData(ResultQuery query, String fileTitle) {
		Pattern p = Pattern.compile("(\\d+)_([a-z]+)_([a-zA-Z]+)\\.txt");
		Matcher m = p.matcher(fileTitle);
		if (m.find()) { 
			query.wordlength = Integer.parseInt(m.group(1));
			query.wordset = m.group(3);
		} else {
			throw new RuntimeException("Incorrect file title: " + fileTitle);
		}
	}
	
	private static void addLineData(ResultQuery query, String line) {
		//System.out.println(line);
		Pattern p = Pattern.compile("([A-Z]+\\d+)([a-zA-Z0-9]*)\\).*, (\\d) words?: (.*) \\(([\\d\\.E\\-]*)\\)");
		Matcher m = p.matcher(line);
		if (m.find()) {
			query.algorithm = m.group(1);
			addCalculationType(query, m.group(2));
			query.subsetsize = Integer.parseInt(m.group(3));
			addWords(query, m.group(4));
			query.bestscore = Double.parseDouble(m.group(5));
		} else {
			throw new RuntimeException("Incorrect line: \"" + line + "\"");
		}
		
	}
	



	private static void addCalculationType(ResultQuery query, String calculationString) {
		if (0 < calculationString.length()) {
			if (calculationString.startsWith("p")) {
				query.calculationtype = CalculationType.Partial;
				calculationString = calculationString.substring(1);
			} else if (calculationString.startsWith("g")) {
				query.calculationtype = CalculationType.Genetic;
				calculationString = calculationString.substring(1);
			} else if (calculationString.startsWith("mc")) {
				query.calculationtype = CalculationType.MonteCarlo;
				calculationString = calculationString.substring(2);
			} else {
				throw new RuntimeException("Unknown calculation type: " + calculationString);
			}
			query.calculationParameter = calculationString;
		} else {
			query.calculationtype = CalculationType.Exhaustive;
		}
	}

	private static void addWords(ResultQuery query, String wordsetString) {
		System.out.println(wordsetString);
		String[] wordsetList = wordsetString.split(" || ");
		for (String wordset : wordsetList) {
			System.out.println("\t"+wordset);
		}
		
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
