class ByteHex {
    public static String [] convertB2H(byte[] bytes){
        String [] stringArr = new String [bytes.length];  
        for(int i= 0; i<bytes.length; i++){
            stringArr[i] = String.format("%02x", bytes[i]);
        }
        return stringArr ;
    }
}