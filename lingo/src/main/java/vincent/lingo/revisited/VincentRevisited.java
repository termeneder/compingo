package vincent.lingo.revisited;
/**

 * Generate the best starting words for the game of Lingo
 * Copyright (C) 2012 Vincent Tunru
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General private License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General private License for more details.
 * 
 * You should have received a copy of the GNU General private License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

import java.util.HashMap;
import java.util.Map;

import bram.lingo.standardwordfinder.OptimalWordSets;
import bram.lingo.standardwordfinder.SortOrder;
import bram.lingo.standardwordfinder.StandardWordSetFinder;
import bram.lingo.words.Letter;
import bram.lingo.words.Word;
import bram.lingo.words.wordSets.WordSet;

/**
 * Generate a list of good starting words according to a hand-crafted algorithm
 */
public class VincentRevisited extends StandardWordSetFinder{

	private SortOrder c_order;
	private WordSet c_blackList;
	
	public VincentRevisited(SortOrder order) {
		c_order = order;
		createBlackList();
	}

	@Override
	public OptimalWordSets findOptimal(WordSet totalSet) {
		WordSet filteredSet = filterSet(totalSet);
		WordSet bestSet = getNBestWords(filteredSet, getSubsetSize());
		OptimalWordSets optimalWordSets = new OptimalWordSets(c_order);
		if (!containsNull(bestSet)) {
			optimalWordSets.tryNewWordSet(bestSet, 1);
		}
		return optimalWordSets;
	}



	@Override
	public String getCode() {
		return "I1";
	}

	@Override
	public String getDescription() {
		return "Vincents algorithm";
	}
	
	private boolean containsNull(WordSet set) {
		for (Word word : set) {
			if (word == null) {
				return true;
			}
		}
		return false;
	}
	
	private WordSet filterSet(WordSet unfilteredSet) {
		WordSet filteredSet = new WordSet();
		for (Word word : unfilteredSet) {
			if (!c_blackList.contains(word)) {
				filteredSet.addWord(word);
			}
		}
		return filteredSet;
	}
	
	private void createBlackList() {
		c_blackList = new WordSet();
		String[] blacklist = {"eindtrap", "slotrace", "werkland",
				"clubshow", "jeukbult", "topkunst",
				"diets", "nulrij",
				"omruil",
				"busramp", "eindmix", "kamptijd", "kampdag", "kopzijde", "loopdag", "pechdag", "topmerk",
				"accupack", "foutcode", "schepbek", "thuismap", "werkhuis", "wormkuur", "ypresien"
				};
		for (String wordInBlacklist : blacklist) {
			c_blackList.addWord(wordInBlacklist);
		}
		
	}

	private Map<Word, Integer> getWordScores(WordSet totalSet,
			int[] letterCount,
			WordSet bestWords)
	{
		Map<Word, Integer> wordScores = new HashMap<Word, Integer>();
		for(Word word : totalSet){
			if (bestWords.contains(word)) {
				continue;
			}
			int score = 0;
			String foundChars = word.getLetter(0).toString();
			
			// NOTE: This loop starts at 1 because we skip the first letter.
			//       We already know this letter, so it should not result in a higher score.
			scoreLetters:
			for(int i = 1; i < word.length(); i++){
				
				// Letters already found in the rest of the word are no good
				if(foundChars.indexOf(word.getLetter(i).toString()) != -1){
					continue scoreLetters;
				}
				// If this letter was already present in other words,
				// it's no good either
				for(Word betterWord : bestWords){
					if(betterWord.containsLetter(word.getLetter(i))){
						continue scoreLetters;
					}
				}
				
				// Add the amount of times this letter appears,
				// for words of this length starting with this letter,
				// to the score of the word this letter appeared in.
				score += letterCount[letterToInt(word.getLetter(i))];
				foundChars += word.getLetter(i).toString();
			}
			wordScores.put(word, score);
		}
		
		return wordScores;
	}
	
	/**
	 * Retrieve lists of the best starting words, taking those found in previous lists into account
	 * @param set 
	 * @param n The amount of lists to generate
	 * @return n lists of best starting words for each first letter and each length
	 */
	private WordSet getNBestWords(WordSet set, int n){
		WordSet bestWords = new WordSet();
		int[] letterCountForSet = countLetters(set);
		for(int i = 0; i < n; i++){
			Word nextBestWord = getBestWord(getWordScores(set, letterCountForSet, bestWords));
			bestWords.addWord(nextBestWord);
		}
		return bestWords;
	}
	
	private Word getBestWord(Map<Word, Integer> wordScores){
		Word bestWord = null;
		
		int bestScore = getWorstScorePossible();
		// Each time, find the bestNth word
		for(Word word : wordScores.keySet()){
			// If this word has a better score than the one found up til now
			// consider this the best word found until now.
			if(isBetterThanCurrentBest(wordScores.get(word), bestScore))
			{
				bestScore = wordScores.get(word);
				bestWord = word;
			}
		}

		
		return bestWord;
	}
	
	private boolean isBetterThanCurrentBest(int score, int currentBestScore) {
		if (c_order == SortOrder.ASC) {
			return currentBestScore < score;
		} else {
			return score < currentBestScore;
		}
		
	}

	private int getWorstScorePossible() {
		if (c_order == SortOrder.ASC) {
			return Integer.MIN_VALUE;
		} else {
			return Integer.MAX_VALUE;
		}
	} 

	private int[] countLetters(WordSet totalSet){
		// Now count how often each letter is present for words with certain first letters
		int[] letterCount = new int[27];
		int character;
		for(Word word : totalSet){
			// View each letter of this word
			// NOTE: This loop starts at 1 because we can skip the first letter.
			//       Every word that starts with this letter contains it, so we need not count it.
			for(int k = 1; k < word.length(); k++){
				character = letterToInt(word.getLetter(k));
				// Invalid character, just skip it (probably won't influence the results that much)
				if(-1 == character){
					continue;
				}
				letterCount[character]++;
			}
		}
		return letterCount;
	}
	
	private int letterToInt(Letter letter) {
		return letter.ordinal();
	}


}
