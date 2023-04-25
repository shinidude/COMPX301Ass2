import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class LZdecode {
    //The dictionary for
    static  ArrayList<Integer> phraseNumList = new ArrayList<Integer>();//where the phrase numbers are listed
    static ArrayList<Integer> misMatchSymbols = new ArrayList<Integer>(); //where the mismatched numbers are listed 
	static ArrayList <String> hexSymbols = new ArrayList<String>();//list of decimal mismatch symbols turned into hex
	static ArrayList <String> trackerlist  = new ArrayList<>();//list of the decoded hex values which also helps to track the entered values 
	public static void main(String[] args){
    	try {
        	 // reader object for standard input stream
        	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			String line = "";

        	while ((line = reader.readLine())!=null){
            	//Grabbing the output of the LZencoder 
            	String [] encodedOutput =  line.split(" ");
			    // System.out.println(Arrays.toString(encodedOutput));

            	//if it is the end of the values decoded 
				phraseNumList.add(Integer.parseInt(encodedOutput[0]));//pasin
				if(encodedOutput[1].toString().compareTo("$") == 0){
    			break;//Break the 
			}
			misMatchSymbols.add(Integer.parseInt(encodedOutput[1]));
        	}
			reader.close(); //Closing the scanner 
			//Turning the mismatched symbols into hex
			for(int i = 0; i<misMatchSymbols.size(); i++){
				hexSymbols.add(Integer.toHexString(misMatchSymbols.get(i)));
			}
	
			//Getting the decoded hex output 
			decode();
			//Turning the hex output into string 
			hex2String(String.join("", trackerlist));
    } catch (Exception e) {
        // TODO: handle exception
        e.printStackTrace();
    }
}

	/**
	 * Compare()
	 * A method that organises the decoded hex values 
	 * based on phraseNumber list and available hex symbols 
	 */
	public static void decode(){
		try {
			StringBuilder data =new StringBuilder(); 
			String joinedString = "";
			int  phraseNum; 
			for(int i = 0; i<phraseNumList.size();i++ ){
				phraseNum = phraseNumList.get(i);
				if(phraseNum ==0){//not in the tracklist yet 
					trackerlist.add(hexSymbols.get(i));//Add it in the tracklist 
				}else{//if it exist in the tracklist 
					data =  new StringBuilder(trackerlist.get(phraseNum-1));//Grab the data based on he phrased num in the tracklist
					if(hexSymbols.size()==i){//if there are no hex symbols anymore 
						trackerlist.add(i,data.toString()); //Add the associated data based on its phrasenumber in the tracker list 
					}else{//if there are still more symbols 
						joinedString = data.append(hexSymbols.get(i)).toString(); //join the associated data before the actual hexSybmol
						trackerlist.add(i,joinedString);//Add this in the trackerlist 
					}
				}
    	}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}	
	}


	/**
	 * decode()
	 * Takes the joined hex value output 
	 * and turns this value into a string 
	 */
	private static void hex2String(String hex){
		try {
			OutputStreamWriter writer = new OutputStreamWriter(System.out);
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
    		for (int i = 0; i < hex.length(); i += 2) {
      			String str = hex.substring(i, i + 2);
      			int byteVal = Integer.parseInt(str, 16);
      			byteStream.write(byteVal);
    		} 
    		String s = new String(byteStream.toByteArray(), Charset.forName("UTF-8"));
			byte[] val =byteStream.toByteArray();
			for (byte b : byteStream.toByteArray()) {
				System.out.println(b);
			}
			writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
}
