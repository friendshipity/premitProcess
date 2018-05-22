package com.szl.syj.image;


import com.szl.syj.utils.ImageUtils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2018/3/14.
 */
public class ColorAntiYellow {

    public static void antiYellow(String loadPath, String writePath) {

        List<File> fileList = Arrays.asList(new File(loadPath).listFiles());
        for (File docFile : fileList) {
            try {
                ImageUtils.coveyRGB2HUE(docFile.getAbsolutePath(), writePath + docFile.getName() + "anti_yellow.jpg");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static byte[] antiYellowSingleV1(String image) throws Exception {
        BufferedImage bi = ImageUtils.coveyRGB2HUECORE_v1(image);
        return ImageUtils.imageToBytes(bi,"jpg");
    }

    public static byte[] antiYellowSingleV1(byte[] imageBinary) throws Exception {
        BufferedImage bi = null;
        bi = ImageUtils.bytes2ImageBuffer(imageBinary);
        bi = ImageUtils.coveyRGB2HUECORE_v1(bi);
        return ImageUtils.imageToBytes(bi,"jpg");
    }





    public static byte[] antiYellowSingleV2(String image) throws Exception {
        BufferedImage bi = ImageUtils.coveyRGB2HUECORE_v2(image);
        return ImageUtils.imageToBytes(bi,"jpg");
    }
    public static byte[] antiYellowSingleV2(byte[] imageBinary) throws Exception {
        BufferedImage bi = null;
        bi = ImageUtils.bytes2ImageBuffer(imageBinary);
        bi = ImageUtils.coveyRGB2HUECORE_v2(bi);
        return ImageUtils.imageToBytes(bi,"jpg");
    }

    public static void main(String[] args) {

//
//        List<File> fileList = Arrays.asList(new File("p_falsePics4/").listFiles());
//        for (File docFile : fileList) {
//            try {
//                ImageUtils.coveyRGB2HUE(docFile.getAbsolutePath(), "p_anti_yellow3/" + docFile.getName() + "anti_yellow.jpg");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

    }
}
