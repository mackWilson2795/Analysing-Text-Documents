package cpen221.mp1;

import java.text.BreakIterator;
import java.util.ArrayList;

import static cpen221.mp1.Document.*;

public class Sentence {

    private ArrayList<Word> sentence;
    private int phrases;
    private int length;

    /**
     *
     * @param seed
     */
    public Sentence(String seed){
        String sentenceSeed = formatSentence(seed);
        BreakIterator iterator = BreakIterator.getWordInstance();
        iterator.setText(sentenceSeed);
        int start = iterator.first();
        // TODO: Is this correct ???
        phrases = 1;

        for (int end = iterator.next(); end != BreakIterator.DONE; start=end, end = iterator.next()){
            String word = seed.substring(start, end);

            if (word.equals(" ")) {
                continue;
            }
            if (word.contains(PHRASE_BREAKERS.get(0).toString()) || word.contains(PHRASE_BREAKERS.get(1).toString()) || word.contains(PHRASE_BREAKERS.get(2).toString())){
                // TODO: Decide how to break phrases - should ',' in a word break it
                phrases++;
                continue;
            }
            sentence.add(new Word(word));
        }
    }

    /*
    private void getNextWord (int start, int end){
        //TODO: make this
    }
     */

    /*
    private ArrayList<Word> toArrayList(){
        //TODO: make this
    }
     */

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
    public int getLength(){
        return length;
    }

    /**
     *
     * @param toFormat
     * @return
     */
    private String formatSentence(String toFormat) {
        StringBuilder builder = new StringBuilder();
        builder.append(toFormat);

        while (SYMBOLS.contains(builder.charAt(0))) {
            builder.deleteCharAt(0);
        }
        while (SYMBOLS.contains(builder.charAt(builder.length() - 1)) || builder.charAt(0) == HASH_TAG) {
            builder.deleteCharAt(builder.length() - 1);
        }

        return builder.toString();
    }

}