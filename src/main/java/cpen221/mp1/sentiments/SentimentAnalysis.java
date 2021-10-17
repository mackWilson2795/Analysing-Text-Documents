package cpen221.mp1.sentiments;

import com.google.cloud.language.v1.AnalyzeSentimentResponse;
import com.google.cloud.language.v1.Document.Type;
import com.google.cloud.language.v1.LanguageServiceClient;
import com.google.cloud.language.v1.Sentiment;
import cpen221.mp1.exceptions.NoSuitableSentenceException;
import cpen221.mp1.Document;

public class SentimentAnalysis {

    /**
     *
     * @param document
     * @return
     * @throws NoSuitableSentenceException
     */
    public static String getMostPositiveSentence(Document document)
        throws NoSuitableSentenceException {

        if (document.mostPositive == 0){
            getSentenceSentiments(document);
        }
        if (document.mostPositive == 0){
            throw new NoSuitableSentenceException();
        } else {
            return  document.getSentence((int) document.mostPositive);
        }
    }

    /**
     *
     * @param document
     * @return
     * @throws NoSuitableSentenceException
     */
    public static String getMostNegativeSentence(Document document)
        throws NoSuitableSentenceException {
        if (document.mostNegative == 0){
            getSentenceSentiments(document);
        }
        if (document.mostNegative == 0){
            throw new NoSuitableSentenceException();
        } else {
            return  document.getSentence((int) document.mostNegative);
        }
    }

    /**
     *
     * @param document
     * @return
     */
    private static void getSentenceSentiments(Document document) {
        int mostPositiveSentence = 0;
        float highestSentimentScore = 0;
        int mostNegativeSentence = 0;
        float lowestSentimentScore = 0;

        try (LanguageServiceClient language = LanguageServiceClient.create()) {
            for (int i = 1; i <= document.numSentences(); i++) {
                com.google.cloud.language.v1.Document nextSentence = com.google.cloud.language.v1.Document.newBuilder()
                        .setContent(document.getSentence(i)).setType(Type.PLAIN_TEXT).build();
                AnalyzeSentimentResponse response = language.analyzeSentiment(nextSentence);
                Sentiment sentiment = response.getDocumentSentiment();
                if (sentiment.getScore() > highestSentimentScore){
                    mostPositiveSentence = i;
                    highestSentimentScore = sentiment.getScore();
                }
                if (sentiment.getScore() < lowestSentimentScore){
                    mostNegativeSentence = i;
                    lowestSentimentScore = sentiment.getScore();
                }
            }

            if (highestSentimentScore > 0 && mostPositiveSentence != 0){
                document.mostPositive = mostPositiveSentence;
            }
            if (lowestSentimentScore < 0 && mostNegativeSentence != 0){
                document.mostNegative = mostNegativeSentence;
            }

        } catch (Exception ioe){
            throw new RuntimeException("Error: Unable to communicate with the Sentiment Analyzer.");
        }
    }
}
