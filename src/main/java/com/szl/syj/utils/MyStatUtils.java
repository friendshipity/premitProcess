package com.szl.syj.utils;

import org.apache.commons.math3.stat.StatUtils;

/**
 * Created by Administrator on 2017/11/17.
 */
public class MyStatUtils {
    public static int maxInt(Integer[] array) {
        double[] dArray = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            dArray[i] = (double) array[i];
        }
        double ans = StatUtils.max(dArray);
        return (int) ans;
    }

    public static int maxIntIndex(Integer[] array) throws Exception {
        double[] dArray = new double[array.length];
        int maxIndex = 0;
        for (int i = 0; i < array.length; i++) {
            dArray[i] = (double) array[i];
            if(array[i]>maxIndex){
                maxIndex = i;
            }
        }
        double ans = StatUtils.max(dArray);
        return maxIndex;
    }


}
