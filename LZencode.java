import java.io.*;
import java.util.ArrayList;

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

        /* ENCODING DONE HERE */
        // Vars for encoding
        String phrase="";
        int lastPhrasePos = 0;
        // ArrayLists to manage phrases and their encodings
        ArrayList<String> dictPhrases = new ArrayList<String>();
        ArrayList<String> dictEncoded = new ArrayList<String>();
        // init empty 0th indices
        dictPhrases.add("");
        dictEncoded.add("");
        // For each element in the hex characters (each nibble)
        for (int i=0; i<hexChars.length(); i++) {
            phrase += String.valueOf(hexChars.charAt(i)); // Add character to current phrase

            if (dictPhrases.contains(phrase)) { // If phrase has been encoded
                lastPhrasePos = dictPhrases.indexOf(phrase); // Get pos of greatest matching phrase so far
                if (i == hexChars.length()-1) { // If on last character of input
                    // Then we must have no more mismatched characters, so
                    dictEncoded.add(lastPhrasePos+" $"); // Add delim and position of matching phrase 
                }
            } else { // phrase up to here hasn't been encoded
                dictPhrases.add(phrase); // Add phrase to dictionary of known phrases
                // the parseInt converts hex to decimal value
                dictEncoded.add(lastPhrasePos + " " + Integer.parseInt( String.valueOf(  phrase.charAt( phrase.length()-1 )  ), 16 )); // add phrase encoding to dictionary
                phrase = ""; // reset current phrase
                lastPhrasePos = 0; // reset last phrase pos
            }
        }

        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
            // For each item in dictionary of encoded phrases
            for (int i=1; i<dictEncoded.size(); i++) {
                writer.write(dictEncoded.get(i)); // Print encoding to std output
                writer.newLine();
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
