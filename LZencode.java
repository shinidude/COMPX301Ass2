import java.io.*;
import java.util.*;

public class LZencode {
    public static void main(String[] args) {
        // reader object for standard input stream
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        // writer object for standard output stream
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        
        // Create root node for trie
        TrieNode root;
        // create pointers for finding and adding entries to the trie
        TrieNode pointMain;
        TrieNode p;
        int count; // counter for the index

        String datum; // var for each character taken from the line to be encoded

        String hexChars; // var to store data in before encoding
        // try...catch... for any io errors
        try {
            String line; // var to read stream into
            // loop until end of stream, reading each line
            while ((line=reader.readLine()) != null) {
                root = new TrieNode(0, "", null);
                pointMain = root;
                p = pointMain;
                count=1;
                hexChars = ""; // reset the data string
                // get each line as it's bytes in an array, and convert to hex, before turning into a string
                hexChars += String.join("", ByteHex.convertB2H(line.getBytes()));
                
                // loop through line of characters received
                for (int i=0; i<hexChars.length(); i++) {
                    // get single character from line
                    datum = String.valueOf(hexChars.charAt(i));
        
                    // search for character in trie
                    p = pointMain.search(datum);
                    if (p == null) { // if null then character isnt in yet
                        pointMain.addChild(count, datum); // add character to trie
                        writer.write(pointMain.index + " " + Integer.parseInt(datum, 16)); // display to std output
                        writer.newLine(); // move to new line
                        count++; // increment phrase index
                        pointMain = root; // reset main pointer
                    } else {
                        if (i == hexChars.length()-2) { // if second last character in line
                            i++; // increment string index
                            datum = String.valueOf(hexChars.charAt(i)); // get next character (last character)
                            writer.write(p.index + " " + Integer.parseInt(datum, 16)); // write std output
                            writer.newLine(); // write new line
                        }
                        pointMain = p; // increment pointers through trie
                    }
                }
                // write line separator string and move to new line
                writer.write("----");
                writer.newLine();
                writer.flush(); // flush output from buffer to std output
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * Class for multiway trie
     */
    public static class TrieNode {
        // key vars to hold phrase index, and value of each node. and their children.
        int index;
        String val;
        TrieNode parent;
        ArrayList<TrieNode> children;
    
        // Constructor to init values
        public TrieNode(int i, String v, TrieNode p) {
            index = i;
            val = v;
            parent = p;
            children  = new ArrayList<TrieNode>();
        }
    
        /*
         * Adds a child to the arraylist of the current node
         */
        public void addChild(int i, String v) {
            children.add(new TrieNode(i, v, this));
        }
    
        /*
         * Searches the list of children if they have the value we're looking for
         */
        public TrieNode search(String v) {
            if (children.size() == 0) { // if there are no children to this node
                return null; // return null
            }
            for (TrieNode c : children) { // loop through children
                if (c.val.equals(v)) { // check if they have the value we're looking for
                    return c; // return the child node if it does
                }
            }
            // if the value is not found among the children, return null
            return null; 
        }
    }
}