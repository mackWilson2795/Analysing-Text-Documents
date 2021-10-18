package cpen221.mp1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class SentenceWordClassTests {

    @Test
    public void emptyDocTest() {
        Document testDoc1 = new Document("test1", "resources/Text8.txt");
        Assertions.assertEquals(0, testDoc1.averageWordLength());
        Assertions.assertEquals(0,testDoc1.uniqueWordRatio());
        Assertions.assertEquals(0,testDoc1.hapaxLegomanaRatio());
        Assertions.assertEquals(0,testDoc1.averageSentenceComplexity());
        Assertions.assertEquals(0,testDoc1.averageSentenceLength());
    }

    @Test
    public void testDocID() {
        Document testDocument = new Document("test", "resources/testdoc.txt");
        Assertions.assertEquals("test", testDocument.getDocId());
    }

    @Test
    public void testSentenceWithMultiplePhrases() {
        Document testDocument3 = new Document("test", "resources/testdoc.txt");
        Assertions.assertEquals(3, testDocument3.averageSentenceComplexity(), 0.005);
    }

    @Test
    public void irregularPunctuation() {
        cpen221.mp1.Document testDocument = new Document("test", "resources/oddPunctuation.txt");
        Assertions.assertEquals(6, testDocument.averageSentenceComplexity(), 0.005);
        Assertions.assertEquals("Why are you, ,working, so hard ,,,,,,;;;;'''' Come into the shade, away from the sun, and sing a song with me.,", testDocument.getSentence(1));
    }
}
