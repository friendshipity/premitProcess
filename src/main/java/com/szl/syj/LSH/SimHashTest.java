package com.szl.syj.LSH;

/**
 * Created by Administrator on 2018/4/4.
 */
public class SimHashTest {
    public static void main(String[] args){

        String str ="上海适达餐饮管理有限公司成都青羊万达广场店JY25101050026159王潇颖";
        String str1 ="上海适达餐饮管理有限公司成都青羊万达广场店JY25101050026159王潇颖";
        SimHash hash1= new SimHash(SimHash.Token_CN(str), 64);
        SimHash hash2 = new SimHash(SimHash.Token_CN(str1), 64);
        System.currentTimeMillis();


    }
}
