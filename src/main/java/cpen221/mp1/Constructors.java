package cpen221.mp1;

    import cpen221.mp1.exceptions.NoSuitableSentenceException;
    import cpen221.mp1.sentiments.SentimentAnalysis;

    import java.net.URL;
    import java.text.CharacterIterator;
    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.Hashtable;
    import java.io.FileNotFoundException;
    import java.io.FileReader;
    import java.text.BreakIterator;
    import java.util.Scanner;
    import java.io.IOException;
    import java.io.BufferedReader;
    import java.io.FileReader;
    import java.text.BreakIterator;
    import java.util.Locale;

public class Constructors{

}

public class Document {
    String doc_ID;
    public String document;
    private ArrayList<String> Sentences = new ArrayList<String>();

    //try reconsidering the data structure, lock and key would be easier since we make map index as key using the break iterator

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
     *
     * @param docId the document identifier
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

    /* ------- Task 1 ------- */

    public double averageWordLength() {


        // TODO: Implement this method
        return 0.0;
    }

    public double uniqueWordRatio() {
        // TODO: Implement this method

        //hashtable


        return 0.0;
    }

    public double hapaxLegomanaRatio() {
        // TODO: Implement this method
        return 0.0;
    }

    /* ------- Task 2 ------- */

    /**
     * Obtain the number of sentences in the document
     * @return the number of sentences in the document
     */
    public int numSentences() {

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

    public String toString(){

        return document;

    }
}

public class WordReader{

    private static int wordCount;

    public static ArrayList<String> SentenceBreak(String text){

        wordCount = 0;

        ArrayList<String> sentences = new ArrayList<String>();
        BreakIterator iterator = BreakIterator.getSentenceInstance(Locale.US);
        iterator.setText(text);


        int start = iterator.first();
        for (int end = iterator.next();
             end != BreakIterator.DONE;
             start = end, end = iterator.next()) {

            String sentence = text.substring(start, end);
            sentences.add(sentence);
            wordCount++;
        }

        return sentences;
    }

    public static int getcount(){
//Sorry Badly Phrased, should be sentence count
        return wordCount;

    }





}
