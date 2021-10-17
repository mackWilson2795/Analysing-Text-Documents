package cpen221.mp1.sentiments;

import com.google.cloud.language.v1.AnalyzeSentimentResponse;
import com.google.cloud.language.v1.Document.Type;
import com.google.cloud.language.v1.LanguageServiceClient;
import com.google.cloud.language.v1.Sentiment;
import cpen221.mp1.exceptions.NoSuitableSentenceException;
import cpen221.mp1.Document;

public class SentimentAnalysis {
    private static int mostPositiveFound;
    private static int mostNegativeFound;

    /**
     * Obtain the sentence with the most positive sentiment in the document
     *
     * @param document the Document to be analyzed
     * @return the sentence with the most positive sentiment in the
     * document; when multiple sentences share the same sentiment value
     * returns the sentence that appears later in the document
     * @throws NoSuitableSentenceException if there is no sentence that
     *                                     expresses a positive sentiment
     */
    public static String getMostPositiveSentence(Document document, int[] sentenceSentiments)
        throws NoSuitableSentenceException {
        mostPositiveFound = sentenceSentiments[0];
        mostNegativeFound = sentenceSentiments[1];
        if (mostPositiveFound == 0) {
            getSentenceSentiments(document);
            sentenceSentiments[0] = mostPositiveFound;
            sentenceSentiments[1] = mostNegativeFound;
        }
        if (mostPositiveFound == 0) {
            throw new NoSuitableSentenceException();
        } else {
            return  document.getSentence(mostPositiveFound);
        }
    }

    /**
     * Obtain the sentence with the most negative sentiment in the document
     *
     * @param document the Document to be analyzed
     * @return the sentence with the most negative sentiment in the document;
     * when multiple sentences share the same sentiment value, returns the
     * sentence that appears later in the document
     * @throws NoSuitableSentenceException if there is no sentence that
     *                                     expresses a negative sentiment
     */
    public static String getMostNegativeSentence(Document document, int[] sentenceSentiments)
        throws NoSuitableSentenceException {
        mostPositiveFound = sentenceSentiments[0];
        mostNegativeFound = sentenceSentiments[1];
        if (mostNegativeFound == 0) {
            getSentenceSentiments(document);
            sentenceSentiments[0] = mostPositiveFound;
            sentenceSentiments[1] = mostNegativeFound;
        }
        if (mostNegativeFound == 0) {
            throw new NoSuitableSentenceException();
        } else {
            return  document.getSentence(mostNegativeFound);
        }
    }



    /**
     * Analyzes the sentiment of every sentence in the given Document.
     * The sentence numbers of the most positive and negative sentence are stored
     * in mostPositiveFound and mostNegativeFound respectively.
     * Sentences are considered positive if their sentiment is >= 0.3.
     * Sentences are considered negative if their sentiment is <= 0.3.
     *
     * @param document the Document to be analyzed
     * @throws RuntimeException if the Google AI sentiment analyzer cannot be accessed
     */
    private static void getSentenceSentiments(Document document) {
        float highestSentimentScore = 0.3f;
        float lowestSentimentScore = -0.3f;

        try (LanguageServiceClient language = LanguageServiceClient.create()) {
            for (int i = 1; i <= document.numSentences(); i++) {
                com.google.cloud.language.v1.Document nextSentence = com.google.cloud.language.v1.Document.newBuilder()
                        .setContent(document.getSentence(i)).setType(Type.PLAIN_TEXT).build();
                AnalyzeSentimentResponse response = language.analyzeSentiment(nextSentence);
                Sentiment sentiment = response.getDocumentSentiment();
                if (sentiment.getScore() >= highestSentimentScore) {
                    mostPositiveFound = i;
                    highestSentimentScore = sentiment.getScore();
                }
                if (sentiment.getScore() <= lowestSentimentScore) {
                    mostNegativeFound = i;
                    lowestSentimentScore = sentiment.getScore();
                }
            }
        } catch (Exception ioe){
            ioe.printStackTrace();
            throw new RuntimeException("Error: Unable to communicate with the Sentiment Analyzer.");
        }
    }
}
