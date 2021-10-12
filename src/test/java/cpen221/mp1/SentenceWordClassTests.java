package cpen221.mp1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class SentenceWordClassTests {
    private static Document testDocument1;
    private static Document testDocument2;

    @Test
    public void testPhrases() {
        Document testDocument3 = new Document("test", "resources/testdoc.txt");
        Assertions.assertEquals(3, testDocument3.averageSentenceComplexity(), 0.005);
    }
}
