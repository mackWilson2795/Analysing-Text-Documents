
package cpen221.mp1.crypanalysis;

import com.google.cloud.language.v1.Sentence;
import cpen221.mp1.Document;
import cpen221.mp1.SentenceClass;

import java.util.ArrayList;

public abstract class Cryptography {

        /**
         * Encrypt a document by replacing the i-th character, c_i, with
         * c_i + A \times \sin{(i \times 2\pi / P + \pi/4)}
         * where A is the amplitude and P is the period for the sine wave.
         * When encrypting text with multiple sentences, exactly one space
         * is used to separate sentences.
         *
         * @param doc the document to encrypt
         * @param length the number of characters to encrypt.
         *               If {@code length} is less than the number of
         *               characters in the document then only that many
         *               characters are encrypted.
         *               If {@code length} is greater than the number
         *               of characters in the document then additional
         *               ' ' are added at the end and encrypted.
         * @param period is the period of the sine wave used for encryption
         *               and {@code period} must be a factor of
         *               {@code length} other than 1 and {@code length} itself.
         * @param amplitude is the amplitude of the encrypting sine wave
         *                  and can be 64, 128, 256 or 512.
         * @return the encrypted array, with exactly one encrypted space
         *                  separating sentences.
         */
        public static int[] encrypt(Document doc, int length, int period, int amplitude) {
            ArrayList<Integer> encrypted = new ArrayList<>();
            int charIndex =0;
            int sentenceIndex=0;
            int totalChar = 0;
            String sentence = doc.getSentence(sentenceIndex);
            int characterCount = 0;
            char charToEncrypt;
            while(characterCount < length){
                if(charIndex== sentence.length() && sentenceIndex < doc.numSentences() -1){
                    charIndex =0;
                    sentenceIndex++;
                    sentence = doc.getSentence(sentenceIndex);
                    encrypted.add(encrypt(' ', period, amplitude, characterCount));
                    characterCount++;
                    length++;
                }
                if (sentenceIndex >= doc.numSentences() - 1 && charIndex==sentence.length()){
                    charToEncrypt = ' ';
                }
                else{
                    charToEncrypt = sentence.charAt(charIndex);
                    charIndex++;
                }
                encrypted.add(encrypt(charToEncrypt, period, amplitude, characterCount));
                characterCount++;
            }
            return toArray(encrypted);
        }

        /**
         * Decrypt a text that has been encrypted using {@code Cryptography#encrypt}.
         * @param codedText the data to decrypt.
         * @return the decrypted text.
         */
        public static String decrypt(int[] codedText) {
            // TODO: implement this method
            return null;
        }
        private static Integer encrypt(char verticalShift, int frequency, int amplitude, int x){
            int encrypt = (int) (verticalShift + amplitude*Math.sin(x*2*Math.PI/frequency + Math.PI/4));
            return encrypt;
        }
        private static int[] toArray(ArrayList<Integer> list){
            int[] array = new int[list.size()];
            for (int i =0; i < list.size(); i++) {
                array[i]= list.get(i);
            }
            return array;
        }

}
