package cpen221.mp1;

import cpen221.mp1.similarity.DocumentSimilarity;
import cpen221.mp1.similarity.GroupingDocuments;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class Task45Tests {


    @Test
    public void divergenceTestManyDocs() {
        URL testURL = null;
        try {
            testURL = new URL("http://textfiles.com/stories/antcrick.txt");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Document testDoc = new Document("file1", testURL);
        System.out.println(testDoc);

        Document text1 = new Document("file2", "resources/Text1.txt");
        System.out.println(text1);
        Document text2 = new Document("file3", "resources/Text2.txt");
        Document text3 = new Document("file4", "resources/Text3.txt");
        Document text4 = new Document("file5", "resources/Text4.txt");
        Document text5 = new Document("file6", "resources/Text5.txt");
        Document text6 = new Document("file7", "resources/Text6.txt");
        Document text7 = new Document("file8", "resources/Text7.txt");


        Set<Document> testDocs = new HashSet<Document>();

        testDocs.add(testDoc);
        testDocs.add(text1);
        testDocs.add(text2);
        testDocs.add(text3);
        testDocs.add(text4);
        testDocs.add(text5);
        testDocs.add(text6);
        testDocs.add(text7);

        Set<Set<Document>> expected = new HashSet<>();

        Set<Document> expectedSet1 = new HashSet<>();

        expectedSet1.add(testDoc);
        expectedSet1.add(text1);
        expectedSet1.add(text2);
        expectedSet1.add(text3);


        Set<Document> expectedSet2 = new HashSet<>();

        expectedSet2.add(text4);
        expectedSet2.add(text5);


        Set<Document> expectedSet3 = new HashSet<>();
        expectedSet3.add(text6);

        Set<Document> expectedSet4 = new HashSet<>();
        expectedSet4.add(text7);

        expected.add(expectedSet1);
        expected.add(expectedSet2);
        expected.add(expectedSet3);
        expected.add(expectedSet4);

        Assertions.assertEquals(expected, GroupingDocuments.groupBySimilarity(testDocs, 4));
    }
}
