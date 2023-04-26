import java.io.*;
import java.util.ArrayList;

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
				if (line.compareTo("----")!=0) {
					//Grabbing the output of the LZencoder 
					String [] encodedOutput =  line.split(" ");
					phraseNumList.add(Integer.parseInt(encodedOutput[0]));//pasin
					misMatchSymbols.add(Integer.parseInt(encodedOutput[1]));
				} else {
					for(int i = 0; i<misMatchSymbols.size(); i++){
						hexSymbols.add(Integer.toHexString(misMatchSymbols.get(i)));
					}

					//Getting the decoded hex output 
					decode();
					//Turning the hex output into string 
					displayOutput(String.join("", trackerlist));

					phraseNumList = new ArrayList<Integer>();
					misMatchSymbols = new ArrayList<Integer>();
					hexSymbols = new ArrayList<String>();
					trackerlist  = new ArrayList<>();
				}
        	}
			reader.close(); //Closing the scanner 
			// //Turning the mismatched symbols into hex
			// for(int i = 0; i<misMatchSymbols.size(); i++){
			// 	hexSymbols.add(Integer.toHexString(misMatchSymbols.get(i)));
			// }
			
			// //Getting the decoded hex output 
			// decode();
			// //Turning the hex output into string 
			// displayOutput(String.join("", trackerlist));
    } catch (Exception e) {
        // TODO: handle exception
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
	 * displayOutput(String hex)
	 * processes the hex value to become a byte value 
	 * and dispays it 
	 */
	private static void displayOutput(String hex){
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
			byte[] byteArr = ByteHex.convertH2B(hex);
			for(int i= 0; i<byteArr.length;i++){
				writer.write(Byte.toUnsignedInt(byteArr[i]));
			}
			writer.newLine();
			writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
}
