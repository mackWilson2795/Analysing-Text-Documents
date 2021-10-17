package cpen221.mp1;

import cpen221.mp1.exceptions.NoSuitableSentenceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.cloud.language.v1.AnalyzeSentimentResponse;
import com.google.cloud.language.v1.Document;
import com.google.cloud.language.v1.Document.Type;
import com.google.cloud.language.v1.LanguageServiceClient;
import com.google.cloud.language.v1.Sentiment;

public class Task3SentimentTests {

    private static cpen221.mp1.Document testDocument1;
    private static cpen221.mp1.Document testDocument2;

    @BeforeAll
    public static void setupTests() throws MalformedURLException {
        testDocument1 = new cpen221.mp1.Document("The Ant and The Cricket", "resources/antcrick.txt");
        testDocument2 = new cpen221.mp1.Document("The Ant and The Cricket", new URL("http://textfiles.com/stories/antcrick.txt"));
    }

    @Test
    public void testSentences() {
        String text = "maybe"; // the text for analysis
        try (LanguageServiceClient language = LanguageServiceClient.create()) {
            Document doc = Document.newBuilder().setContent(text).setType(Type.PLAIN_TEXT).build();
            AnalyzeSentimentResponse response = language.analyzeSentiment(doc);
            Sentiment sentiment = response.getDocumentSentiment();
            if (sentiment != null) {
                System.out.println(sentiment.getScore());
                System.out.println(sentiment.getMagnitude());
            }
        }
        catch (IOException ioe) {
            System.out.println(ioe);
            throw new RuntimeException("Unable to communicate with Sentiment Analyzer!");
        }
    }

    @Test
    public void testSentiment() {
        try {
            testDocument1.getMostPositiveSentence();
            testDocument1.getMostNegativeSentence();
        } catch (Exception ioe){
            System.out.println("Error - no suitable sentence exception");
        }
        Assertions.assertEquals("\"Well, try dancing now!\"", testDocument1.getSentence(testDocument1.mostPositive));
        Assertions.assertEquals("Then the snow fell and she could find nothing at all to eat.",testDocument1.getSentence(testDocument1.mostNegative));
    }

    @Test
    public void neutralSentencesPosPos () {
        cpen221.mp1.Document testDoc = new cpen221.mp1.Document("doc", "resources/neutralSentences.txt");
        try {
            testDoc.getMostPositiveSentence();
            testDoc.getMostPositiveSentence();
        } catch (Exception NoSuitableSentenceException){
            System.out.println("Neutral sentence test - no suitable sentence");
        }
    }

    @Test
    public void neutralSentencesNegNeg () {
        cpen221.mp1.Document testDoc = new cpen221.mp1.Document("doc", "resources/neutralSentences.txt");
        try {
            testDoc.getMostNegativeSentence();
            testDoc.getMostNegativeSentence();
        } catch (Exception NoSuitableSentenceException){
            System.out.println("Neutral sentence test - no suitable sentence");
        }
    }
}