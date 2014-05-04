package bram.lingo.train;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.StringUtils;



public class StandardWordSet {

	List<StandardWordTupel> c_tupelList;
	
	public StandardWordSet() {
		c_tupelList = createTupelList();
	}

	private static List<StandardWordTupel> createTupelList() {
		List<StandardWordTupel> tupelList = new ArrayList<StandardWordTupel>();
		tupelList.add(new StandardWordTupel(5, "a", "aster", "ajuin", "afgod"));
		tupelList.add(new StandardWordTupel(5, "b", "boren", "balts", "buiig"));
		tupelList.add(new StandardWordTupel(5, "c", "cameo", "clips", "crank"));
		tupelList.add(new StandardWordTupel(5, "d", "doren", "dulia", "drost"));
		tupelList.add(new StandardWordTupel(5, "e", "ernst", "email", "edoch"));
		tupelList.add(new StandardWordTupel(5, "f", "faire", "futon", "fluks"));
		tupelList.add(new StandardWordTupel(5, "g", "garoe", "gunst", "glimp"));
		tupelList.add(new StandardWordTupel(5, "h", "hoera", "hints", "hulde"));
		tupelList.add(new StandardWordTupel(5, "i", "inert", "imago", "idool"));
		tupelList.add(new StandardWordTupel(5, "j", "janet", "jolig", "judas"));
		tupelList.add(new StandardWordTupel(5, "k", "karen", "klopt", "kruis"));
		tupelList.add(new StandardWordTupel(5, "l", "liane", "lotus", "lijmer"));
		tupelList.add(new StandardWordTupel(5, "m", "maten", "micro", "malus"));
		tupelList.add(new StandardWordTupel(5, "n", "natie", "noord", "negus"));
		tupelList.add(new StandardWordTupel(5, "o", "opera", "onwil", "omdat"));
		tupelList.add(new StandardWordTupel(5, "p", "paren", "polis", "putse"));
		tupelList.add(new StandardWordTupel(5, "q", "quota", "query", "quilt"));
		tupelList.add(new StandardWordTupel(5, "r", "roten", "radijs", "romig"));
		tupelList.add(new StandardWordTupel(5, "s", "salet", "spion", "scrum"));
		tupelList.add(new StandardWordTupel(5, "t", "toean", "trips", "talud"));
		tupelList.add(new StandardWordTupel(5, "u", "urine", "ultra", "uzelf"));
		tupelList.add(new StandardWordTupel(5, "v", "vloer", "vijand", "vuist"));
		tupelList.add(new StandardWordTupel(5, "w", "waren", "whist", "wodka"));
		tupelList.add(new StandardWordTupel(5, "x", "xenon", "xeres"));
		tupelList.add(new StandardWordTupel(5, "y", "yucca", "ylide", "yebra"));
		tupelList.add(new StandardWordTupel(5, "z", "zagen", "zwilk", "zodat"));
		tupelList.add(new StandardWordTupel(5, "ij", "ijszak", "ijsbijl", "ijking"));
		
		tupelList.add(new StandardWordTupel(6, "a", "afstel", "arduin", "aplomb"));
		tupelList.add(new StandardWordTupel(6, "b", "blaren", "bisdom", "bijtrek"));
		tupelList.add(new StandardWordTupel(6, "c", "centra", "chorus", "climax"));
		tupelList.add(new StandardWordTupel(6, "d", "dralen", "dompig", "dijstuk"));
		tupelList.add(new StandardWordTupel(6, "e", "esprit", "eiland", "ecloge"));
		tupelList.add(new StandardWordTupel(6, "f", "finale", "forums", "factum"));
		tupelList.add(new StandardWordTupel(6, "g", "gloren", "gaspit", "gedrum"));
		tupelList.add(new StandardWordTupel(6, "h", "hernia", "holist", "humbug"));
		tupelList.add(new StandardWordTupel(6, "i", "intake", "impuls", "indigo"));
		tupelList.add(new StandardWordTupel(6, "j", "jonker", "jutmis", "jetlag"));
		tupelList.add(new StandardWordTupel(6, "k", "klaren", "korist", "kutwijf"));
		tupelList.add(new StandardWordTupel(6, "l", "langer", "luidop", "lijkmis"));
		tupelList.add(new StandardWordTupel(6, "m", "manier", "molest", "muziek"));
		tupelList.add(new StandardWordTupel(6, "n", "napret", "nonius", "nagalm"));
		tupelList.add(new StandardWordTupel(6, "o", "orante", "opslag", "omnium"));
		tupelList.add(new StandardWordTupel(6, "p", "parten", "paksoi", "pluche"));
		tupelList.add(new StandardWordTupel(6, "q", "quarto", "quiche", "quidam"));
		tupelList.add(new StandardWordTupel(6, "r", "retina", "rombus", "rijglijf"));
		tupelList.add(new StandardWordTupel(6, "s", "snater", "scholp", "signum"));
		tupelList.add(new StandardWordTupel(6, "t", "tronie", "telpas", "tijdvak"));
		tupelList.add(new StandardWordTupel(6, "u", "ultiem", "unfair", "upload"));
		tupelList.add(new StandardWordTupel(6, "v", "vetrol", "visman", "vreugd"));
		tupelList.add(new StandardWordTupel(6, "w", "wraken", "witbol", "wasdag"));
		tupelList.add(new StandardWordTupel(6, "x", "xyleem", "xyleen"));
		tupelList.add(new StandardWordTupel(6, "y", "yuppen", "yellen", "yankee"));
		tupelList.add(new StandardWordTupel(6, "z", "zanger", "zijluik", "zoiets"));
		tupelList.add(new StandardWordTupel(6, "ij", "ijskern", "ijlgoed", "ijkmaat"));
		
		tupelList.add(new StandardWordTupel(7, "a", "alstoen", "afkerig", "adagium"));
		tupelList.add(new StandardWordTupel(7, "b", "baronie", "bloktijd", "bouwsom"));
		tupelList.add(new StandardWordTupel(7, "c", "coaster", "curling", "chamade"));
		tupelList.add(new StandardWordTupel(7, "d", "doerian", "daklijst", "dumping"));
		tupelList.add(new StandardWordTupel(7, "e", "ernstig", "ethanol", "eindrijm"));
		tupelList.add(new StandardWordTupel(7, "f", "flinter", "fanshop", "fiducie"));
		tupelList.add(new StandardWordTupel(7, "g", "graniet", "geldsom", "gijlkuip"));
		tupelList.add(new StandardWordTupel(7, "h", "harpoen", "huislijk", "hoogtijd"));
		tupelList.add(new StandardWordTupel(7, "i", "instore", "inktlap", "ingewijd"));
		tupelList.add(new StandardWordTupel(7, "j", "janboel", "juriste", "jaagpad"));
		tupelList.add(new StandardWordTupel(7, "k", "koralen", "kunstig", "knijprem"));
		tupelList.add(new StandardWordTupel(7, "l", "lansier", "lijkauto", "lompweg"));
		tupelList.add(new StandardWordTupel(7, "m", "minaret", "mollusk", "medicijn"));
		tupelList.add(new StandardWordTupel(7, "n", "naturel", "noestig", "noodkap"));
		tupelList.add(new StandardWordTupel(7, "o", "operand", "oculist", "ongemak"));
		tupelList.add(new StandardWordTupel(7, "p", "planter", "piskous", "pijnboom"));
		tupelList.add(new StandardWordTupel(7, "q", "quilten", "quatsch", "quizzen"));
		tupelList.add(new StandardWordTupel(7, "r", "retinol", "rijksdag", "rumboon"));
		tupelList.add(new StandardWordTupel(7, "s", "stralen", "spuikom", "schijfje"));
		tupelList.add(new StandardWordTupel(7, "t", "toendra", "tolhuis", "tijdperk"));
		tupelList.add(new StandardWordTupel(7, "u", "uraniet", "uilskop", "upgrade"));
		tupelList.add(new StandardWordTupel(7, "v", "viertal", "vanouds", "vijfkamp"));
		tupelList.add(new StandardWordTupel(7, "w", "warnest", "wildrijk", "witgoud"));
		tupelList.add(new StandardWordTupel(7, "x", "xylitol", "xerofyt"));
		tupelList.add(new StandardWordTupel(7, "y", "yogales", "yttrium", "ypsilon"));
		tupelList.add(new StandardWordTupel(7, "z", "zeiknat", "zorglijk", "zesvoud"));
		tupelList.add(new StandardWordTupel(7, "ij", "ijsvloer", "ijscoman", "ijsdikte"));
		
		
		
		return tupelList;
	}
	
	public StandardWordTupel getTupel() {
		while (true) {
			StandardWordTupel tupel = getRandomTupel();
			if (checkOdds(tupel)) {
				return tupel;
			}
		}
	}

	private boolean checkOdds(StandardWordTupel tupel) {
		double odds = tupel.getOdds();
		Random rand = new Random();
		double randomNumber = rand.nextDouble();
		if (randomNumber < odds) {
			return true;
		} else {
			return false;
		}
	}

	public void removeTupel(StandardWordTupel tupel) {
		c_tupelList.remove(tupel);
	}
	
	public boolean isEmpty() {
		return c_tupelList.isEmpty();
	}
	
	public StandardWordTupel getRandomTupel() {
		int amountOfTupels = c_tupelList.size();
		Random rand = new Random();
		int index = rand.nextInt(amountOfTupels);
		return c_tupelList.get(index);
	}

	public void printFeedback() {
		int amountOfTries = 0;
		int amountOfWords = 0;
		int amountIfCorrectWords = 0;
		int amountOfIncorrectWords = 0;
		for (StandardWordTupel tupel : c_tupelList) {
			System.out.println(tupel);
			amountOfTries += tupel.getTries();
			amountOfWords += tupel.getWordsTried();
			amountIfCorrectWords += tupel.getCorrect();
			amountOfIncorrectWords += tupel.getIncorrect();
		}
		String total = StringUtils.rightPad("Totaal", 35);
		total += "pogingen " + StringUtils.rightPad(Integer.toString(amountOfTries), 4);
		total += "correct " + StringUtils.rightPad(Integer.toString(amountIfCorrectWords), 4);
		total += "incorrect " + StringUtils.rightPad(Integer.toString(amountOfIncorrectWords), 4);
		total += "factor " + StringUtils.rightPad(Double.toString((double) amountIfCorrectWords / (double) amountOfWords), 4);
		System.out.println(total);
	}

	public int size() {
		
		return c_tupelList.size();
	}
	
}
