package cpen221.mp1;

import java.util.Locale;

import static cpen221.mp1.Document.*;

public class Word {
    private String word;

    /**
     * Create an instance of a word, this will have no
     *
     * @param seed the token string to be turned into a word, is not null.
     * @throws IllegalArgumentException if the given word contains only invalid characters (punctuation and symbols)
     */
    public Word(String seed) {
        String formattedWord = TextFormatters.formatText(seed);

        if (formattedWord.isEmpty()) {
            throw new IllegalArgumentException("Word contained no valid characters.");
        } else {
            word = formattedWord.toLowerCase();
        }
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