package cpen221.mp1;

import java.util.ArrayList;
import java.util.StringTokenizer;

import static cpen221.mp1.Document.*;

public class SentenceClass {

    private final ArrayList<Word> sentence = new ArrayList<>();
    private int phrases = 0;
    private int length;

    /**
     *
     * @param seed
     */
    public SentenceClass(String seed){
        String text = Document.formatText(seed);
        StringTokenizer tokenizer = new StringTokenizer(text, " ");
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
        }
        length = sentence.size();
    }

    @Override
    /**
     *
     * @return
     */
    public String toString(){
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < length; i++){
            builder.append(getWord(i));
        }
        return builder.toString();
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