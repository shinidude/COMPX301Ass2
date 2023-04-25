import java.io.*;
import java.util.ArrayList;

public class LZencode {
    public static void main(String[] args) {
        // reader object for standard input stream
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        
        // Array to store the dictonaries for each encoded phrase (separated by line)
        ArrayList<ArrayList<String>> dicts = new ArrayList<ArrayList<String>>();

        String hexChars; // var to store data in before encoding
        // try...catch... for any io errors
        try {
            String line=""; // var to read stream into
            // loop until end of stream, reading each line
            while ((line=reader.readLine()) != null) {
                hexChars = ""; // reset the data string
                // get each line as it's bytes in an array, and convert to hex, before turning into a string
                hexChars += String.join("", ByteHex.convertB2H(line.getBytes()));
                dicts.add(encode(hexChars)); // append dictionary to array
            }
            // System.out.println(hexChars); // Testing output to ensure it is correct
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
            ArrayList<String> dictEncoded;
            // For each dictionary in array of dictonaries
            for (int d=0; d<dicts.size(); d++) {
                // get dictionary of encoded phrase
                dictEncoded = dicts.get(d);
                // For each item in dictionary of the encoded phrase
                for (int i=1; i<dictEncoded.size(); i++) {
                    writer.write(dictEncoded.get(i)); // Print encoding to std output
                    writer.newLine();
                }
                writer.write("----"); // Print encoding separator
                writer.newLine();
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /// method to do the encoding
    /// Separate so that it can be call more easily
    /// And to better keep the code clean
    public static ArrayList<String> encode(String data) {
        // Vars for encoding
        String phrase = "";
        int lastPhrasePos = 0;
        // ArrayLists to manage phrases and their encodings
        ArrayList<String> dictPhrases = new ArrayList<String>();
        ArrayList<String> dictEncoded = new ArrayList<String>();
        // init empty 0th indices
        dictPhrases.add("");
        dictEncoded.add("");

        // For each element in the hex characters (each nibble)
        for (int j=0; j<data.length(); j++) {
            phrase += String.valueOf(data.charAt(j)); // Add character to current phrase

            if (dictPhrases.contains(phrase)) { // If phrase has been encoded
                lastPhrasePos = dictPhrases.indexOf(phrase); // Get pos of greatest matching phrase so far
                if (j == data.length()-1) { // If on last character of input
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

        // Return dictionary of the encoded phrase
        return dictEncoded;
    }
}
