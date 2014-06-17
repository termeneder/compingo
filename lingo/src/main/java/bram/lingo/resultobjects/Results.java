package bram.lingo.resultobjects;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "results")
public class Results implements Iterable<ResultQuery> {

	public Results() {
		resultList = new ArrayList<ResultQuery>();
	}
	
	@XmlElement(name = "result")
	public List<ResultQuery> resultList;
	
	private static final String READ_LOCATION = "src/main/resources/result/OTTUExml/OTTUE.xml"; 
	public static Results materialiseSavedInstance() {
		try {
			File file = new File(READ_LOCATION);
			JAXBContext jaxbContext = JAXBContext.newInstance(Results.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

			Results root = (Results) jaxbUnmarshaller.unmarshal(file);
			return root;
		} catch(JAXBException e) {
			throw new RuntimeException("Couldn't write root node.", e);
		}
	}
	
	@Override
	public Iterator<ResultQuery> iterator() {
		return resultList.iterator();
	}

	public void add(ResultQuery query) {
		resultList.add(query);
		
	}

	
}
