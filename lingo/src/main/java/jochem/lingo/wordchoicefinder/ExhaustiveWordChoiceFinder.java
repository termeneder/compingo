package jochem.lingo.wordchoicefinder;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import jochem.lingo.tools.Parsers;
import jochem.lingo.valuators.AverageCorrectCountValuator;
import jochem.mytools.LazySetOfSizedSubsets;
import jochem.mytools.Valuator;



public class ExhaustiveWordChoiceFinder implements WordChoiceFinder {

    private Iterable<Set<String>> possibilities;
    private Valuator<Set<String>> valuator;
    private long nrOfPossibilities;
    private final DecimalFormat df = new DecimalFormat("#.#");
    private static final int PROGRESS_REPORT_INTERVAL = 1000;

    public ExhaustiveWordChoiceFinder(Set<String> words, int nrOfWords, Valuator<Set<String>> valuator) {
        LazySetOfSizedSubsets<String> myPossibilities = new LazySetOfSizedSubsets<String>(new ArrayList<String>(words), nrOfWords);
        this.possibilities = myPossibilities;
        this.valuator = valuator;
        this.nrOfPossibilities = myPossibilities.size();
    }

    public ExhaustiveWordChoiceFinder(Iterable<Set<String>> possibilities, Valuator<Set<String>> valuator, long nrOfPossibilities) {
        this.possibilities = possibilities;
        this.valuator = valuator;
    }

    public Set<Set<String>> getWords() {
        long nano = System.nanoTime();
        double bestScore = Double.NEGATIVE_INFINITY;
        Set<Set<String>> bestChoices = new HashSet<Set<String>>();

        int count = 0;
        for (Set<String> chosenWords : possibilities) {
            double score = valuator.valuate(chosenWords);

            if (score == bestScore) {
                bestChoices.add(chosenWords);
            }
            if (score > bestScore) {
                bestScore = score;
                bestChoices.clear();
                bestChoices.add(chosenWords);
            }
            count++;
            if (nrOfPossibilities >= PROGRESS_REPORT_INTERVAL && count % (nrOfPossibilities / PROGRESS_REPORT_INTERVAL) == 0) {
                System.out.println(String.format("%s%% (%s - %s): %sms, est. %ss",
                        df.format((((double) count) / (double) nrOfPossibilities) * 100), count, nrOfPossibilities,
                        (System.nanoTime() - nano) / 1000000, (System.nanoTime() - nano) / 1000000000 * nrOfPossibilities / count));
            }
        }

        return bestChoices;
    }

    public static void main(String[] args) {
        File f = new File("src\\main\\resources\\wordlists\\ottue\\5.txt");
        Set<String> words = Parsers.parse(f, 5, 'a');

        Valuator<Set<String>> valuator = new AverageCorrectCountValuator(words);
        Iterable<Set<String>> bestAmbiguity = new ExhaustiveWordChoiceFinder(words, 3, valuator).getWords();
        for (Set<String> choice : bestAmbiguity) {
            System.out.println(choice + ", score=" + valuator.valuate(choice));
        }
        System.out.println(bestAmbiguity);
    }

}
