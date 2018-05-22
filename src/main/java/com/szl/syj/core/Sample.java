package com.szl.syj.core;

import com.baidu.aip.ocr.AipOcr;
import com.szl.syj.utils.ImageUtils;
import org.json.JSONObject;

import java.util.HashMap;


/**
 * Created by Administrator on 2018/3/12.
 */
public class Sample {
    //设置APPID/AK/SK
    public static final String APP_ID = "10912333";
    public static final String API_KEY = "7R8UIulfb4zqOgbaru6AuX9u";
    public static final String SECRET_KEY = "G4D7OVRCqZhAK3SKEz9v2W33wCkZ1zuo";

    public static String highResolution(AipOcr client) {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("detect_direction", "true");
        options.put("probability", "true");


        // 参数为本地图片路径
        String image = "test.jpg";
        JSONObject res = client.basicAccurateGeneral(image, options);
        System.out.println(res.toString(2));

        // 参数为本地图片二进制数组
        byte[] file = ImageUtils.getImageBinary("test.jpg");;
        res = client.basicAccurateGeneral(file, options);
        System.out.println(res.toString(2));
        return res.toString(2);

    }

    public void identification(AipOcr client) {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();


        // 参数为本地图片路径
        String image = "test.jpg";
        JSONObject res = client.businessLicense(image, options);
        System.out.println(res.toString(2));

        // 参数为本地图片二进制数组
        byte[] file = ImageUtils.getImageBinary("test.jpg");
        res = client.businessLicense(file, options);
        System.out.println(res.toString(2));

    }




    public static void main(String[] args) {
        // 初始化一个AipOcr
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

//        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
//        client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
//        client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
        // 也可以直接通过jvm启动参数设置此环境变量
        System.setProperty("aip.log4j.conf", "conf/log4j.properties");






        // 调用接口
        String path = "test.jpg";
        long start = System.currentTimeMillis();


        highResolution(client);
        long end = System.currentTimeMillis();


//        JSONObject res = client.basicGeneral(path, new HashMap<String, String>());
//        System.out.println(res.toString(2));
        System.out.println(end-start);
    }
}