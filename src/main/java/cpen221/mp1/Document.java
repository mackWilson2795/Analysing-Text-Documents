package cpen221.mp1;

import cpen221.mp1.exceptions.NoSuitableSentenceException;

import java.net.URL;
import java.util.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.BreakIterator;

public class Document {
    String doc_ID;
    String document;
    HashMap<String, Integer> wordCounts;
    Double totalWordCount = 0.0;

    public static final Set<Character> SYMBOLS = Set.of(' ', '!', '$', '%', '&', '\'', '(', ')', '*', '+', ',', '-', '.',
            '/', ':', '"', ';', '<', '=', '>', '?', '@', '[', '\\', ']', '^', '_', '`', '{', '|', '}', '~');
    public static final char HASH_TAG = '#';
    public static final Set<Character> SENTENCE_ENDERS = Set.of('!', '.', '?');
    public static final List<Character> PHRASE_BREAKERS = List.of(',', ';', ':');

    private HashMap<String, Integer> instanceCounter(String seed) {
        HashMap<String, Integer> wordMap = new HashMap<String, Integer>();
        BreakIterator wordIterator = BreakIterator.getWordInstance();
        wordIterator.setText(seed);
        int start = wordIterator.first();
        int end = wordIterator.next();
        for (end = wordIterator.next(); end != BreakIterator.DONE; end = wordIterator.next()) {
            String word = document.substring(start, end);

            if (wordMap.containsKey(word)) {
                int count = wordMap.get(word);
                wordMap.replace(word, count++);
            } else {
                wordMap.put(word, 1);
            }
            totalWordCount++;
            start = end;
        }
        return wordCounts;
    }

//private final String cleanDoc;
    /* ------- Task 0 ------- */
    /*  all the basic things  */

    /**
     * Create a new document using a URL
     *
     * @param docId  the document identifier
     * @param docURL the URL with the contents of the document
     */
    public Document(String docId, URL docURL) {
        doc_ID = docId;

    }

    /**
     * @param docId    the document identifier
     * @param fileName the name of the file with the contents of
     *                 the document
     */
    public Document(String docId, String fileName) {
        doc_ID = docId;
        StringBuilder seed = new StringBuilder();

        try {
            Scanner docScanner = new Scanner(new FileReader(fileName));
            while (docScanner.hasNext()) {
                seed.append(docScanner.nextLine());
            }
        } catch (FileNotFoundException ioe) {
            System.out.println("Error reading file.");
        }

        document = seed.toString();
        wordCounts = instanceCounter(document);
    }

    /**
     * Obtain the identifier for this document
     *
     * @return the identifier for this document
     */
    public String getDocId() {
        // TODO: Implement this method
        return null;
    }

    /* ------- Task 1 ------- */

    public double averageWordLength() {
        BreakIterator iterator = BreakIterator.getWordInstance();
        iterator.setText(document);
        int start = iterator.first();
        int wordCount = 0;
        double charCount = 0.0;

        for (int end = iterator.next(); end != BreakIterator.DONE; end = iterator.next()) {
            String word = document.substring(start, end);

            charCount += word.length();
            wordCount++;
            start = end;
        }
        return 0.0;
    }


    private int totalWords(){
        return -1;
    }

    public double uniqueWordRatio(){
            int numUniqueWords = wordCounts.keySet().size();
        return numUniqueWords/totalWordCount;
    }

    public double hapaxLegomanaRatio() {
           double countExactlyOnce = 0.0;

           ArrayList<Integer> counts = new ArrayList<Integer>(wordCounts.values());
           while(counts.contains(1)){

           }
        return 0.0;
    }

    /* ------- Task 2 ------- */

    /**
     * Obtain the number of sentences in the document
     * @return the number of sentences in the document
     */
    public int numSentences() {
        // TODO: Implement this method
        return 0;
    }

    /**
     * Obtain a specific sentence from the document.
     * Sentences are numbered starting from 1.
     *
     * @param sentence_number the position of the sentence to retrieve,
     * {@code 1 <= sentence_number <= this.getSentenceCount()}
     * @return the sentence indexed by {@code sentence_number}
     */
    public String getSentence(int sentence_number) {
        // TODO: Implement this method
        return null;
    }

    public double averageSentenceLength() {
        // TODO: Implement this method
        return 0.0;
    }

    public double averageSentenceComplexity() {
        // TODO: Implement this method
        return 0.0;
    }

    /* ------- Task 3 ------- */

//    To implement these methods while keeping the class
//    small in terms of number of lines of code,
//    implement the methods fully in sentiments.SentimentAnalysis
//    and call those methods here. Use the getSentence() method
//    implemented in this class when you implement the methods
//    in the SentimentAnalysis class.

//    Further, avoid using the Google Cloud AI multiple times for
//    the same document. Save the results (cache them) for
//    reuse in this class.

//    This approach illustrates how to keep classes small and yet
//    highly functional.

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
        return null;
    }

}
