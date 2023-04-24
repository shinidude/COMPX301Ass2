import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Scanner;

public class LZdecode {
    //The dictionary for
    static  ArrayList<Integer> phraseNumList = new ArrayList<Integer>();//where the phrase numbers are listed
    static ArrayList<Integer> misMatchSymbols = new ArrayList<Integer>(); //where the mismatched numbers are listed 
	static ArrayList <String> hexSymbols = new ArrayList<String>();//list of decimal mismatch symbols turned into hex
	static ArrayList <String> trackerlist  = new ArrayList<>();//list of the decoded hex values which also helps to track the entered values 
	public static void main(String[] args){
    	try {
        	Scanner sc = new Scanner(System.in);

        	while (sc.hasNextLine()){
            	//Grabbing the output of the LZencoder 
            	String [] encodedOutput =  sc.nextLine().split(" ");
			    System.out.println(encodedOutput);

            	//if it is the end of the values decoded 
				phraseNumList.add(Integer.parseInt(encodedOutput[0]));//pasin
				if(encodedOutput[1].toString().compareTo("$") == 0){
    			break;//Break the 
			}
			misMatchSymbols.add(Integer.parseInt(encodedOutput[1]));
        	}
			sc.close(); //Closing the scanner 
			//Turning the mismatched symbols into hex
			for(int i = 0; i<misMatchSymbols.size(); i++){
				hexSymbols.add(Integer.toHexString(misMatchSymbols.get(i)));
			}
	
			//Getting the decoded hex output 
			Compare();
			//Turning the hex output into string 
			decode(String.join("", trackerlist));
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
	public static void Compare(){
		try {
			StringBuilder data =new StringBuilder(); 
			String joinedString = "";
			int  phraseNum; 
			for(int i = 0; i<phraseNumList.size();i++ ){
				phraseNum = phraseNumList.get(i);
				if(phraseNum ==0){//not in the tracklist yet 
					trackerlist.add(hexSymbols.get(i));//Add it in the tracklist 
					System.out.println("this is the trackerlist 1");
					System.out.println(trackerlist);
				}else{//if it exist in the tracklist 
					data =  new StringBuilder(trackerlist.get(phraseNum-1));//Grab the data based on he phrased num in the tracklist
					if(hexSymbols.size()==i){//if there are no hex symbols anymore 
						trackerlist.add(i,data.toString()); //Add the associated data based on its phrasenumber in the tracker list 
					}else{//if there are still more symbols 
						System.out.println("The data the will be joined:" +" "+ data);
						joinedString = data.append(hexSymbols.get(i)).toString(); //join the associated data before the actual hexSybmol
						trackerlist.add(i,joinedString);//Add this in the trackerlist 
					}
				}
			System.out.println("this is the trackerlist 2");
			System.out.println(trackerlist);
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
	private static void decode(String hex){
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
    	for (int i = 0; i < hex.length(); i += 2) {
      		String str = hex.substring(i, i + 2);
      		int byteVal = Integer.parseInt(str, 16);
      		byteStream.write(byteVal);
    	} 
    	String s = new String(byteStream.toByteArray(), Charset.forName("UTF-8"));
		System.out.println(s);
	} 
}
