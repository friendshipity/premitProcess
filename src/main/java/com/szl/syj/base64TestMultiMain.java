package com.szl.syj;



import com.szl.syj.utils.ImageUtils;
import com.szl.syj.utils.TextUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2018/3/16.
 */
public class base64TestMultiMain  {
    private static   int cur=80;


    public static void main(String[] arges){
        ExecutorService executorService= Executors.newFixedThreadPool(5);
        while(cur<90){
            base64TestMulti base64TestMulti = new base64TestMulti();
            base64TestMulti.setGroupNum(cur++);
            executorService.execute(base64TestMulti);
        }
        executorService.shutdown();

    }
}
