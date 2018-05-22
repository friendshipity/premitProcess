package com.szl.syj.utils;

import com.szl.syj.LSH.SimHash;

/**
 * Created by Administrator on 2018/4/4.
 */
public class FNVHashTest {

    public static void main(String[] args){
        String str1 = "1Y25100410171A0";//0
        String str2 = "1Y25100410171A2";//1
        String str3 = "1Y25100412171A2";//2
        String str4 = "双流县叙永荤豆花饭店";//1
        String str5 = "双流区叙永荤豆花饭店";//0
        String str6 = ":双流区叙水荤豆花饭店";//1
        String str7 = "大邑县夜不收荤豆花饭店";//2
        String str8 = "双流区叙永荤";
        System.out.println(CharHash.charHash1(str1));
        System.out.println(CharHash.charHash1(str2));
        System.out.println(CharHash.charHash1(str3));
        System.out.println(CharHash.charHash1(str4));
        System.out.println(CharHash.charHash1(str5));
        System.out.println(CharHash.charHash1(str6));
        System.out.println(CharHash.charHash1(str7));
        System.out.println(CharHash.charHash1(str8));
        System.out.println("-------------------------------");

        System.out.println(CharHash.charHash2(str1));
        System.out.println(CharHash.charHash2(str2));
        System.out.println(CharHash.charHash2(str3));
        System.out.println(CharHash.charHash2(str4));
        System.out.println(CharHash.charHash2(str5));
        System.out.println(CharHash.charHash2(str6));
        System.out.println(CharHash.charHash2(str7));
        System.out.println(CharHash.charHash2(str8));



        System.out.println("-------------------------------");
        System.out.println(CharHash.charHash3(str1));
        System.out.println(CharHash.charHash3(str2));
        System.out.println(CharHash.charHash3(str3));
        System.out.println(CharHash.charHash3(str4));
        System.out.println(CharHash.charHash3(str5));
        System.out.println(CharHash.charHash3(str6));
        System.out.println(CharHash.charHash3(str7));
        System.out.println(CharHash.charHash3(str8));

        SimHash hash1 = new SimHash(SimHash.Token_CN(str4), 64);
//        hash1.subByDistance(hash1, 3);

        SimHash hash2 = new SimHash(SimHash.Token_CN(str8), 64);
//        hash2.subByDistance(hash2, 3);
        System.out.println(hash1.hammingDistance(hash2));
        System.out.println(hash2.hammingDistance(hash1));


    }
}
