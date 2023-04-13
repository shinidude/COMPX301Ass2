import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class LZencode {
    public static void main(String[] args) {
        // reader object for standard input stream
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // try...catch... for any io errors
        try {
            // vars for input
            byte[] byteChars = new byte[0];
            int charTracker = 0;
            int iChar=0;
            // while character read is not eos (end of stream)
            while ((iChar=reader.read()) != -1) {
                byteChars = incSize(byteChars); // increment byte array
                byteChars[charTracker] = (byte)iChar; // cast and append char to byte array
                charTracker++; // increment tracker
            }
            // Convert bytes to hex
            String[] hexChars = ByteHex.convertB2H(byteChars);
            // System.out.println(Arrays.toString(hexChars)); // Testing output to ensure it is correct
        } catch (IOException e) {
            e.printStackTrace();
        }

        // encode here
        
    }

    public static byte[] incSize(byte[] array) {
        byte[] out = new byte[array.length+1];
        System.arraycopy(array, 0, out, 0, array.length);

        return out;
    }
}
