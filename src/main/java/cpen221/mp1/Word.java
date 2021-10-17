package cpen221.mp1;

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
    /** Returns a string representation of the word.
     *  This representation will be formatted such that there are no symbols or spaces before the first non-symbol,
     *  non-space character. The following is the list of symbols:
     *  ! " $ % & ' ( ) * + , - . / : ; < = > ? @ [ \ ] ^ _ ` { | } ~
     *  There will be no hashtags that are immediately followed by any of the above symbols or another hashtag.
     *  Any alphabetical characters in the string will be in lowercase.
     *
     * @return string representation of the word.
     */
    public String toString() {
        return word;
    }
}