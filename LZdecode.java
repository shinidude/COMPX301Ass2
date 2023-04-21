import java.util.ArrayList;
import java.util.Scanner;

public class LZdecode {
    //The dictionary for
    static ArrayList<Integer> phraseNumList = new ArrayList<Integer>();//where the phrase numbers are listed
    static ArrayList<Integer> misMatchSymbols = new ArrayList<Integer>(); //where the mismatched numbers are listed 
    
  public static void main(String[] args) {
    try {
        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            //Grabbing the output of the LZencoder 
            String [] encodedOutput =  sc.nextLine().split(" ");
            phraseNumList.add(Integer.parseInt(encodedOutput[0]));//pasin
            misMatchSymbols.add(Integer.parseInt(encodedOutput[1]));
            System.out.println(phraseNumList);
            System.out.println(misMatchSymbols);
    
        }
        
    } catch (Exception e) {
        // TODO: handle exception
        e.printStackTrace();
    }
  }
    
}
