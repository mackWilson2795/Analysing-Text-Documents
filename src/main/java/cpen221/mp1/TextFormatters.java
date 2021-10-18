package cpen221.mp1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class TextFormatters {
    public static final Set<Character> SYMBOLS = Set.of('!', '$', '%', '&', '\'', '(', ')', '*', '+', ',', '-', '.',
            '/', ':', '"', ';', '<', '=', '>', '?', '@', '[', '\\', ' ', ']', '^', '_', '`', '{', '|', '}', '~', '#');
    public static final char HASH_TAG = '#';
    public static final List<Character> PHRASE_BREAKERS = List.of(',', ';', ':');

    /**
     * Returns a List containing SentenceClasses for every sentence in a document
     *
     * @param document Document to be read from.
     * @return List of SentenceClasses with every sentence in the document.
     */
    public static ArrayList<SentenceClass> sentenceArray(Document document){
        ArrayList<SentenceClass> temporarySentenceArray = new ArrayList<>();
        for (int i = 1; i <= document.numSentences(); i++) {
            SentenceClass nextSentence = new SentenceClass(document.getSentence(i));
            temporarySentenceArray.add(nextSentence);
        }
        return temporarySentenceArray;
    }

    /**
     * Counts the instances of each unique word in a given document.
     * @param sentenceList An array of sentences.
     * @return A map which maps each unique word in document to an integer, representing
     * the amount of times that word occurred in document.
     */
    public static HashMap<String, Integer> wordInstanceCounter(ArrayList<SentenceClass> sentenceList) {
        HashMap<String, Integer> wordMap = new HashMap<>();
        for (int i = 0; i < sentenceList.size(); i++) {
            SentenceClass currentSentence = sentenceList.get(i);
            for (int k = 0; k < currentSentence.getSentenceLength(); k++) {
                String word = currentSentence.getWord(k);
                if (wordMap.containsKey(word)) {
                    int count = wordMap.get(word);
                    count++;
                    wordMap.replace(word, count);
                } else {
                    wordMap.put(word, 1);
                }
            }
        }
        return wordMap;
    }

    /**
     * Returns a modified copy of a given string, any double spaces,
     * newline characters, or tab characters will be replaced with
     * single spaces.
     *
     * @param seed string to be modified
     * @return a formatted copy of the input string
     */
    public static String formatDocument(String seed){
        String formattedDoc = seed;
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

    /**
     * Creates a string containing a formatted version of the provided string.
     * Removes symbols and spaces up to the first non-symbol,
     * non-space character, the following are considered symbols:
     * ! " $ % & ' ( ) * + , - . / : ; < = > ? @ [ \ ] ^ _ ` { | } ~
     * Removes any hashtags that are immediately followed by any of the above symbols or another hashtag.
     * Any alphabetical characters in the string will be in lowercase.
     *
     * @param toFormat the string to be formatted
     * @return a formatted version of the provided string
     */
    public static String formatText(String toFormat) {
        StringBuilder builder = new StringBuilder();
        builder.append(toFormat);
        while (!(builder.isEmpty()) && SYMBOLS.contains(builder.charAt(0))) {
            if (builder.charAt(0) == HASH_TAG){
                if (SYMBOLS.contains(builder.charAt(1))){
                    builder.deleteCharAt(0);
                } else {
                    break;
                }
            } else {
                builder.deleteCharAt(0);
            }
        }
        while (!(builder.isEmpty()) && SYMBOLS.contains(builder.charAt(builder.length() - 1))) {
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString().toLowerCase();
    }

    /**
     * Returns a copy of the given string without any spaces before or after the first or last non-space character.
     *
     * @param seed string to be copied and formatted
     * @return copy of seed without any leading or lagging spaces
     */
    public static String removeSpaces(String seed){
        StringBuilder builder = new StringBuilder();
        builder.append(seed);
        while (!(builder.isEmpty()) && builder.charAt(0) == ' '){
            builder.deleteCharAt(0);
        }
        while (!(builder.isEmpty()) && builder.charAt(builder.length() - 1) == ' '){
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString();
    }

    /**
     * Counts the number of words within a List of Sentences
     *
     * @param sentenceList a List of SentenceClass type sentences
     * @return the number of words in every sentence of the List
     */
    public static int totalWordCount(ArrayList<SentenceClass> sentenceList){
        int totalWordCount = 0;
        for (int i = 0; i < sentenceList.size(); i++){
            totalWordCount += sentenceList.get(i).getSentenceLength();
        }
        return totalWordCount;
    }
}
