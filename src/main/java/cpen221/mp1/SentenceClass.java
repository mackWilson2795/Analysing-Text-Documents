package cpen221.mp1;

import java.util.ArrayList;
import java.util.Locale;
import java.util.StringTokenizer;

import static cpen221.mp1.Document.*;

public class SentenceClass {

    private final ArrayList<Word> sentence = new ArrayList<>();
    private int phrases = 1;
    private int length;
    private String originalString;

    /**
     *
     * @param seed is not null and contains at least one word. BreakIterator can be used to break a text into its
     *             constituent sentences
     *
     */
    public SentenceClass(String seed){
        originalString = formatText(seed).toLowerCase();
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
     *
     * @return
     */
    public String toString(){
        return originalString;
    }

    /**
     *
     * @param index
     * @return
     */
    public String getWord(int index){
        return sentence.get(index).toString();
    }

    /**
     *
     * @return
     */
    public int numPhrases(){
        return phrases;
    }

    /**
     *
     * @return
     */
    public int getSentenceLength(){
        return length;
    }
}