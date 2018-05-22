package com.szl.syj.image;


import com.szl.syj.utils.ImageUtils;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2018/3/13.
 */
public class colorDistanceTest {

    public static void main(String[] args){

        double[] rawRGb1 = new double[3];
        rawRGb1[0] = 0;
        rawRGb1[1] = 0;
        rawRGb1[2] = 0;
        double[] rawRGb2 = new double[3];
        rawRGb2[0] = 255;
        rawRGb2[1] = 255;
        rawRGb2[2] = 255;


        rawRGb1 = ImageUtils.HSV2xyz(ImageUtils.RGB2HSV(rawRGb1));
        rawRGb2 = ImageUtils.HSV2xyz(ImageUtils.RGB2HSV(rawRGb2));
        // distance vs raw
        double distance = ImageUtils.distance(rawRGb1,rawRGb2);
        System.out.println(distance);




        double[] rawRGb = new double[3];
        rawRGb[0] = 220;
        rawRGb[1] = 200;
        rawRGb[2] = 80;
//        List<File> fileList = Arrays.asList(new File("pics2/").listFiles());
//        for (File docFile : fileList) {
//            try {
////                ImageUtils.conveyColorEuclideanSimilarity(docFile.getAbsolutePath(), "p_colorDistanceMetric/"+docFile.getName() + "colorSim.jpg",rawRGb);
//                ImageUtils.coveyRGB2YUVReduction(docFile.getAbsolutePath(), "p_colorDistanceMetric/"+docFile.getName() + "colorSim.jpg");
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }



    }
}
