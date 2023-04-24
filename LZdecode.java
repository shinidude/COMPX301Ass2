import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Scanner;

public class LZdecode {
    //The dictionary for
    static  ArrayList<Integer> phraseNumList = new ArrayList<Integer>();//where the phrase numbers are listed
    static ArrayList<Integer> misMatchSymbols = new ArrayList<Integer>(); //where the mismatched numbers are listed 
	static ArrayList <String> hexSymbols = new ArrayList<String>();//list of decimal mismatch symbols turned into hex
	static ArrayList <String> trackerlist  = new ArrayList<>();
  public static void main(String[] args) {
    try {
        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()){
            //Grabbing the output of the LZencoder 
            String [] encodedOutput =  sc.nextLine().split(" ");
			    System.out.println(encodedOutput);

            //if it is the end of the values decoded 
			if(encodedOutput[1].toString().compareTo("$") == 0){
				phraseNumList.add(Integer.parseInt(encodedOutput[0]));//pasin
				break;//Break the 
			}else{
				phraseNumList.add(Integer.parseInt(encodedOutput[0]));//pasin
                misMatchSymbols.add(Integer.parseInt(encodedOutput[1]));
			}
        }
		dec2Hex();
		Compare();
		decode(String.join("", trackerlist));


    } catch (Exception e) {
        // TODO: handle exception
        e.printStackTrace();
    }
  }

	private static void dec2Hex(){
		for(int i = 0; i<misMatchSymbols.size(); i++){
			hexSymbols.add(Integer.toHexString(misMatchSymbols.get(i)));
		}
		System.out.println(hexSymbols);
	}

	public static void Compare(){
		try {
			System.out.println("tedeest");
			StringBuilder data =new StringBuilder(); 
			String joinedString = "";
			int  phraseNum; 
			for(int i = 0; i<phraseNumList.size();i++ ){
				phraseNum = phraseNumList.get(i);
				if(phraseNum ==0){
					trackerlist.add(hexSymbols.get(i));
					System.out.println("this is the trackerlist 1");
					System.out.println(trackerlist);
				}else{
					data =  new StringBuilder(trackerlist.get(phraseNum-1));
					if(hexSymbols.size()==i){
						trackerlist.add(i,data.toString());

					}else{
						System.out.println("The data the will be joined:" +" "+ data);
						joinedString = data.append(hexSymbols.get(i)).toString();
						trackerlist.add(i,joinedString);
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

	private static void decode(String hex){
		System.out.println(hex);
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
