package com.szl.syj.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.math3.stat.StatUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Created by Administrator on 2018/3/12.
 */
public class ImageUtils {


    private static final double R = 100;
    private static final double angle = 30;
    private static final double h = R * Math.cos(angle / 180 * Math.PI);
    private static final double r = R * Math.sin(angle / 180 * Math.PI);
    private static final double max_distance = 22083.647796503188;
    private static final double sqrt3 = 1.73205;

    public static double[] HSV2xyz(double[] hsv) {
        double H = hsv[0];
        double S = hsv[1];
        double V = hsv[2];

        double x = r * V * S * Math.cos(H / 180 * Math.PI);
        double y = r * V * S * Math.sin(H / 180 * Math.PI);
        double z = h * (1 - V);
        hsv[0] = x;
        hsv[1] = y;
        hsv[2] = z;
        return hsv;
    }

    public static double distance(double[] xyz1, double[] xyz2) {
        return Math.sqrt(Math.pow(xyz1[0] - xyz2[0], 2) + Math.pow(xyz1[1] - xyz2[1], 2) + Math.pow(xyz1[2] - xyz2[2], 2));
    }


    public static byte[] getImageBinary(String path) {
        File f = new File(path);
        BufferedImage bi;
        try {
            bi = ImageIO.read(f);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bi, "jpg", baos);
            byte[] bytes = baos.toByteArray();

            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void conveyColorEuclideanSimilarity(String image, String newImagePath, double[] targetRGB) throws Exception {
        int[] rgb = new int[3];
        File file = new File(image);
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int width = bi.getWidth();
        int height = bi.getHeight();
        int minx = bi.getMinX();
        int miny = bi.getMinY();

        double max_Dis = 0.0;

        for (int i = minx; i < width; i++) {
            for (int j = miny; j < height; j++) {
                int pixel = bi.getRGB(i, j); // 下面三行代码将一个数字转换为RGB数字
                rgb[0] = (pixel & 0xff0000) >> 16;
                rgb[1] = (pixel & 0xff00) >> 8;
                rgb[2] = (pixel & 0xff);
                double[] rgbD = new double[3];
                rgbD[0] = Double.valueOf(rgb[0]);
                rgbD[1] = Double.valueOf(rgb[1]);
                rgbD[2] = Double.valueOf(rgb[2]);
                rgbD = HSV2xyz(RGB2HSV(rgbD));
                // distance vs raw
                double distance = ImageUtils.distance(targetRGB, rgbD);
                if (distance > max_Dis)
                    max_Dis = distance;
            }
        }
        for (int i = minx; i < width; i++) {
            for (int j = miny; j < height; j++) {
                int pixel = bi.getRGB(i, j); // 下面三行代码将一个数字转换为RGB数字
                rgb[0] = (pixel & 0xff0000) >> 16;
                rgb[1] = (pixel & 0xff00) >> 8;
                rgb[2] = (pixel & 0xff);
                double[] rgbD = new double[3];
                rgbD[0] = Double.valueOf(rgb[0]);
                rgbD[1] = Double.valueOf(rgb[1]);
                rgbD[2] = Double.valueOf(rgb[2]);
                rgbD = HSV2xyz(RGB2HSV(rgbD));
                // distance vs raw
                double distance = ImageUtils.distance(targetRGB, rgbD);
                double similarity = (distance / max_Dis) * 255;

                rgb[0] = (int) similarity;
                rgb[1] = (int) similarity;
                rgb[2] = (int) similarity;

                int pixelN = 0;
                pixelN = (rgb[0] * 256 + rgb[1]) * 256 + rgb[2];
                if (pixelN > 8388608) {
                    pixelN = pixelN - 16777216;
                }

                bi.setRGB(i, j, pixelN);

            }
        }
        ImageIO.write(bi, "jpg", new File(newImagePath));
    }


    public static void coveyRGB2YUVReduction(String image, String newImagePath) throws Exception {
        int[] rgb = new int[3];
        File file = new File(image);
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int width = bi.getWidth();
        int height = bi.getHeight();
        int minx = bi.getMinX();
        int miny = bi.getMinY();

        for (int i = minx; i < width; i++) {
            for (int j = miny; j < height; j++) {
                int pixel = bi.getRGB(i, j); // 下面三行代码将一个数字转换为RGB数字
                rgb[0] = (pixel & 0xff0000) >> 16;
                rgb[1] = (pixel & 0xff00) >> 8;
                rgb[2] = (pixel & 0xff);
                double[] rgbD = new double[3];
                rgbD[0] = Double.valueOf(rgb[0]);
                rgbD[1] = Double.valueOf(rgb[1]);
                rgbD[2] = Double.valueOf(rgb[2]);
                rgbD = RGB2YUV(rgbD);
                rgb[0] = (int) (rgbD[0]);
                rgb[1] = (int) (rgbD[1] + 50);
//                rgb[1] = (int)(rgbD[1]+128);
                rgb[2] = (int) (rgbD[2] + 25);
//                rgb[2] = (int)(rgbD[2]+128);
                rgbD = YUV2RGB(rgb);
                rgb[0] = (int) (rgbD[0]);
                rgb[1] = (int) (rgbD[1]);
//                rgb[1] = (int)(rgbD[1]+128);
                rgb[2] = (int) (rgbD[2]);


                int pixelN = 0;
                pixelN = (rgb[0] * 256 + rgb[1]) * 256 + rgb[2];
                if (pixelN > 8388608) {
                    pixelN = pixelN - 16777216;
                }

                bi.setRGB(i, j, pixelN);

            }
        }
        ImageIO.write(bi, "jpg", new File(newImagePath));
    }

    public static void coveyRGB2YUV(String image, String newImagePath) throws Exception {
        int[] rgb = new int[3];
        File file = new File(image);
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int width = bi.getWidth();
        int height = bi.getHeight();
        int minx = bi.getMinX();
        int miny = bi.getMinY();

        for (int i = minx; i < width; i++) {
            for (int j = miny; j < height; j++) {
                int pixel = bi.getRGB(i, j); // 下面三行代码将一个数字转换为RGB数字
                rgb[0] = (pixel & 0xff0000) >> 16;
                rgb[1] = (pixel & 0xff00) >> 8;
                rgb[2] = (pixel & 0xff);
                double[] rgbD = new double[3];
                rgbD[0] = Double.valueOf(rgb[0]);
                rgbD[1] = Double.valueOf(rgb[1]);
                rgbD[2] = Double.valueOf(rgb[2]);
                rgbD = RGB2YUV(rgbD);
                rgb[0] = (int) rgbD[0];
                rgb[1] = (int) Math.abs(rgbD[1]);
//                rgb[1] = (int)(rgbD[1]+128);
                rgb[2] = (int) Math.abs(rgbD[2]);
//                rgb[2] = (int)(rgbD[2]+128);

                int pixelN = 0;
                pixelN = (rgb[0] * 256 + rgb[1]) * 256 + rgb[2];
                if (pixelN > 8388608) {
                    pixelN = pixelN - 16777216;
                }

                bi.setRGB(i, j, pixelN);

            }
        }
        ImageIO.write(bi, "jpg", new File(newImagePath));
    }

    public static void coveyRGB2HUE(String image, String newImagePath) {
        BufferedImage bi = null;
        try {
            bi = coveyRGB2HUECORE_v2(image);
            ImageIO.write(bi, "jpg", new File(newImagePath));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static byte[] imageToBytes(BufferedImage bImage, String format) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ImageIO.write(bImage, format, out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }
    public static BufferedImage bytes2ImageBuffer(byte[] bImage) {
        ByteArrayInputStream in = new ByteArrayInputStream(bImage);

        BufferedImage image = null;
        try{
            image =ImageIO.read((InputStream)in);
        }catch (Exception e){
            e.printStackTrace();
        }

        return image;
    }

    public static BufferedImage coveyRGB2HUECORE_v1(BufferedImage bi) throws Exception {
        int[] rgb = new int[3];
        int width = bi.getWidth();
        int height = bi.getHeight();
        int minx = bi.getMinX();
        int miny = bi.getMinY();

        for (int i = minx; i < width; i++) {
            for (int j = miny; j < height; j++) {
                int pixel = bi.getRGB(i, j); // 下面三行代码将一个数字转换为RGB数字
                rgb[0] = (pixel & 0xff0000) >> 16;
                rgb[1] = (pixel & 0xff00) >> 8;
                rgb[2] = (pixel & 0xff);
                double[] rgbD = new double[3];
                rgbD[0] = Double.valueOf(rgb[0]);
                rgbD[1] = Double.valueOf(rgb[1]);
                rgbD[2] = Double.valueOf(rgb[2]);
                double HUE = RGB2HUE(rgbD);
                double[] hsv = RGB2HSV(rgbD);
                double[] yuv = RGB2YUV(rgbD);
                boolean light = false;

                //strategy 1
                if(rgbD[0]>240 && rgbD[1]>240 &&rgbD[2]>240 ){
                    light = true;
                }

                if (((HUE - 240 > -30 && HUE - 240 < 15) && hsv[1]>0.2 )
                        ||((HUE - 240 > -50 && HUE - 240 < 20) && hsv[1]>0.01 &&light)

                 )

                //strategy 2

//                if (((HUE - 240 > -30 && HUE - 240 < 15) && hsv[1]>0.2 )
//                        ||((HUE - 240 > -50 && HUE - 240 < 20) && hsv[1]>0.01 && yuv[1]>245 )
//
//                        )
//strategy 3
//                if (((HUE - 240 > -30 && HUE - 240 < 15) && hsv[1] > 0.2)
//
//                        )

                {
//                if (hsv[1]>0.25) {
                    rgb[0] = 0;
                    rgb[1] = 0;
                    rgb[2] = 0;
                }
                int pixelN = 0;
                pixelN = (rgb[0] * 256 + rgb[1]) * 256 + rgb[2];
                if (pixelN > 8388608) {
                    pixelN = pixelN - 16777216;
                }

                bi.setRGB(i, j, pixelN);
            }
        }
        return bi;
    }



    public static BufferedImage coveyRGB2HUECORE_v1(String image) throws Exception {

        File file = new File(image);
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return coveyRGB2HUECORE_v1(bi);

    }

    ///
    public static BufferedImage coveyRGB2HUECORE_v2(BufferedImage bi) throws Exception {
        int[] rgb = new int[3];
        int width = bi.getWidth();
        int height = bi.getHeight();
        int minx = bi.getMinX();
        int miny = bi.getMinY();

        for (int i = minx; i < width; i++) {
            for (int j = miny; j < height; j++) {
                int pixel = bi.getRGB(i, j); // 下面三行代码将一个数字转换为RGB数字
                rgb[0] = (pixel & 0xff0000) >> 16;
                rgb[1] = (pixel & 0xff00) >> 8;
                rgb[2] = (pixel & 0xff);
                double[] rgbD = new double[3];
                rgbD[0] = Double.valueOf(rgb[0]);
                rgbD[1] = Double.valueOf(rgb[1]);
                rgbD[2] = Double.valueOf(rgb[2]);
                double HUE = RGB2HUE(rgbD);
                double[] hsv = RGB2HSV(rgbD);
                double[] yuv = RGB2YUV(rgbD);
                boolean light = false;

                //strategy 1
                if(rgbD[0]>240 && rgbD[1]>240 &&rgbD[2]>240 ){
                    light = true;
                }

                if (((HUE - 240 > -30 && HUE - 240 < 15) && hsv[1]>0.2 )
                        ||((HUE - 240 > -50 && HUE - 240 < 20) && hsv[1]>0.01 &&light)

                        )

                //strategy 2

//                if (((HUE - 240 > -30 && HUE - 240 < 15) && hsv[1]>0.2 )
//                        ||((HUE - 240 > -50 && HUE - 240 < 20) && hsv[1]>0.01 && yuv[1]>245 )
//
//                        )
//strategy 3
//                if (((HUE - 240 > -30 && HUE - 240 < 15) && hsv[1] > 0.2)
//
//                        )

                {
//                if (hsv[1]>0.25) {
                    rgb[0] = 0;
                    rgb[1] = 0;
                    rgb[2] = 0;
                }
                int pixelN = 0;
                pixelN = (rgb[0] * 256 + rgb[1]) * 256 + rgb[2];
                if (pixelN > 8388608) {
                    pixelN = pixelN - 16777216;
                }

                bi.setRGB(i, j, pixelN);
            }
        }
        return bi;
    }



    public static BufferedImage coveyRGB2HUECORE_v2(String image) throws Exception {

        File file = new File(image);
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return coveyRGB2HUECORE_v2(bi);

    }


    public static double[] RGB2YUV(double[] rgb) {
        double R = rgb[0];
        double G = rgb[1];
        double B = rgb[2];
        double Y = 0.299 * R + 0.587 * G + 0.114 * B;
        double U = -0.169 * R - 0.331 * G + 0.5 * B + 128;
        double V = 0.5 * R - 0.419 * G - 0.081 * B + 128;

//        System.out.println(Y);
//        System.out.println(U);
//        System.out.println(V);
        double[] rgbN = new double[3];
        rgbN[0] = Y;
        rgbN[1] = U;
        rgbN[2] = V;
        return rgbN;
    }

    public static double RGB2HUE(double[] rgb) {
        double R = rgb[0];
        double G = rgb[1];
        double B = rgb[2];
        double theta = Math.atan2(sqrt3 * (G - B), 2 * R - G - B);
        return (theta / (Math.PI)) * 180 + 180;
    }


    public static double RGB2HUE2(double[] rgb) {
        double R = rgb[0];
        double G = rgb[1];
        double B = rgb[2];

        double theta = Math.atan((sqrt3 * (G - B)) / (2 * R - G - B));
//        if(theta<0){
//            theta = Math.PI+theta;
//        }
//        return 360-(theta/(Math.PI))*360;
        return theta;

    }

    public static double[] YUV2RGB(int[] rgb) {
        int Y = rgb[0];
        int U = rgb[1];
        int V = rgb[2];
        double R = Y + 1.403 * (V - 128);
        double G = Y - 0.343 * (U - 128) - 0.714 * (V - 128);
        double B = Y + 1.770 * (U - 128);
        double[] rgbD = new double[3];
        rgbD[0] = R;
        rgbD[1] = G;
        rgbD[2] = B;
        return rgbD;
    }


    public static double[] RGB2HSV(double[] rgb) {
        double R = rgb[0];
        double G = rgb[1];
        double B = rgb[2];

//        double V = StatUtils.max(rgb);

        double max = -1000;
        int indexV = 0;
        for (int i = 0; i < 3; i++) {
            if (rgb[i] > max)
                max = rgb[i];
            indexV = i;
        }
        double V = max;


        double S = 0.0;
        if (V != 0.0)
            S = (V - StatUtils.min(rgb)) / V;

        double H = 0.0;

        if (max != 0.0 && G != B && B != G && R != G) {
            if (indexV == 0) {
                H = 60 * (G - B) / (V - StatUtils.min(rgb));
            } else if (indexV == 1) {
                H = 120 + 60 * (B - G) / (V - StatUtils.min(rgb));
            } else if (indexV == 2) {
                H = 240 + 60 * (R - G) / (V - StatUtils.min(rgb));
            }
            if (H < 0)
                H = H + 360;
        }
//        System.out.println(H);
//        System.out.println(S);
//        System.out.println(V);
        double[] rgbN = new double[3];
        rgbN[0] = H;
        rgbN[1] = S;
        rgbN[2] = V;
        return rgbN;
    }
    //base64字符串转byte[]
    public static byte[] base64String2ByteFun(String base64Str){
        return Base64.decodeBase64(base64Str);
    }
    //byte[]转base64
    public static String byte2Base64StringFun(byte[] b){
        return Base64.encodeBase64String(b);
    }

}
