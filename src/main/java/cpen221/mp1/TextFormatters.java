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

    public static ArrayList<SentenceClass> sentenceList(Document document){
        ArrayList<SentenceClass> temporarySentenceArray = new ArrayList<>();
        for (int i = 1; i <= document.numSentences(); i++) {
            SentenceClass nextSentence = new SentenceClass(document.getSentence(i));
            temporarySentenceArray.add(nextSentence);
        }
        return temporarySentenceArray;
    }

    public static HashMap<String, Integer> wordInstanceCounter(ArrayList<SentenceClass> sentenceList) {
        HashMap<String, Integer> wordMap = new HashMap<String, Integer>();
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

    public static int wordCounts(ArrayList<SentenceClass> sentenceList){
        int wordCount = 0;
        for (int i = 0; i < sentenceList.size(); i++){
            wordCount += sentenceList.get(i).getSentenceLength();
        }
        return wordCount;
    }
}
