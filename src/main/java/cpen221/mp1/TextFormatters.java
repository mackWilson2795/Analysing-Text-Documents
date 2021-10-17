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
     * Produces a List which contains every Sentence in a given Document.
     *
     * @param document Document to be read from
     * @return List of every Sentence in the given Document.
     */
    public static ArrayList<SentenceClass> sentenceList(Document document){
        ArrayList<SentenceClass> temporarySentenceArray = new ArrayList<>();
        for (int i = 1; i <= document.numSentences(); i++) {
            SentenceClass nextSentence = new SentenceClass(document.getSentence(i));
            temporarySentenceArray.add(nextSentence);
        }
        return temporarySentenceArray;
    }

    /**
     * Produces a Map where the Keys are every unique String that occur in a List of sentences
     * and the values are the number of times that word occurs.
     *
     * @param sentenceList List of SentenceClass sentences
     * @return Map with every unique String in the List of Sentences and the
     * number of times that String occurs
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
     *  Returns a copy of a given string where any doubled spaces, newline characters,
     *  or tab characters are replaced with a single space.
     *
     * @param seed the document to be formatted
     * @return formatted version of the given string
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
    public static int wordCounts(ArrayList<SentenceClass> sentenceList){
        int wordCount = 0;
        for (int i = 0; i < sentenceList.size(); i++){
            wordCount += sentenceList.get(i).getSentenceLength();
        }
        return wordCount;
    }
}
