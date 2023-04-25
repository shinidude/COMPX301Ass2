import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;

class ByteHex {
    public static String [] convertB2H(byte[] bytes){
        String [] stringArr = new String [bytes.length];  
        for(int i= 0; i<bytes.length; i++){
            stringArr[i] = String.format("%02x", bytes[i]);
        }
        return stringArr ;
    }

    public static byte [] convertH2B(String hexStr){
        byte [] byteArr; 
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        for (int i = 0; i < hexStr.length(); i += 2) {
              String str = hexStr.substring(i, i + 2);
              int byteVal = Integer.parseInt(str, 16);
              byteStream.write(byteVal);
        } 
        byteArr = new byte [byteStream.toByteArray().length];
        for(int i = 0; i<byteArr.length; i++){
            byteArr[i] = byteStream.toByteArray()[i];
            System.out.println(byteArr[i]);
        }
        System.out.println(byteArr);
        return byteArr;
    }
    public static void main(String[] args) {
        byte [] bytes = {9, 10, 20, 8, 15, 25};

        String [] output = convertB2H(bytes);
        byte [] out2 = convertH2B(String.join("", output));
        System.out.println("output for the convertB2H(bytes)");
        System.out.println(Arrays.toString(output));
        System.out.println("output for the convertH2B(bytes)");
        System.out.println(out2);
        
    }
}