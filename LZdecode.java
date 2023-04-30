import java.io.*;
import java.util.ArrayList;

public class LZdecode {
    //The dictionary for
    static  ArrayList<Integer> phraseNumList = new ArrayList<Integer>();//where the phrase numbers are listed
    static ArrayList<Integer> misMatchSymbols = new ArrayList<Integer>(); //where the mismatched numbers are listed 
	static ArrayList <String> hexSymbols = new ArrayList<String>();//list of decimal mismatch symbols turned into hex
	static ArrayList <String> trackerList  = new ArrayList<>();//list of the decoded hex values which also helps to track the entered values 
	public static void main(String[] args){
    	try {
        	 // reader object for standard input stream
        	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			String line = "";

        	while ((line = reader.readLine())!=null){
				if (line.compareTo("----")!=0) { // if line is line separator
					//Grabbing stream from std input 
					String [] encodedOutput =  line.split(" ");
					phraseNumList.add(Integer.parseInt(encodedOutput[0]));//pasin
					misMatchSymbols.add(Integer.parseInt(encodedOutput[1]));
				} else {
					// loop through mismatched symbols
					for(int i = 0; i<misMatchSymbols.size(); i++){
						// add to arraylist of hex characters
						hexSymbols.add(Integer.toHexString(misMatchSymbols.get(i)));
					}

					//Getting the decoded hex output 
					decode();
					//Turning the hex output into string 
					// System.err.println("TEST: "+String.join("", trackerList).length()%2);
					displayOutput(String.join("", trackerList));

					// reset necessary array lists
					phraseNumList = new ArrayList<Integer>();
					misMatchSymbols = new ArrayList<Integer>();
					hexSymbols = new ArrayList<String>();
					trackerList  = new ArrayList<>();
				}
        	}
			reader.close(); //Closing the scanner 
    } catch (Exception e) {
        e.printStackTrace();
    }
}
	/**
	 * decode()
	 * A method that organises the decoded hex values 
	 * based on phraseNumber list and available hex symbols 
	 */
	public static void decode(){
		try {
			StringBuilder data = new StringBuilder(); 
			String joinedString = "";
			int  phraseNum; 
			for(int i = 0; i<phraseNumList.size();i++ ) {
				phraseNum = phraseNumList.get(i);
				if(phraseNum == 0){//not in the tracklist yet
					trackerList.add(hexSymbols.get(i));//Add it in the tracklist 
				}else{//if it exist in the tracklist 
					data =  new StringBuilder(trackerList.get(phraseNum-1));//Grab the data based on the phrase num in the tracklist
					if(hexSymbols.size()==i){//if there are no hex symbols anymore 
						trackerList.add(i,data.toString()); //Add the associated data based on its phrasenumber in the tracker list 
					}else{//if there are still more symbols 
						joinedString = data.append(hexSymbols.get(i)).toString(); //join the associated data before the actual hexSybmol
						trackerList.add(i,joinedString);//Add this in the trackerList 
					}
				}
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

	/**
	 * displayOutput(String hex)
	 * processes the hex value to become a byte value 
	 * and dispays it 
	 */
	private static void displayOutput(String hex){
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));//Used to write on console 
			byte[] byteArr = ByteHex.convertH2B(hex); //Converting hexstring into byte array
			for(int i= 0; i<byteArr.length;i++){ //printing each bytes onto the console
				writer.write(Byte.toUnsignedInt(byteArr[i]));
			}
			writer.newLine();//Add a newline 
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	} 
}
