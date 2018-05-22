package com.szl.syj.utils;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2018/4/4.
 */
public class CharHash {

    public static double charHash(String str) {

        final int len = str.length();

        int sum = 0;
        for (int i = 0; i < len; i++) {
            sum += (int) str.charAt(i);
//            rv *= FNV_32_PRIME;
//            System.out.println((int)str.charAt(i));
        }
        double re = sum / len;
        return re;
    }

    public static double charHash1(String str) {

        final int len = str.length();

        int sum = 0;
        for (int i = 0; i < len; i++) {
            sum += (int) str.charAt(i);
//            rv *= FNV_32_PRIME;
//            System.out.println((int)str.charAt(i));
        }
        double re = sum / 10000;
        return re;
    }
    public static double charHash2(String str) {

        final int len = str.length();

        int sum = 0;
        int k = 0;
        int indexNum = 0;
        for (int i = 0; i < len; i++) {
            k = (int) str.charAt(i);
//            if ((k >= 65 && k <= 90) || (k >= 97 && k <= 122) || (k > 48 && k < 57)) {
            if ((k > 48 && k < 57)) {
                sum +=(i*Integer.valueOf(String.valueOf(str.charAt(i))));
                indexNum++;
            }
//            rv *= FNV_32_PRIME;
//            System.out.println((int)str.charAt(i));
        }
        double re = sum ;
        return re;
    }


    public static double charHash3(String str) {
        int len = str.length();
        StringBuilder sb = new StringBuilder();
        while (len-->0){
            sb.append("0");
        }
        return Similarity.wordExtention(str,sb.toString());

    }

}
