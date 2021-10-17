package cpen221.mp1;

import cpen221.mp1.exceptions.NoSuitableSentenceException;

import cpen221.mp1.sentiments.SentimentAnalysis;

import javax.print.Doc;
import java.net.URL;
import java.text.BreakIterator;
import java.util.*;
import java.io.FileReader;
import java.util.Scanner;
import java.io.IOException;
import java.io.BufferedReader;

public class Document {

    private String doc_ID;
    private String document;
    private HashMap<String, Integer> wordCounts;
    private int totalWordCount = 0;
    private ArrayList<SentenceClass> doc_array;
    private int mostPositive = 0;
    private int mostNegative = 0;

    /**
     * Create a new Document given a URL.
     *
     * @param docId    the document identifier
     * @param docURL the URL with the contents of the document
     */

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
            document = TextFormatters.formatDocument(document);
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
            System.out.println("Problem reading file!");
        }

        doc_array = SentenceBreak(document);
        wordCounts = TextFormatters.wordInstanceCounter(doc_array);
        totalWordCount = TextFormatters.wordCounts(doc_array);
    }


    /**
     * Create a new Document given a URL.
     *
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
            document = TextFormatters.formatDocument(document);
            reader.close();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
            System.out.println("Problem reading file!");
        }
        doc_array = SentenceBreak(document);
        wordCounts = TextFormatters.wordInstanceCounter(doc_array);
        totalWordCount = TextFormatters.wordCounts(doc_array);
    }

    private static ArrayList<SentenceClass> SentenceBreak(String text){
        ArrayList<SentenceClass> temporaryDocArray = new ArrayList<>();
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

    public String getDocId() {
        return doc_ID;
    }

    /**
     *
     * @return
     */
    public double averageWordLength() {
        int totalCharCount = 0;
        for (int i = 0; i < doc_array.size(); i++){
            for (int j = 0; j < doc_array.get(i).getSentenceLength(); j++){
                totalCharCount += doc_array.get(i).getWord(j).length();
            }
        }
        return (double) totalCharCount / totalWordCount;
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
    public String getSentence(int sentence_number) {
        return doc_array.get(sentence_number - 1).toString();
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
        int[] sentenceSentiments = {mostPositive, mostNegative};
        String mostPositiveSentence = SentimentAnalysis.getMostPositiveSentence(this, sentenceSentiments);
        mostPositive = sentenceSentiments[0];
        mostNegative = sentenceSentiments[1];
        return  mostPositiveSentence;
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
        int[] sentenceSentiments = {mostPositive, mostNegative};
        String mostNegativeSentence = SentimentAnalysis.getMostNegativeSentence(this, sentenceSentiments);
        mostPositive = sentenceSentiments[0];
        mostNegative = sentenceSentiments[1];
        return  mostNegativeSentence;
    }
}