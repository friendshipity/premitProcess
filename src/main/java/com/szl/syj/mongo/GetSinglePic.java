package com.szl.syj.mongo;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.szl.syj.utils.ImageUtils;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/5/16.
 */
public class GetSinglePic {
    public static void main(String[] args){
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger rootLogger = loggerContext.getLogger("org.mongodb.driver");
        rootLogger.setLevel(Level.OFF);


        MongoOperator operator = new MongoOperator();
        operator.connect();

        Map<String, String> map = new HashMap<>();
        String md5 = "0fee0ebd14a863decf23b779f792a3c8";
        map = operator.getPic("certificate_cache", md5);
        byte[] binary = null;
        binary = ImageUtils.base64String2ByteFun(map.get("base64"));
        BufferedImage bi = ImageUtils.bytes2ImageBuffer(binary);
        try {
            ImageIO.write(bi, "jpg", new File("test" +  ".jpg"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.currentTimeMillis();
    }
}
