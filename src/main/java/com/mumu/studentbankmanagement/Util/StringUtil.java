package com.mumu.studentbankmanagement.Util;

public class StringUtil {
    public static String markString(String str,int start,int length){
        StringBuilder sb=new StringBuilder();
        sb.append(str.substring(0,start-1));
        for (int i = 0; i < length; i++) {
            sb.append("*");
        }
        sb.append(str.substring(start+length-1));
        return sb.toString();
    }

    public static String generateRandomCode(int i) {

        String str = "0123456789";
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < i; j++) {
            int index = (int) (Math.random() * str.length());
            sb.append(str.charAt(index));
        }
        return sb.toString();
    }
}
