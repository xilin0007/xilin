package com.fxl.frame.util.encrypt;

import org.apache.commons.codec.binary.Base64;

public class Base64Utils {
    
    public static String encrypt(String source) {
        byte[] bytes = source.getBytes();
        return Base64.encodeBase64String(bytes);
    }
    
    public static String decode(String enc) {
        byte[] bytes = Base64.decodeBase64(enc);
        return new String(bytes);
    }
    
    public static void main(String[] args) {
        String source = "adfsdfsdfwe2";
        String enc = encrypt(source);
        String dec = decode(enc);
        System.out.println(enc);
        System.out.println(dec);
    }
}
