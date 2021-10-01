package cpen221.mp1;

import static cpen221.mp1.Document.HASH_TAG;
import static cpen221.mp1.Document.SYMBOLS;

public class Word {
    private String word;

    /**
     *
     * @param seed the token string
     */
    public Word(String seed){
        StringBuilder builder = new StringBuilder();
        builder.append(seed);

        while (SYMBOLS.contains(builder.charAt(0))){
            builder.deleteCharAt(0);
        }
        while (SYMBOLS.contains(builder.charAt(builder.length() - 1)) || builder.charAt(0) == HASH_TAG){
            builder.deleteCharAt(builder.length() - 1);
        }

        word = builder.toString();
    }

    /**
     *
     * @return
     */
    public String toString(){
        return word;
    }
}

