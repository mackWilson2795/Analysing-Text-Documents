package cpen221.mp1;

import java.util.Locale;

import static cpen221.mp1.Document.*;

public class Word {
    private String word;

    /**
     * @param seed the token string
     */
    public Word(String seed) {
        String formattedWord = Document.formatText(seed);

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