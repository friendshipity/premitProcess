package com.szl.syj.image;


import com.szl.syj.utils.ImageUtils;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2018/3/12.
 */
public class picTest {




    public static void main(String[] args){
        double[] pixal = new double[3];
        pixal[0] = 241;
        pixal[1] = 214;
        pixal[2] = 87;
        ImageUtils.RGB2YUV(pixal);
//        ImageUtils.RGB2HSV(pixal);
//        System.out.println(RGB2YUV(pixal));

        pixal[0] = 230;
        pixal[1] = 192;
        pixal[2] = 81;
        ImageUtils.RGB2YUV(pixal);
//        ImageUtils.RGB2HSV(pixal);
//        System.out.println(RGB2YUV(pixal));
        pixal[0] = 208;
        pixal[1] = 198;
        pixal[2] = 183;
        ImageUtils.RGB2YUV(pixal);
//        ImageUtils.RGB2HSV(pixal);
//        System.out.println(RGB2YUV(pixal));


//        try {
//            ImageUtils.coveyRGB2YUV("fuzzyPermitSample.jpeg","fuzzyNew3.jpg");
//        }catch (Exception e){
//            e.printStackTrace();
//        }



//        List<File> fileList = Arrays.asList(new File("pics/").listFiles());
//        for (File docFile : fileList) {
//            try {
//                ImageUtils.coveyRGB2HUE(docFile.getAbsolutePath(), "p_anti_yellow/"+docFile.getName() + "anti_yellow.jpg");
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }

    }

}
