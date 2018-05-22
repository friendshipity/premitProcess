package com.szl.syj.image;


import com.szl.syj.utils.ImageUtils;

/**
 * Created by Administrator on 2018/3/14.
 */
public class colorHUEtest {


    public static void main(String[] args){

        double[] pixal = new double[3];
        pixal[0] = 241;
        pixal[1] = 214;
        pixal[2] = 87;
        System.out.println(ImageUtils.RGB2HUE(pixal));
        System.out.println(ImageUtils.RGB2HUE2(pixal));

        pixal[0] = 230;
        pixal[1] = 192;
        pixal[2] = 81;

        System.out.println(ImageUtils.RGB2HUE(pixal));
        System.out.println(ImageUtils.RGB2HUE2(pixal));

        pixal[0] = 208;
        pixal[1] = 198;
        pixal[2] = 183;
        System.out.println(ImageUtils.RGB2HUE(pixal));
        System.out.println(ImageUtils.RGB2HUE2(pixal));

        System.out.println("yellow");
        pixal[0] = 250;
        pixal[1] = 250;
        pixal[2] = 0;
        System.out.println(ImageUtils.RGB2HUE(pixal));
        System.out.println(ImageUtils.RGB2HUE2(pixal));

        System.out.println("black");
        pixal[0] = 0;
        pixal[1] = 0;
        pixal[2] = 0;
        System.out.println(ImageUtils.RGB2HUE(pixal));
        System.out.println(ImageUtils.RGB2HUE2(pixal));

        System.out.println("red");
        pixal[0] = 205;
        pixal[1] = 92;
        pixal[2] = 92;
        System.out.println(ImageUtils.RGB2HUE(pixal));
        System.out.println(ImageUtils.RGB2HUE2(pixal));

        System.out.println("orange");
        pixal[0] = 255;
        pixal[1] = 165;
        pixal[2] = 0;
        System.out.println(ImageUtils.RGB2HUE(pixal));
        System.out.println(ImageUtils.RGB2HUE2(pixal));

        System.out.println("gold");
        pixal[0] = 255;
        pixal[1] = 215;
        pixal[2] = 0;
        System.out.println(ImageUtils.RGB2HUE(pixal));
        System.out.println(ImageUtils.RGB2HUE2(pixal));

        System.out.println("milk");
        pixal[0] = 202;
        pixal[1] = 200;
        pixal[2] = 204;
        System.out.println(ImageUtils.RGB2HUE(pixal));
        System.out.println(ImageUtils.RGB2HUE2(pixal));

        System.out.println("green");
        pixal[0] = 	0;
        pixal[1] = 128;
        pixal[2] = 0;
        System.out.println(ImageUtils.RGB2HUE(pixal));
        System.out.println(ImageUtils.RGB2HUE2(pixal));
        System.out.println("lightgreen");
        pixal[0] = 127;
        pixal[1] = 255;
        pixal[2] = 0;
        System.out.println(ImageUtils.RGB2HUE(pixal));
        System.out.println(ImageUtils.RGB2HUE2(pixal));
        System.out.println("skyBule");
        pixal[0] = 0;
        pixal[1] = 191;
        pixal[2] = 255;
        System.out.println(ImageUtils.RGB2HUE(pixal));
        System.out.println(ImageUtils.RGB2HUE2(pixal));

        System.out.println("188 148 96");
        pixal[0] = 188;
        pixal[1] = 148;
        pixal[2] = 96;
        System.out.println(ImageUtils.RGB2HUE(pixal));
        System.out.println(ImageUtils.RGB2HUE2(pixal));




        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");


        System.out.println("");
        System.out.println("");
        System.out.println("136 133 122");
        pixal[0] = 136;
        pixal[1] = 133;
        pixal[2] = 122;
        System.out.println(ImageUtils.RGB2HUE(pixal));
        System.out.println(ImageUtils.RGB2HSV(pixal));
        System.out.println(ImageUtils.RGB2YUV(pixal));

        System.out.println("");
        System.out.println("");
        System.out.println("249 221 122");
        pixal[0] = 249;
        pixal[1] = 221;
        pixal[2] = 122;
        System.out.println(ImageUtils.RGB2HUE(pixal));
        System.out.println(ImageUtils.RGB2HSV(pixal));
        System.out.println(ImageUtils.RGB2YUV(pixal));

        System.out.println("");
        System.out.println("");
        System.out.println("255 253 162");
        pixal[0] = 255;
        pixal[1] = 253;
        pixal[2] = 162;
        System.out.println(ImageUtils.RGB2HUE(pixal));
        System.out.println(ImageUtils.RGB2HSV(pixal));
        System.out.println(ImageUtils.RGB2YUV(pixal));

        System.out.println("");
        System.out.println("");
        System.out.println("254 253 245");
        pixal[0] = 254;
        pixal[1] = 254;
        pixal[2] = 245;
        System.out.println(ImageUtils.RGB2HUE(pixal));
        System.out.println(ImageUtils.RGB2HSV(pixal));
        System.out.println(ImageUtils.RGB2YUV(pixal));

        System.out.println("");
        System.out.println("");
        System.out.println("94 93 89");
        pixal[0] = 94;
        pixal[1] = 93;
        pixal[2] = 89;
        System.out.println(ImageUtils.RGB2HUE(pixal));
        System.out.println(ImageUtils.RGB2HSV(pixal));
        System.out.println(ImageUtils.RGB2YUV(pixal));


    }
}
