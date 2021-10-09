package cpen221.mp1;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.StringTokenizer;

import static cpen221.mp1.Document.*;

public class Sentence {

    private final ArrayList<Word> sentence = new ArrayList<>();
    private int phrases;
    private int length;

    /**
     *
     * @param seed
     */
    public Sentence(String seed){
        StringTokenizer tokenizer = new StringTokenizer(seed, " ");
        boolean phraseBroken = false;

        while (tokenizer.hasMoreTokens()){
            String nextToken = tokenizer.nextToken();
            int i = 0;
            int j = nextToken.length();

            try {
                while (SYMBOLS.contains(nextToken.charAt(i))){
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
                    if (PHRASE_BREAKERS.contains(nextToken.charAt(i))) {
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

            length = sentence.size();
        }

    }

    /**
     *
     * @return
     */
    public String getSentence(){
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < length; i++){
            builder.append(this.getWord(i));
            builder.append(" ");
        }

        return builder.toString();
    }

    /**
     *
     * @param index
     * @return
     */
    private String getWord(int index){
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


    // TODO: can combine two methods into one????
    /**
     *
     * @param toFormat
     * @return
     */
    private String formatSentence(String toFormat) {
        StringBuilder builder = new StringBuilder();
        builder.append(toFormat);

        while (SYMBOLS.contains(builder.charAt(0))) {
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
        while (SYMBOLS.contains(builder.charAt(builder.length() - 1))) {
            builder.deleteCharAt(builder.length() - 1);
        }

        return builder.toString();
    }

}