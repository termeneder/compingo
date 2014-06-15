package bram.lingo.resultobjects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class Indices implements Iterable<Integer>{

	Indices() {
		indexList = new ArrayList<Integer>();
	}
	
	public Indices(int amountOfIndices) {
		indexList = new ArrayList<Integer>();
		for (int index = 0 ; index < amountOfIndices ; index++) {
			indexList.add(index);
		}
	}
	
	@XmlElement(name = "index")
	public List<Integer> indexList;

	@Override
	public Iterator<Integer> iterator() {
		return indexList.iterator();
	}
	
	public int getAmountOfIndices() {
		return indexList.size();
	};
	
	public boolean hasNext(int maxIndex) {
		int expectedIndexForLast = maxIndex-1;
		for (int i = getAmountOfIndices()-1 ; i >= 0 ; i--) {
			if (indexList.get(i) != expectedIndexForLast) {
				return true;
			}
			expectedIndexForLast--;
		}
		return false;
	}
	
	public void nextIndices(int size) {
		int maxIndex = size - 1;
		for (int i = getAmountOfIndices()-1 ; i >= 0 ; i--) {
			int currentIndexI = indexList.get(i);
			if (currentIndexI < maxIndex) {
				int newIndex = currentIndexI+1;
				indexList.set(i, currentIndexI+1);
				for (int indexAfter = i + 1 ; indexAfter < getAmountOfIndices() ; indexAfter++) {
					newIndex++;
					indexList.set(indexAfter, newIndex);
				}
				break;
			}
			maxIndex--;
		}
	}

	public String toString() {
		String str = "";
		for (int i = 0 ; i < getAmountOfIndices() ; i++) {
			if (i!=0) {
				str += ",";
			}
			str += indexList.get(i);
		}
		return str;
	}
	
}
