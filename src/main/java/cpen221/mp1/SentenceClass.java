package cpen221.mp1;

import java.util.ArrayList;
import java.util.Locale;
import java.util.StringTokenizer;

import static cpen221.mp1.Document.*;

public class SentenceClass {

    private final ArrayList<Word> sentence = new ArrayList<>();
    private int phrases = 1;
    private final int length;
    private final String originalString;

    /**
     * @param seed is not null and contains at least one word.
     *
     */
    public SentenceClass(String seed){
        originalString = removeSpaces(seed);
        StringTokenizer tokenizer = new StringTokenizer(seed.toLowerCase(), " ");
        boolean phraseBroken = false;

        while (tokenizer.hasMoreTokens()){
            String nextToken = tokenizer.nextToken();
            int i = 0;
            int j = nextToken.length();
            try {
                while (i < nextToken.length() && SYMBOLS.contains(nextToken.charAt(i))){
                    if (PHRASE_BREAKERS.contains(nextToken.charAt(i))) {
                        phraseBroken = true;
                        break;
                    }
                    i++;
                }
                Word nextWord = new Word(nextToken);
                sentence.add(nextWord);
                if (phraseBroken) {
                    phrases++;
                    phraseBroken = false;
                }
                while (SYMBOLS.contains(nextToken.charAt(j - 1))){
                    if (PHRASE_BREAKERS.contains(nextToken.charAt(j - 1))) {
                        phraseBroken = true;
                        break;
                    }
                    j--;
                }
            } catch (IllegalArgumentException ignored) {
                /*
                Token was an invalid word - no action needed.
                 */
            }
        }
        length = sentence.size();
    }

    @Override
    /**
     * Obtain string representation of the sentence.
     *
     * @return original, unformatted version of the sentence.
     */
    public String toString(){
        return originalString;
    }

    /**
     * Obtain a specific word from the sentence.
     * Strings are indexed starting from 0.
     *
     * @param index the position of the word in the sentence,
     *              {@code 0 <= index < this.getSentenceLength()}
     * @return the word indexed by {@code index}
     */
    public String getWord(int index){
        return sentence.get(index).toString();
    }

    /**
     * Obtain the number of phrases in the sentence.
     * A phrase is a section of a sentence which is non-empty (contains a word),
     * sentences are broken into phrases by commas, colons, or semicolons.
     *
     * @return the number
     */
    public int numPhrases(){
        return phrases;
    }

    /**
     * Obtain the number of words in a sentence.
     *
     * @return the number of valid words contained in a sentence.
     */
    public int getSentenceLength(){
        return length;
    }

    /**
     * Returns a copy of the given string without any spaces before or after the first or last non-space character.
     *
     * @param seed string to be copied and formatted
     * @return copy of seed without any leading or lagging spaces
     */
    private String removeSpaces(String seed){
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
}