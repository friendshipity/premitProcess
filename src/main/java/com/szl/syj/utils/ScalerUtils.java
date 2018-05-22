package com.szl.syj.utils;

/**
 * Created by Administrator on 2017/12/5.
 */
public class ScalerUtils {
    public static double[] log(double[] array) {
        double[] array2 = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            array2[i] = Math.log(array[i]);
        }
        return array2;
    }


    public static double[] log10(double[] array) {
        double[] array2 = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            array2[i] = Math.log10(array[i]);
        }
        return array2;
    }
}
