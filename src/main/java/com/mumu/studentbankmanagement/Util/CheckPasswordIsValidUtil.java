package com.mumu.studentbankmanagement.Util;

public class CheckPasswordIsValidUtil {
    private static final int[]bitmaps=createBitmap();
    private static final int UPPER_CASE=1<<1;
    private static final int LOWER_CASE=1<<2;
    private static  final int DIGIT=1<<3;
    private static final int SPECIAL_CHAR=1<<4;
    private static final int ALL=UPPER_CASE|LOWER_CASE|DIGIT|SPECIAL_CHAR;
    static int[] createBitmap(){
        int [] maps=new int[128];
        for(int i=0;i<128;i++){
            if(i>=65&&i<=90){
                maps[i]=UPPER_CASE;
            }else if(i>=97&&i<=122) {
                maps[i] = LOWER_CASE;
            }else if(i>=48&&i<=57){
                maps[i]=DIGIT;
            }else if(i==33||i==64||i==35||i==36||i==37||i==94||i==38||i==42||i==95||i==45){
                maps[i]=SPECIAL_CHAR;
            }

        }
        return maps;
    }
    public static boolean checkPasswordStringWithBitmap(String password){
        if(password.length()<6){
            return false;
        }
        int hints=0;
        int count=1;
        int prevDiff=0;
        for(int i=0;i<password.length();i++){
            char c=password.charAt(i);
            if(bitmaps[c]==0){
                return false;
            }
            int currentBit=bitmaps[c];
            hints|=currentBit;
            if(i<password.length()-1){
                int nextBit=bitmaps[password.charAt(i+1)];
                int diff=c-password.charAt(i+1);
                if(Math.abs(diff)==1&&(prevDiff==0||prevDiff==diff)&&currentBit==nextBit){
                    count++;
                    prevDiff=diff;
                    if(count>=4){
                        return false;
                    }
                }else{
                    count=1;
                    prevDiff=0;
                }
            }
        }
        if(hints!=ALL){
            return false;
        }
        return true;
    }
}

