package cpen221.mp1;

import com.google.cloud.language.v1.Sentence;
import cpen221.mp1.exceptions.NoSuitableSentenceException;

import cpen221.mp1.sentiments.SentimentAnalysis;
import org.checkerframework.checker.units.qual.A;

import javax.print.Doc;
import java.net.URL;
import java.text.BreakIterator;
import java.util.*;
import java.io.FileReader;
import java.util.Scanner;
import java.io.IOException;
import java.io.BufferedReader;

public class Document {
    public static final Set<Character> SYMBOLS = Set.of('!', '$', '%', '&', '\'', '(', ')', '*', '+', ',', '-', '.',
            '/', ':', '"', ';', '<', '=', '>', '?', '@', '[', '\\', ' ', ']', '^', '_', '`', '{', '|', '}', '~', '#');
    public static final char HASH_TAG = '#';
    public static final Set<Character> SENTENCE_ENDERS = Set.of('!', '.', '?');
    public static final List<Character> PHRASE_BREAKERS = List.of(',', ';', ':');

    String doc_ID;
    String document;
    HashMap<String, Integer> wordCounts;
    int totalWordCount = 0;
    ArrayList<SentenceClass> doc_array;

    public Document(String docId, URL docURL) {
        doc_ID = docId;
        try {
            StringBuilder data = new StringBuilder();
            Scanner urlScanner = new Scanner(docURL.openStream());
            while (urlScanner.hasNext()) {
                data.append(urlScanner.nextLine());
                data.append(" ");
            }
            document = data.toString();

            document = formatString(document);
        }
        catch (IOException ioe) {
            System.out.println("Problem reading file!");
        }

        doc_array = SentenceBreak(document);
    }


    /**
     * @param docId    the document identifier
     * @param fileName the name of the file with the contents of
     *                 the document
     */
    public Document(String docId, String fileName) {
        doc_ID = docId;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            StringBuilder data = new StringBuilder();
            for (String fileLine = reader.readLine(); fileLine != null; fileLine = reader.readLine()) {
                data.append(fileLine);
                data.append(" ");
            }
            document = data.toString();

            document = formatString(document);
            reader.close();
            // TODO: Is this redundant?
            document = formatString(document);
        }
        catch (IOException ioe) {
            System.out.println("Problem reading file!");
        }

        doc_array = SentenceBreak(document);
    }

    private static ArrayList<SentenceClass> SentenceBreak(String text){
        ArrayList<SentenceClass> temporaryDocArray = new ArrayList<SentenceClass>();
        BreakIterator iterator = BreakIterator.getSentenceInstance(Locale.US);
        iterator.setText(text);

        int start = iterator.first();
        for (int end = iterator.next();
             end != BreakIterator.DONE;
             start = end, end = iterator.next()) {

            SentenceClass nextSentence = new SentenceClass(text.substring(start, end));
            temporaryDocArray.add(nextSentence);
        }

        return temporaryDocArray;
    }

    private HashMap<String, Integer> instanceCounter(String seed) {
        HashMap<String, Integer> wordMap = new HashMap<String, Integer>();

        for (int i = 0; i < doc_array.size(); i++) {
            SentenceClass currentSentence = doc_array.get(i);
            for (int k = 0; k < currentSentence.getSentenceLength(); k++) {
                String word = currentSentence.getWord(k);
                if (wordMap.containsKey(word)) {
                    int count = wordMap.get(word);
                    count++;
                    wordMap.replace(word, count);
                } else {
                    wordMap.put(word, 1);
                }
                totalWordCount++;
            }
        }
        return wordCounts;
    }

    public String getDocId() {
        return doc_ID;
    }

    public String formatString(String seed){
        String formattedDoc = seed;

        while (formattedDoc.contains("  ")){
            formattedDoc = formattedDoc.replaceAll("  "," ");
        }
        while (formattedDoc.contains("\n")){
            formattedDoc = formattedDoc.replaceAll("\n","");
        }
        while (formattedDoc.contains("\t")){
            formattedDoc = formattedDoc.replaceAll("\t"," ");
        }

        return formattedDoc;
    }


    /* ------- Task 1 ------- */

    /**
     *
     * @return
     */
    public double averageWordLength() {
        int wordCount = 0;
        return 0.0;
    }

    public double totalWords() {
        return totalWordCount;
    }

    public double uniqueWordRatio() {
        int numUniqueWords = wordCounts.keySet().size();
        return (double) numUniqueWords / totalWordCount;
    }

    public double hapaxLegomanaRatio() {
        ArrayList<Integer> counts = new ArrayList<Integer>(wordCounts.values());
        int countExactlyOnce = 0;
        for (int i = 0; i < counts.size(); i++) {
            if (counts.get(i) == 1) {
                countExactlyOnce++;
            }
        }

        return (double) countExactlyOnce / totalWordCount;
    }

    /* ------- Task 2 ------- */

    /**
     * Obtain the number of sentences in the document
     *
     * @return the number of sentences in the document
     */
    public int numSentences() {
        return doc_array.size();
    }

    /**
     * Obtain a specific sentence from the document.
     * Sentences are numbered starting from 1.
     *
     * @param sentence_number the position of the sentence to retrieve,
     *                        {@code 1 <= sentence_number <= this.getSentenceCount()}
     * @return the sentence indexed by {@code sentence_number}
     */
    // TODO: MACK IMPLEMENT THIS
    public String getSentence(int sentence_number) {
        StringBuilder sentenceString = new StringBuilder();
        String word;
        ArrayList<String> sentence = new ArrayList<String>();
        int size = sentence.size();
        for (int i = 0; i < size; i++) {
            word = sentence.get(i);
            sentenceString.append(word);
        }
        return sentenceString.toString();
    }

    public double averageSentenceLength() {
        int counter = 0;
        int sentenceLength = 0;

        while (counter < doc_array.size()) {
            sentenceLength += doc_array.get(counter).getSentenceLength();
            counter++;
        }

        return (double) sentenceLength / counter;
    }


    public double averageSentenceComplexity() {
        int counter = 0;
        int complexity = 0;
        while (counter < doc_array.size()) {
            complexity += doc_array.get(counter).numPhrases();
            counter++;
        }
        if (counter == 0){
            return 0;
        }
        return (double) complexity / counter;
    }


    public String toString(){
        return document;
    }

    /**
     * Obtain the sentence with the most positive sentiment in the document
     *
     * @return the sentence with the most positive sentiment in the
     * document; when multiple sentences share the same sentiment value
     * returns the sentence that appears later in the document
     * @throws NoSuitableSentenceException if there is no sentence that
     *                                     expresses a positive sentiment
     */
    public String getMostPositiveSentence() throws NoSuitableSentenceException {
        // TODO: Implement this method
        return null;
    }

    /**
     * Obtain the sentence with the most negative sentiment in the document
     *
     * @return the sentence with the most negative sentiment in the document;
     * when multiple sentences share the same sentiment value, returns the
     * sentence that appears later in the document
     * @throws NoSuitableSentenceException if there is no sentence that
     *                                     expresses a negative sentiment
     */
    public String getMostNegativeSentence() throws NoSuitableSentenceException {
        // TODO: Implement this method
        return null;
    }
    /* ------- Task 4 ------- */
    public double compare(Document document1, Document document2){
        double divergence = 0;
        List<String> wordsInBoth  = new ArrayList<String>(findWordsInBoth(document1,document2));
        for(int i = 0; i< wordsInBoth.size(); i++){
            int frequencyInDoc1 = document1.wordCounts.get(wordsInBoth.get(i));
            int frequencyInDoc2 = document2.wordCounts.get(wordsInBoth.get(i));
            double probabilityInDoc1 = (double)frequencyInDoc1/document1.totalWordCount;
            double probabilityInDoc2 = (double)frequencyInDoc2/document2.totalWordCount;
            divergence += calculateDivergence(probabilityInDoc1, probabilityInDoc2);
        }

        return divergence/2.0;
    }

    private List<String> findWordsInBoth(Document document1, Document document2){
        Set<String> wordsInBoth = new HashSet<String>(document1.wordCounts.keySet());
        Set<String> wordsInDoc2 = new HashSet<String>(document2.wordCounts.keySet());
        wordsInBoth.retainAll(wordsInDoc2);

        return new ArrayList<String>(wordsInBoth);
    }
    //TODO: FIND A BETTER WAY TO IMPLEMENT LOG BASE 2
    private double calculateDivergence(double probability1, double probability2){
        double divergence = 0.0;
        double m1 = (probability1 + probability2)/2;
        divergence = probability1* Math.log(probability1/m1)/Math.log(2.0) +  probability2* Math.log(probability2/m1)/Math.log(2.0);
        return divergence;
    }
}