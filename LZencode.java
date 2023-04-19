import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LZencode {
    public static void main(String[] args) {
        // reader object for standard input stream
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String hexChars=""; // var to store data in before encoding
        // try...catch... for any io errors
        try {
            String line=""; // var to read stream into
            // loop until end of stream, reading each line
            while ((line=reader.readLine()) != null) {
                // get each line as it's bytes in an array, and convert to hex, before turning into a string
                hexChars += String.join("",ByteHex.convertB2H(line.getBytes()));
            }
            // System.out.println(hexChars); // Testing output to ensure it is correct
        } catch (Exception e) {
            e.printStackTrace();
        }

        // encode here
        /*
         * LOOP through each "nibble"
         *     add to current phrase
         *     WHILE tracker is not at end of dict
         *         IF current phrase doesn't match known phrases
         *             find most recently matching phrase
         *             add to dictionary
         *             reset current phrase
         *         ENDIF
         *     ENDWHILE
         * ENDLOOP
         */
        String phrase="";
        int tracker=0;
        int lastPhrasePos = 0;
        ArrayList<String> dictPhrases = new ArrayList<String>();
        ArrayList<String> dictEncoded = new ArrayList<String>();
        dictPhrases.add("");
        dictEncoded.add("");
        for (int i=0; i<hexChars.length(); i++) {
            phrase += String.valueOf(hexChars.charAt(i));

            if (dictPhrases.contains(phrase)) {
                lastPhrasePos = dictPhrases.indexOf(phrase);
            } else {
                if (phrase.length() == 1) {
                    lastPhrasePos = 0;
                }
                dictPhrases.add(phrase);
                dictEncoded.add(lastPhrasePos + " " + String.valueOf( phrase.charAt( phrase.length()-1 ) ));
                phrase = "";
            }
        }

        System.out.println(dictPhrases.toString());
        System.out.println(dictEncoded.toString());
    }
}
