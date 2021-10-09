package cpen221.mp1;

import static cpen221.mp1.Document.*;

public class Word {
    private String word;

    /**
     * @param seed the token string
     */
    public Word(String seed) {
        String formattedWord = formatWord(seed);

        if (formattedWord.isEmpty()) {
            throw new IllegalArgumentException("Word contained no valid characters.");
        } else {
            word = formattedWord;
        }
    }

    // TODO: can combine two methods into one????
    /**
     *
     * @param toFormat
     * @return
     */
    private String formatWord(String toFormat) {
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

    /**
     * @return
     */
    public int length() {
        return word.length();
    }

    @Override
    /**
     *
     * @return
     */
    public String toString() {
        return word;
    }
}