import java.util.Arrays;

class ByteHex {
    public static String [] convertB2H(byte[] bytes){
        String [] stringArr = new String [bytes.length];  
        for(int i= 0; i<bytes.length; i++){
            stringArr[i] = String.format("%02x", bytes[i]);
        }
        return stringArr ;
    }

    // public static void main(String[] args) {
    //     byte [] bytes = {0, 10, 20, 8, 15, 25};

    //     String [] output = convertB2H(bytes);
    //     System.out.println(Arrays.toString(output));
        
    // }
}