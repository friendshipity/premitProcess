package com.szl.syj.utils;

import org.apache.commons.math3.distribution.BetaDistribution;

/**
 * Created by Administrator on 2017/11/1.
 */
public class Sampler {

    public static double betasampler(double alpha,double beta){
        BetaDistribution betaDistribution=new BetaDistribution(alpha,beta);
        return betaDistribution.sample();
    }
    public static void main(String[] args){
        int num  = 100;
        while (num-->0) {
            double sample = betasampler(2, 5);
            System.out.println((int)(sample*20));
        }
    }
}
