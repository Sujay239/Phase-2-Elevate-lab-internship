package com.URL.URL.Shortner.Service.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Base62 {
    private static final String alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!@#$%^&*()_+=-}{[]:;><,.?/";
    private static final int base = alphabet.length();


    public static String encode(Long num){
        if(num == 0) return "0";

        StringBuilder sb = new StringBuilder();

        while (num > 0){
            int rem = (int)(num % base);
            sb.append(alphabet.charAt(rem));
            num /= base;
        }

        return sb.reverse().toString();
    }

    public static Long decode(String code){
        long num = 0;
        for(int i = 0; i < code.length(); i++){
            num = num * base + alphabet.indexOf(code.charAt(i));
        }
        return num;
    }
}
