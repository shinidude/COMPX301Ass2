class ByteHex {

    public static String [] convertB2H(byte[] bytes){
        String [] stringArr = new String [bytes.length];  
        //Turning each bytes into hex
        for(int i= 0; i<bytes.length; i++){
            stringArr[i] = String.format("%02x", bytes[i]);
        }
        return stringArr ;
    }

    public static byte [] convertH2B(String hexStr){
        // Setting up char array to convert and byte array to move into
        char[] hexChars = hexStr.toCharArray();
        byte[] byteChar;
        if (hexChars.length%2 == 0) // if char array has an even number of characters
            byteChar = new byte[hexChars.length/2];
        else // there are an odd number of characters in char array
            byteChar = new byte[(hexChars.length+1)/2];

        // loop through char array
        for (int i=0; i<hexChars.length; i += 2) {
            if (i+1 >= hexChars.length) { // if next char is outside of array size
                // append empty char onto last available char
                byteChar[i/2] = (byte)Integer.parseInt(hexChars[i]+"0", 16);
            } else {
                // get char and neighbour and move into byte array after conversion
                byteChar[i/2] = (byte)Integer.parseInt(""+hexChars[i]+hexChars[i+1], 16); 
            }
        }

        // return byte array
        return byteChar;
    }

    
    // public static void main(String[] args) {
    //     byte [] bytes = {9, 10, 20, 8, 15, 25};

    //     String [] output = convertB2H(bytes);
    //     byte [] out2 = convertH2B(String.join("", output));
    //     System.out.println("output for the convertB2H(bytes)");
    //     System.out.println(Arrays.toString(output));
    //     System.out.println("output for the convertH2B(bytes)");
    //     System.out.println(out2);
        
    // }
}