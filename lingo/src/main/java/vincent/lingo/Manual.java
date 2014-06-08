package vincent.lingo;
/**

 * Generate the best starting words for the game of Lingo
 * Copyright (C) 2012 Vincent Tunru
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Generate a list of good starting words according to a hand-crafted algorithm
 */
public class Manual {

	// Length of the shortest and longest words to look for
	int minLength = 5;
	int maxLength = 8;
	
	String[] wordLists = {
			//"corpus" + File.separator + "hunspell-generated",
			//"corpus" + File.separator + "OpenTaal-210G-basis-ongekeurd.txt",
			//"corpus" + File.separator + "Wiktionary-1000basiswoorden.txt"
			"src/main/resources/wordlists/opentaal/5.txt",
			"src/main/resources/wordlists/opentaal/6.txt",
			"src/main/resources/wordlists/opentaal/7.txt",
			"src/main/resources/wordlists/opentaal/8.txt"
			};

	String[] blacklist = {"eindtrap", "slotrace", "werkland",
			"clubshow", "jeukbult", "topkunst",
			"diets", "nulrij",
			"omruil",
			"busramp", "eindmix", "kamptijd", "kampdag", "kopzijde", "loopdag", "pechdag", "topmerk",
			"accupack", "foutcode", "schepbek", "thuismap", "werkhuis", "wormkuur", "ypresien"
			};
	
	
	ArrayList<String>[][] words;
	
	/**
	 * Run the application!
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		Manual woordenRater = new Manual();
		
//		System.out.print(woordenRater.getReadableLetterCount());
		
//		System.out.print(woordenRater.getReadableBestWords());
		
		System.out.println(woordenRater.getNBestWordsAsCsv(woordenRater.getNBestWords(3)));
	}

	/**
	 * Load the words from the corpora, re-using previously loaded words if available
	 * 
	 * @see #loadWords(boolean)
	 * @return An array containing the words from the corpora
	 */
	public ArrayList<String>[][] loadWords(){
		return loadWords(false);
	}
	
	/**
	 * Load the words from the corpora into an array
	 * 
	 * @param refresh Whether to re-use previously loaded words if available
	 * @return An array containing the words from the corpora
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<String>[][] loadWords(boolean refresh){

		if(null != words && refresh != true){
			return words;
		}
		
		words = new ArrayList[maxLength + 1][27];
		
		BufferedReader br;
		int firstLetter;
		
		int amountOfWords = 0;
		
		for(String wordList : wordLists){

			try {
				FileReader fr = new FileReader(wordList);
				br = new BufferedReader(fr);
				
				readWords : for(String word = br.readLine(); word != null; word = br.readLine()){
					// "ij" is a single letter, represented by unicode ĳ
					word = word.replace("ij", "ĳ");
					
					// hunspell files contain suffic information after '/'
					// This can be disregarded for our purposes
//					if(word.indexOf('/') != -1){
//						word = word.substring(0, word.indexOf('/'));
//					}
					
					// Skip words that are not of the required length
					if(word.length() < minLength || word.length() > maxLength){
						continue;
					}
					
					// Skip words that start with a capital letter
					if(Character.isUpperCase(word.charAt(0))){
						continue;
					}
					
					// Skip words with invaild letters
					for(int i = 0; i < word.length(); i++){
						if(-1 == charToInt(word.charAt(i))){
							continue readWords;
						}
					}
					
					firstLetter = charToInt(word.charAt(0));
					
					if(null == words[word.length()][firstLetter]){
						words[word.length()][firstLetter] = new ArrayList<String>();
					}
					
					// Skip blacklisted words
					if(Arrays.asList(blacklist).contains(word.replace("ĳ", "ij"))){
						continue;
					}
					
					// Prevent duplicate words
					if(words[word.length()][firstLetter].contains(word)){
						continue;
					}
					
					amountOfWords++;
					words[word.length()][firstLetter].add(word);
				}
				br.close();
			} catch (FileNotFoundException e) {
				System.out.println("Oops, file not found: " + wordList);
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("Oops, weird error for file " + wordList);
				e.printStackTrace();
			}
		}
		
		System.out.println("Fun fact: " + amountOfWords + " valid words loaded!");
		
		return words;
	}
	
	/**
	 * @see Manual#getWordScores(ArrayList[][], int[][][])
	 * @return A map of words and their scores
	 */
	public HashMap<String, Integer>[][] getWordScores(){
		ArrayList<String>[][] words = loadWords();
		return getWordScores(words, countLetters(words));
	}
	
	/**
	 * Get the scores of words for each first letter, without taking previous words into account
	 * 
	 * @see #getWordScores(ArrayList[][], int[][][], String[][][])
	 * @param words The list of words to score, order by word length and first letter
	 * @param letterCount The number of times each letter appears in words of a certain length, starting with a certain letter
	 * @return A map of words and their scores
	 */
	public HashMap<String, Integer>[][] getWordScores(ArrayList<String>[][] words, int[][][] letterCount){
		return getWordScores(words, letterCount, null);
	}
	
	/**
	 * Get the scores of words for each letter, optionally taking previous words into account
	 * @param words The list of words to score, ordered by word length and first letter
	 * @param letterCount The number of times each letter appears in words of a certain length, starting with a certain letter
	 * @param nBetterWords Previously found word scores, which interfere with the scores of these words. Leave null to disregard.
	 * @return A map of words and their scores
	 */
	public HashMap<String, Integer>[][] getWordScores(ArrayList<String>[][] words,
			int[][][] letterCount,
			String[][][] nBetterWords)
	{
		HashMap<String, Integer>[][] wordScores = new HashMap[maxLength + 1][27];
		
		int score;
		String foundChars;
		
		for(int length = minLength; length <= maxLength; length++){
			for(int firstLetter = 0; firstLetter < 27; firstLetter++){
				wordScores[length][firstLetter] = new HashMap<String, Integer>();
				findBestWord:
				for(String word : words[length][firstLetter]){
					score = 0;
					foundChars = word.substring(0, 1);
					
					// NOTE: This loop starts at 1 because we skip the first letter.
					//       We already know this letter, so it should not result in a higher score.
					scoreLetters:
					for(int i = 1; i < word.length(); i++){
						// Discard words with disallowed letters
						if(charToInt(word.charAt(i)) == -1){
							continue findBestWord;
						}
						
						// Letters already found in the rest of the word are no good
						if(foundChars.indexOf(word.charAt(i)) != -1){
							continue scoreLetters;
						}
						// If this letter was already present in other words,
						// it's no good either
						if(null != nBetterWords){
							for(String[][] betterWords : nBetterWords){
								if(null == betterWords){
									break;
								}
								if(null == betterWords[length][firstLetter]){
									continue;
								}
								if(betterWords[length][firstLetter].indexOf(word.charAt(i)) != -1){
									continue scoreLetters;
								}
							}
						}
						
						// Add the amount of times this letter appears,
						// for words of this length starting with this letter,
						// to the score of the word this letter appeared in.
						score += letterCount[length][firstLetter][charToInt(word.charAt(i))];
						foundChars += word.charAt(i);
					}
					
					wordScores[length][firstLetter].put(word, score);
				}
			}
		}
		
		return wordScores;
	}
	
	/**
	 * Retrieve lists of the best starting words, taking those found in previous lists into account
	 * @param n The amount of lists to generate
	 * @return n lists of best starting words for each first letter and each length
	 */
	public String[][][] getNBestWords(int n){
		String[][][] nBestWords = new String[n][][];
		
		ArrayList<String>[][] words = loadWords();
		int[][][] letterCount = countLetters(words);
		
		for(int i = 0; i < n; i++){
			nBestWords[i] = getBestWords(getWordScores(words, letterCount, nBestWords));
		}
		
		return nBestWords;
	}
	
	/**
	 * Get a list of the best starting words, loading a fresh set of words and scoring them
	 * @return List of the best starting words for each length and each first letter
	 */
	public String[][] getBestWords(){
		return getBestWords(getWordScores());
	}
	
	/**
	 * Given a set of words and their scores, find out which words are best
	 * @param wordScores A map of words and their scores
	 * @return An array of the best word to start with for each length and first letter
	 */
	public String[][] getBestWords(HashMap<String, Integer>[][] wordScores){
		String[][] bestWords = new String[maxLength + 1][27];
		
		int bestScore;
		
		for(int length = minLength; length <= maxLength; length++){
			for(int firstLetter = 0; firstLetter < 27; firstLetter++){
				bestScore = 0;
				// Each time, find the bestNth word
				for(String word : wordScores[length][firstLetter].keySet()){
					// If this word has a better score than the one found up til now
					// consider this the best word found until now.
					if(wordScores[length][firstLetter].get(word) > bestScore)
					{
						bestScore = wordScores[length][firstLetter].get(word);
						bestWords[length][firstLetter] = word;
					}
				}
			}
		}
		
		return bestWords;
	}
	
	/**
	 * Count the occurrence of each letter for each word length and first letter, generating a fresh set of words
	 * @return An occurrence count of each letter for each word length and first letter
	 */
	public int[][][] countLetters(){
		return this.countLetters(this.loadWords());
	}
	
	/**
	 * Given a set of words, counts how often each letter occurs in words of a certain length having a certain first letter
	 * @param words Set of words to analyse
	 * @return An occurrence count of each letter for each word length and first letter
	 */
	public int[][][] countLetters(ArrayList<String>[][] words){

		
		// Now count how often each letter is present for words with certain first letters
		// (Array structure: [word length][starting letter][character whose occurrence we're tracking]
		int[][][] letterCount = new int[maxLength + 1][27][27];
		
		int character;
		
		// The first level of the array is the length of the words
		for(int i = minLength; i <= maxLength; i++){
			// The second level of the array is the starting letter of the words
			for(int j = 0; j < 27; j++){
				// If there are no words of this length with this starting letter, skip it.
				if(null == words[i][j]){
					continue;
				}
				// Now let's iterate over each word of length i starting with the letter at position j in the alphabet
				for(String word : words[i][j]){
					// View each letter of this word
					// NOTE: This loop starts at 1 because we can skip the first letter.
					//       Every word that starts with this letter contains it, so we need not count it.
					for(int k = 1; k < word.length(); k++){
						character = charToInt(word.charAt(k));
						
						// Invalid character, just skip it (probably won't influence the results that much)
						if(-1 == character){
							continue;
						}
						
						letterCount[i][j][character]++;
					}
				}
			}
		}

		return letterCount;
	}
	
	/**
	 * Nicely format a given array of lists of good starting words
	 * 
	 * @param nBestWords An array containing lists of good starting words, ordered by word length and first letter
	 * @return A human-readable display of those words
	 */
	public String getReadableNBestWords(String[][][] nBestWords){
		String result = "";
		
		int n = 0;
		
		for(String[][] bestWords : nBestWords){
			n++;
			result += "= Round " + n + "=";
			result += getReadableBestWords(bestWords, null) + System.getProperty("line.separator");
		}
		
		return result.replace("ĳ", "ij");
	}
	
	/**
	 * Calculate the best starting words for each word length and letter, and format them nicely
	 * 
	 * @return Human-readable display of the best starting words
	 */
	public String getReadableBestWords(){
		HashMap<String, Integer>[][] wordScores = getWordScores();
		return getReadableBestWords(getBestWords(wordScores), wordScores);
	}
	
	/**
	 * Display a nicely formatted list of the best starting words and their scores
	 * 
	 * @param bestWords The words to display nicely
	 * @param wordScores Optional (otherwise null) map of the score of each word
	 * @return Human-readable display of the given starting words
	 */
	public String getReadableBestWords(String[][] bestWords, HashMap<String, Integer>[][] wordScores){
		String result = "";
		
		for(int length = minLength; length <= maxLength; length++){
			result += System.getProperty("line.separator")+ "== Word length " + length + " ==" + System.getProperty("line.separator");
			for(int firstLetter = 0; firstLetter < 27; firstLetter++){
				result += Character.toString(intToChar(firstLetter)).toUpperCase().replace("ĳ", "IJ") + ": ";
				if(null == bestWords[length][firstLetter]){
					result += System.getProperty("line.separator");
					continue;
				}
				result += bestWords[length][firstLetter];
				if(null != wordScores){
					result += " (" + wordScores[length][firstLetter].get(bestWords[length][firstLetter]) + ")";
				}
				result += System.getProperty("line.separator");
			}
		}
		
		return result.replace("ĳ", "ij");
	}
	
	/**
	 * Output a list of possible words in JSON
	 * 
	 * @return JSON representation of possible words
	 */
	public String getWordsAsJson(){
		return getWordsAsJson(loadWords());
	}
	
	/**
	 * Convert the list of words to JSON to use in, say, Lingo web applications
	 * 
	 * @param words The list of words to convert
	 * @return JSON representation of words
	 */
	public String getWordsAsJson(ArrayList<String>[][] words) {
		String json = "{";
		for(int length = minLength; length <= maxLength; length++){
			json += System.getProperty("line.separator") + "'length" + length + "' : [";
			
			for(int firstLetter = 0; firstLetter < 27; firstLetter++){
				json += System.getProperty("line.separator") + "[";
				getWords : for(String word : words[length][firstLetter]){
					json += System.getProperty("line.separator") + "'" + word.replace("ĳ", "ij") + "',";
				}
				json = json.substring(0, json.length() - 1) + "],";
			}
			
			json = json.substring(0, json.length() - 1) + "],";
		}
		
		json = json.substring(0, json.length() - 1) + "}";
		// TODO Auto-generated method stub
		return json;
	}
	
	public String getNBestWordsAsCsv(String[][][] nBestWords){
		String result = "";
		
		for(String[][] bestWords : nBestWords){
			result += System.getProperty("line.separator") + getBestWordsAsCsv(bestWords);
		}
		
		return result;
	}
	
	/**
	 * Calculate the best startings words for each length and letter, and return them as CSV
	 * @return CSV-formatted best starting words
	 */
	public String getBestWordsAsCsv(){
		return getBestWordsAsCsv(getBestWords());
	}
	
	/**
	 * Format the best starting words as CSV
	 * @param bestWords The words to display
	 * @return Comma-separated list of the best starting words
	 */
	public String getBestWordsAsCsv(String[][] bestWords){
		String result = "";
		
		for(int firstLetter = 0; firstLetter < 27; firstLetter++){
			result += System.getProperty("line.separator") + Character.toString(intToChar(firstLetter)).toUpperCase().replace("ĳ", "IJ");
			
			for(int length = minLength; length <= maxLength; length++){
				if(null == bestWords[length][firstLetter]){
					result += ",";
					continue;
				}
				
				result += "," + bestWords[length][firstLetter].replace("ĳ", "ij");
			}
		}
		
		return result;
	}
	
	/**
	 * Count the number of letters in a freshly generated word list, and display them nicely
	 * 
	 * @return Human-readable display of the occurrence count of each letter for each length and first letter
	 */
	public String getReadableLetterCount(){
		return this.getReadableLetterCount(countLetters());
	}
	
	/**
	 * Given a count of letters, format them nicely
	 * 
	 * @param letterCount Occurrence of letters per word length and first letter
	 * @return Human-readable display of the letter count
	 */
	public String getReadableLetterCount(int[][][] letterCount){
		String result = "";
		
		for(int length = minLength; length <= maxLength; length++){
			result += System.getProperty("line.separator")+ "== Word length " + length + " ==" + System.getProperty("line.separator");
			
			for(int j = 0; j < 27; j++){
				result += Character.toUpperCase(intToChar(j)) + System.getProperty("line.separator");
				
				for(int k = 0; k < 27; k++){
					result += "\t" + Character.toString(intToChar(k)).replace("ĳ", "IJ") + ": " + Character.toUpperCase(letterCount[length][j][k]) + System.getProperty("line.separator");
				}
			}
		}
		
		return result.replace("ĳ", "ij");
	}
	
	/**
	 * @param letter An alphabetic letter
	 * @return Representing number, -1 if an invalid Lingo character
	 */
	public static int charToInt(char letter){
		switch(letter){
		case 'a':
			return 0;
		case 'b':
			return 1;
		case 'c':
			return 2;
		case 'd':
			return 3;
		case 'e':
			return 4;
		case 'f':
			return 5;
		case 'g':
			return 6;
		case 'h':
			return 7;
		case 'i':
			return 8;
		case 'j':
			return 9;
		case 'k':
			return 10;
		case 'l':
			return 11;
		case 'm':
			return 12;
		case 'n':
			return 13;
		case 'o':
			return 14;
		case 'p':
			return 15;
		case 'q':
			return 16;
		case 'r':
			return 17;
		case 's':
			return 18;
		case 't':
			return 19;
		case 'u':
			return 20;
		case 'v':
			return 21;
		case 'w':
			return 22;
		case 'x':
			return 23;
		case 'y':
			return 24;
		case 'z':
			return 25;
		// "ĳ" represents "ij"
		case 'ĳ':
			return 26;
		default:
			return -1;
		}
	}

	/**
	 * @param i A number between 0 and 27 (0 included, 27 excluded)
	 * @return The corresponding, lowercase letter
	 */
	public static char intToChar(int i){
		if(i > 26 || i < 0){
			return ' ';
		}
		
		switch(i){
		case 0:
			return 'a';
		case 1:
			return 'b';
		case 2:
			return 'c';
		case 3:
			return 'd';
		case 4:
			return 'e';
		case 5:
			return 'f';
		case 6:
			return 'g';
		case 7:
			return 'h';
		case 8:
			return 'i';
		case 9:
			return 'j';
		case 10:
			return 'k';
		case 11:
			return 'l';
		case 12:
			return 'm';
		case 13:
			return 'n';
		case 14:
			return 'o';
		case 15:
			return 'p';
		case 16:
			return 'q';
		case 17:
			return 'r';
		case 18:
			return 's';
		case 19:
			return 't';
		case 20:
			return 'u';
		case 21:
			return 'v';
		case 22:
			return 'w';
		case 23:
			return 'x';
		case 24:
			return 'y';
		case 25:
			return 'z';
		// "ĳ" represents "ij"
		case 26:
			return 'ĳ';
		default: return ' ';
		}
		
	}
}
