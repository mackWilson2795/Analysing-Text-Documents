package cpen221.mp1.similarity;

import cpen221.mp1.Document;

import java.util.*;
import cpen221.mp1.TextFormatters;


public class DocumentSimilarity {
    //TODO: ALL METHODS STATIC, MERGE INTO MAIN, TEST, SPECS, RUN WITH COVERAGE

    /* DO NOT CHANGE THESE WEIGHTS */
    private final int WT_AVG_WORD_LENGTH = 5;
    private final int WT_UNIQUE_WORD_RATIO = 15;
    private final int WT_HAPAX_LEGOMANA_RATIO = 25;
    private final int WT_AVG_SENTENCE_LENGTH = 1;
    private final int WT_AVG_SENTENCE_CPLXTY = 4;
    private final int WT_JS_DIVERGENCE = 50;
    /* ---- END OF WEIGHTS ------ */

    /* ------- Task 4 ------- */

    /**
     * Compute the Jensen-Shannon Divergence between the given documents
     *
     * @param doc1 the first document, is not null
     * @param doc2 the second document, is not null
     * @return the Jensen-Shannon Divergence between the given documents
     */
    //TODO: DOES A NULL ARRAY HAVE A SIZE
    public double jsDivergence(Document doc1, Document doc2) {
        double divergence = 0;
        List<String> wordsInBoth = new ArrayList<>(findWordsInBoth(doc1, doc2));
        Map<String,Integer> wordCountsDoc1 = getWordCounts(doc1);
        Map<String,Integer> wordCountsDoc2 = getWordCounts(doc2);
        for (int i = 0; i < wordsInBoth.size(); i++) {
            int frequencyInDoc1 = wordCountsDoc1.get(wordsInBoth.get(i));
            int frequencyInDoc2 = wordCountsDoc2.get(wordsInBoth.get(i));
            double probabilityInDoc1 = (double) frequencyInDoc1 / getTotalWordCount(doc1);
            double probabilityInDoc2 = (double) frequencyInDoc2 / getTotalWordCount(doc2);
            divergence += calculateDivergence(probabilityInDoc1, probabilityInDoc2);
        }
        return divergence / 2.0;
    }

    /**
     * Compute the Document Divergence between the given documents
     *
     * @param doc1 the first document, is not null
     * @param doc2 the second document, is not null
     * @return the Document Divergence between the given documents
     */
    public double documentDivergence(Document doc1, Document doc2) {
        double divergence = WT_AVG_WORD_LENGTH * Math.abs(doc1.averageWordLength() - doc2.averageWordLength());
        divergence += WT_UNIQUE_WORD_RATIO * Math.abs(doc1.uniqueWordRatio() - doc2.uniqueWordRatio());
        divergence += WT_AVG_SENTENCE_CPLXTY * Math.abs(doc1.averageSentenceComplexity() - doc2.averageSentenceComplexity());
        divergence += WT_AVG_SENTENCE_LENGTH * Math.abs(doc1.averageSentenceLength() - doc2.averageSentenceLength());
        divergence += WT_HAPAX_LEGOMANA_RATIO * Math.abs(doc1.hapaxLegomanaRatio() - doc2.hapaxLegomanaRatio());
        divergence += WT_JS_DIVERGENCE * (Math.abs(jsDivergence(doc1, doc2)));

        return divergence;
    }


    /**
     * Find the words that occur in both given documents.
     * @param document1 The first document.
     * @param document2 The second document.
     * @return An array containing all the words found in both document1 and document2.
     * The array contains no repeats.
     */
    private List<String> findWordsInBoth(Document document1, Document document2) {
        Set<String> wordsInBoth = new HashSet<>(getWordCounts(document1).keySet());
        Set<String> wordsInDoc2 = new HashSet<>(getWordCounts(document2).keySet());
        wordsInBoth.retainAll(wordsInDoc2);

        return new ArrayList<String>(wordsInBoth);
    }

    /**
     * Finds the divergence of 2 given probabilities.
     * @param probability1 The probability of a certain word being found in a given document.
     * @param probability2 The probability of the same word being found in another given document
     * @return The divergence between both probabilities.
     */
    private double calculateDivergence(double probability1, double probability2) {
        double divergence = 0.0;
        double m1 = (probability1 + probability2) / 2;
        divergence = probability1 * Math.log(probability1 / m1) / Math.log(2.0) + probability2 * Math.log(probability2 / m1) / Math.log(2.0);
        return divergence;
    }

    /**
     * Counts the instances of each unique word in a given document.
     * @param document A document
     * @return A map which maps each unique word in document to an integer, representing
     * the amount of times that word occurred in document.
     */
    private HashMap<String, Integer> getWordCounts(Document document) {
        HashMap<String, Integer> wordCounts = TextFormatters.wordInstanceCounter(TextFormatters.sentenceArray(document));
        return wordCounts;
    }

    /**
     * Count the total number of words in document.
     * @param document A document
     * @return Total number of words in document.
     */
    private int getTotalWordCount (Document document){
        return TextFormatters.totalWordCount(TextFormatters.sentenceArray(document));
    }
}
