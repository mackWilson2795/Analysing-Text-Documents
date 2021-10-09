package cpen221.mp1;

import com.google.cloud.language.v1.Sentence;
import cpen221.mp1.exceptions.NoSuitableSentenceException;

import cpen221.mp1.sentiments.SentimentAnalysis;
import org.checkerframework.checker.units.qual.A;

import java.net.URL;
import java.text.CharacterIterator;
import java.util.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.BreakIterator;
import java.util.Scanner;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.text.BreakIterator;
import java.util.Locale;

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
    ArrayList<Sentence> doc_array = new ArrayList<Sentence>();



    private ArrayList<String> Sentences = new ArrayList<String>();

   


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

        Sentences = WordReader.SentenceBreak(document);
    }

    //Title Should be its own sentence!!!!!

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
            document = formatString(document);
        }
        catch (IOException ioe) {
            System.out.println("Problem reading file!");
        }

        Sentences = WordReader.SentenceBreak(document);
    }



    private HashMap<String, Integer> instanceCounter(String seed) {
        HashMap<String, Integer> wordMap = new HashMap<String, Integer>();
        Sentence sentence = new Sentence();
        for (int i = 0; i < doc_array.size(); i++) {
            sentence = doc_array.get(i);
            for (int k = 0; k < sentence.size(); k++) {
                String word = sentence.get(k);
                if (wordMap.containsKey(word)) {
                    int count = wordMap.get(word);
                    wordMap.replace(word, count++);
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

    private String formatString(String document){
        String formattedDoc = document;

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


    public void getSentences(){

        for (int i = 0; i < Sentences.size(); i++){

            System.out.println( i + " | " + Sentences.get(i));

        }


    }

    /**
     * @param
     */


    /* ------- Task 1 ------- */
    public double averageWordLength() {

        int wordCount = 0;

        return 0.0;
    }

    public double totalWords() {
        List<Integer> values = new ArrayList<Integer>(wordCounts.values());
        return (totalWordCount);
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

        int size = doc_array.size();
        return size;

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
        ArrayList<String> sentence = new ArrayList<String>();
        int counter = 0;
        int sentenceLength = 0;
        while (counter < doc_array.size()) {
            sentence = doc_array.get(counter);
            sentenceLength += sentence.size();
            counter++;
        }
        return sentenceLength / counter;
    }


    public double averageSentenceComplexity() {
        ArrayList<String> sentence = new ArrayList<String>();
        int counter = 0;
        int complexity = 0;
        while (counter < doc_array.size()) {
            sentence = doc_array.get(counter);
            complexity += sentence.numPhrases;
        }
        return complexity / counter;
    }


    public String toString(){

        return document;

    }
}

class WordReader{

    /**
     * Obtain the sentence with the most positive sentiment in the document
     * @return the sentence with the most positive sentiment in the
     * document; when multiple sentences share the same sentiment value
     * returns the sentence that appears later in the document
     * @throws NoSuitableSentenceException if there is no sentence that
     * expresses a positive sentiment
     */
   public String getMostPositiveSentence() throws NoSuitableSentenceException {
        // TODO: Implement this method
        return null;
    }

    /**
     * Obtain the sentence with the most negative sentiment in the document
     * @return the sentence with the most negative sentiment in the document;
     * when multiple sentences share the same sentiment value, returns the
     * sentence that appears later in the document
     * @throws NoSuitableSentenceException if there is no sentence that
     * expresses a negative sentiment
     */
   public String getMostNegativeSentence() throws NoSuitableSentenceException {
        // TODO: Implement this method
       //return null;
   }






}