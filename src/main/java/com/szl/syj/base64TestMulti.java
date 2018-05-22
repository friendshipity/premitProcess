package com.szl.syj;



import com.szl.syj.utils.ImageUtils;
import com.szl.syj.utils.TextUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2018/3/16.
 */
public class base64TestMulti implements Runnable{
    public void setGroupNum(int groupNum) {
        this.groupNum = groupNum;
    }

    private   int groupNum;


    public void run(){


//
//        List<String> pics = TextUtils.loadList("D:\\data\\img_certMD5_base64_1.csv");
//        int counter = 0;
//        for (String base64 : pics) {
//            if(base64.length()<20)
//                continue;
//
//            byte[] binary = null;
//            binary = ImageUtils.base64String2ByteFun(base64);
//            BufferedImage bi = ImageUtils.bytes2ImageBuffer(binary);
//            try {
//                ImageIO.write(bi, "jpg", new File("test" + String.valueOf(counter++) + ".jpg"));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
////            counter++;
//        }









        System.out.println(this.groupNum);
        for(int i =this.groupNum;i<this.groupNum+1;i++) {
            List<String> picss = TextUtils.loadList2("D:\\data\\img_certMD5_base64_"+String.valueOf(i)+".csv");
//            List<String> picss = TextUtils.loadList("D:\\data\\img_certMD5_base64_"+String.valueOf(i)+".csv");
            int counter = 0;
            for (String base64 : picss) {

                byte[] binary = null;
                binary = ImageUtils.base64String2ByteFun(base64);
                BufferedImage bi = ImageUtils.bytes2ImageBuffer(binary);
                try {
//                    ImageIO.write(bi, "jpg", new File("pic/0"+String.valueOf(i)+"/" + String.valueOf(counter++) + ".jpg"));
                    ImageIO.write(bi, "jpg", new File("d:/data/pic/0"+String.valueOf(i)+"/" + String.valueOf(counter++) + ".jpg"));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
//        byte[] bi = ImageUtils.base64String2ByteFun(pics.get(0));
        System.out.println("123");
    }
}
