package cpen221.mp1.similarity;

import cpen221.mp1.Document;

import java.util.*;

public class DocumentSimilarity {
    //TODO: ALL METHODS STATIC, MERGE INTO MAIN, TEST, SPECS, RUN WITH COVERAGE

    /* DO NOT CHANGE THESE WEIGHTS */
    private final int WT_AVG_WORD_LENGTH      = 5;
    private final int WT_UNIQUE_WORD_RATIO    = 15;
    private final int WT_HAPAX_LEGOMANA_RATIO = 25;
    private final int WT_AVG_SENTENCE_LENGTH  = 1;
    private final int WT_AVG_SENTENCE_CPLXTY  = 4;
    private final int WT_JS_DIVERGENCE        = 50;
    /* ---- END OF WEIGHTS ------ */

    /* ------- Task 4 ------- */

    /**
     * Compute the Jensen-Shannon Divergence between the given documents
     * @param doc1 the first document, is not null
     * @param doc2 the second document, is not null
     * @return the Jensen-Shannon Divergence between the given documents
     */
    public double jsDivergence(Document doc1, Document doc2) {
         double divergence = 0;
            List<String> wordsInBoth  = new ArrayList<String>(findWordsInBoth(doc1,doc2));
            for(int i = 0; i< wordsInBoth.size(); i++){
                int frequencyInDoc1 = doc1.getHashMap().get(wordsInBoth.get(i));
                int frequencyInDoc2 = doc2.getHashMap().get(wordsInBoth.get(i));
                double probabilityInDoc1 = (double)frequencyInDoc1/doc1.totalWordCount;
                double probabilityInDoc2 = (double)frequencyInDoc2/doc2.totalWordCount;
                divergence += calculateDivergence(probabilityInDoc1, probabilityInDoc2);
            }
        return divergence/2.0;
    }

    /**
     * Compute the Document Divergence between the given documents
     * @param doc1 the first document, is not null
     * @param doc2 the second document, is not null
     * @return the Document Divergence between the given documents
     */
    public double documentDivergence(Document doc1, Document doc2) {
        double divergence = WT_AVG_WORD_LENGTH*Math.abs(doc1.averageWordLength() - doc2.averageWordLength());
        divergence +=  WT_UNIQUE_WORD_RATIO*Math.abs(doc1.uniqueWordRatio() - doc2.uniqueWordRatio());
        divergence += WT_AVG_SENTENCE_CPLXTY*Math.abs(doc1.averageSentenceComplexity() - doc2.averageSentenceComplexity());
        divergence += WT_AVG_SENTENCE_LENGTH*Math.abs(doc1.averageSentenceLength() - doc2.averageSentenceLength());
        divergence += WT_HAPAX_LEGOMANA_RATIO*Math.abs(doc1.hapaxLegomanaRatio() - doc2.hapaxLegomanaRatio());
        divergence += WT_JS_DIVERGENCE*(Math.abs(jsDivergence(doc1, doc2)));

        return divergence;
    }



    private List<String> findWordsInBoth(Document document1, Document document2){
        Set<String> wordsInBoth = new HashSet<String>(document1.getHashMap().keySet());
        Set<String> wordsInDoc2 = new HashSet<String>(document2.getHashMap().keySet());
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
