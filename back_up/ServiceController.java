package com.szl.syj.controller;

import com.alibaba.fastjson.JSONObject;
import com.szl.syj.integrated.SinglePicProcess;
import com.szl.syj.utils.FileUtils;
import com.szl.syj.utils.ImageUtils;
import com.szl.syj.utils.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class ServiceController {


    @Autowired
    private SinglePicProcess singlePicProcess;
    @Autowired
    private Environment env;


    @RequestMapping("/s/test")
    String test() {
        return "证照识别接口工作正常";
    }

    @RequestMapping("/s/callService")
    String ping() {
        String res = "无法识别";
        int i = restfulCall();
        if (i == 1) {
            res = "食品经营";
        } else if (i == 2) {
            res = "餐饮服务";
        }
        return res;
    }


    @RequestMapping("/s/callService64")
    String ping64() {
        String res = "无法识别";
        int i = restfulCall64();
        if (i == 1) {
            res = "食品经营";
        } else if (i == 2) {
            res = "餐饮服务";
        }
        return res;
    }

    @RequestMapping("/s/callServiceFakeDetectTest")
    String pingFakeDetect() {
        String res = "0";
        int i = restfulCallMuti();
        if (i == 1) {
            res = "食品经营";
        } else if (i == 2) {
            res = "餐饮服务";
        }
        return res;
    }


    @RequestMapping("/s/validation_old_bd_fb")
    String pingvalidateOld11() {
        String res = "0";
        double i = restfulValidationOld("F:\\yang\\java\\Identification_recognition\\p_from_base643\\bd\\fb1\\");
        return String.valueOf(i);
    }
    @RequestMapping("/s/validation_bd_fb")
    String pingvalidateOld112() {
        String res = "0";
        double i = restfulValidation("F:\\yang\\java\\Identification_recognition\\p_from_base643\\bd\\fb1\\");
        return String.valueOf(i);
    }

    @RequestMapping("/s/validation_old_bd_1")
    String pingvalidateOld() {
        String res = "0";
        double i = restfulValidationOld("F:\\yang\\java\\Identification_recognition\\p_from_base643\\bd\\1\\");
        return String.valueOf(i);
    }

    @RequestMapping("/s/validation_bd_1")
    String pingvalidate() {
        String res = "0";
        double i = restfulValidation("F:\\yang\\java\\Identification_recognition\\p_from_base643\\bd\\1\\");
        return String.valueOf(i);
    }
    @RequestMapping("/s/validation_old_elm_1")
    String pingvalidateOld1() {
        String res = "0";
        double i = restfulValidationOld("F:\\yang\\java\\Identification_recognition\\p_from_base643\\elm\\1\\");
        return String.valueOf(i);
    }

    @RequestMapping("/s/validation_elm_1")
    String pingvalidate1() {
        String res = "0";
        double i = restfulValidation("F:\\yang\\java\\Identification_recognition\\p_from_base643\\elm\\1\\");
        return String.valueOf(i);
    }
    @RequestMapping("/s/validation_old_mt_1")
    String pingvalidateOld2() {
        String res = "0";
        double i = restfulValidationOld("F:\\yang\\java\\Identification_recognition\\p_from_base643\\mt\\1\\");
        return String.valueOf(i);
    }

    @RequestMapping("/s/validation_mt_1")
    String pingvalidate2() {
        String res = "0";
        double i = restfulValidation("F:\\yang\\java\\Identification_recognition\\p_from_base643\\mt\\1\\");
        return String.valueOf(i);
    }










    @RequestMapping("/s/validation_old_bd_0")
    String pingvalidateOld0() {
        String res = "0";
        double i = restfulValidationOld("F:\\yang\\java\\Identification_recognition\\p_from_base643\\bd\\0\\");
        return String.valueOf(i);
    }

    @RequestMapping("/s/validation_bd_0")
    String pingvalidate0() {
        String res = "0";
        double i = restfulValidation("F:\\yang\\java\\Identification_recognition\\p_from_base643\\bd\\0\\");
        return String.valueOf(i);
    }
    @RequestMapping("/s/validation_old_elm_0")
    String pingvalidateOld10() {
        String res = "0";
        double i = restfulValidationOld("F:\\yang\\java\\Identification_recognition\\p_from_base643\\elm\\0\\");
        return String.valueOf(i);
    }

    @RequestMapping("/s/validation_elm_0")
    String pingvalidate10() {
        String res = "0";
        double i = restfulValidation("F:\\yang\\java\\Identification_recognition\\p_from_base643\\elm\\0\\");
        return String.valueOf(i);
    }
    @RequestMapping("/s/validation_old_mt_0")
    String pingvalidateOld20() {
        String res = "0";
        double i = restfulValidationOld("F:\\yang\\java\\Identification_recognition\\p_from_base643\\mt\\0\\");
        return String.valueOf(i);
    }

    @RequestMapping("/s/validation_mt_0")
    String pingvalidate20() {
        String res = "0";
        double i = restfulValidation("F:\\yang\\java\\Identification_recognition\\p_from_base643\\mt\\0\\");
        return String.valueOf(i);
    }




















    @Async
    @PostMapping("/s/receive")
    public int permitClassify(@RequestBody byte[] result) throws InterruptedException {

        int flag = 0;
        try {
            long s = System.currentTimeMillis();
            flag = singlePicProcess.RuleOCR(result);
            long e = System.currentTimeMillis();
//            System.out.println(e - s + "ms");

        } catch (Exception e) {

            System.err.println("err occurs");
            e.printStackTrace();
        }


//
//        if(flag!=1){
//            try {
//                ImageIO.write(ImageUtils.bytes2ImageBuffer(result), "jpg", new File("p_err2/"+ flag+"_"+UUID.randomUUID()+".jpg"));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }


//        System.out.println("completed..");
        return flag;
    }


    @Async
    @PostMapping("/s/receive64")
    public int permitClassify64(@RequestBody String result) throws InterruptedException {
        byte[] bi = ImageUtils.base64String2ByteFun(result);
        int flag = 0;
        try {
            long s = System.currentTimeMillis();
            flag = singlePicProcess.RuleOCR(bi);
            long e = System.currentTimeMillis();
            System.out.println(e - s + "ms");

        } catch (Exception e) {

            System.err.println("err occurs");
            e.printStackTrace();
        }
//        System.out.println("completed..");

//        if(flag==1){
//            try {
//                ImageIO.write(ImageUtils.bytes2ImageBuffer(bi), "jpg", new File("p_food_sell_permit/"+ UUID.randomUUID()+".jpg"));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        return flag;
    }

    @Async
    @PostMapping("/s/receive2")
    public int fakeDetect(@RequestBody byte[] result) throws InterruptedException {

        int flag = 0;
        try {
            long s = System.currentTimeMillis();
            flag = singlePicProcess.RuleOCR2(result);
            long e = System.currentTimeMillis();
//            System.out.println(e - s + "ms");

        } catch (Exception e) {

            System.err.println("err occurs");
            e.printStackTrace();
        }
//        System.out.println("completed..");
        return flag;
    }


    public int restfulCall() {
        RestTemplate restTemplate = new RestTemplate();
        String image = "test2.jpeg";
        byte[] imageBinaries = ImageUtils.getImageBinary(image);
        String url = "http://172.27.2.141:8080/s/receive";
        int flag = restTemplate.postForObject(url, imageBinaries, int.class, "test");
        return flag;
//        int flag = 0;
//        return flag;
    }


    public int restfulCall64() {
//        RestTemplate restTemplate = new RestTemplate();
//        List<String> pics =  TextUtils.loadCert264("base64/cert.csv");
//        String url = "http://172.27.2.141:8080/s/receive64";
//        int flag = restTemplate.postForObject(url, pics.get(4), int.class,"test");
        int flag = 0;
        return flag;
    }


    public int restfulCallMuti64() {
//        RestTemplate restTemplate = new RestTemplate();
//        List<String> pics =  TextUtils.loadCert264("base64/cert.csv");
        int flag = 0;
//        for(String pic : pics) {
//            String url = "http://172.27.2.141:8080/s/receive64";
//            flag = restTemplate.postForObject(url, pic, int.class, "test");
//        }
////
////        String image = "test2.jpeg";
////        byte[] imageBinaries = ImageUtils.getImageBinary(image);
////        String url = "http://172.27.2.141:8080/s/receive2";
////        int flag = restTemplate.postForObject(url, imageBinaries, int.class,"test");
        return flag;
    }


    public int restfulCallMuti() {
        int flag = 0;
        RestTemplate restTemplate = new RestTemplate();
//        List<String> pics =  TextUtils.loadCert264("base64/cert.csv");
//        List<byte[]> pics =  FileUtils.readFilsAsByte("F:\\yang\\java\\Identification_recognition\\p_from_base642");
        List<byte[]> pics = FileUtils.readFilsAsByte("F:\\yang\\springBootTest\\promitcess\\premitProcess\\p_liutong");

        for (byte[] pic : pics) {
//            String url = "http://172.27.2.141:8081/s/receive2";
            String url = "http://172.27.2.99:8081/s/receive";
            flag = restTemplate.postForObject(url, pic, int.class, "test");
        }
//
//        String image = "test2.jpeg";
//        byte[] imageBinaries = ImageUtils.getImageBinary(image);
//        String url = "http://172.27.2.141:8080/s/receive2";
//        int flag = restTemplate.postForObject(url, imageBinaries, int.class,"test");

        return flag;
    }


    public double restfulValidation(String path) {
        int flag = 0;
        RestTemplate restTemplate = new RestTemplate();

        List<byte[]> pics = FileUtils.readFilsAsByte(path);
        int all = pics.size();
        int right = 0;
        System.out.println("start");
        for (byte[] pic : pics) {
            String url = "http://172.27.2.141:8081/s/receive";
//            String url = "http://172.27.2.99:8081/s/receive";
            flag = restTemplate.postForObject(url, pic, int.class, "test");
            if (flag != 0)
                right++;
        }
        System.out.println("----over all algorithm rate----");
        System.out.println(all);
        System.out.println(right);
//
//        String image = "test2.jpeg";
//        byte[] imageBinaries = ImageUtils.getImageBinary(image);
//        String url = "http://172.27.2.141:8080/s/receive2";
//        int flag = restTemplate.postForObject(url, imageBinaries, int.class,"test");

        return ((double)right/(double)all);
    }


    public double restfulValidationOld(String path) {// base64
        String res = null;
        RestTemplate restTemplate = new RestTemplate();
        List<byte[]> pics = FileUtils.readFilsAsByte(path);
        List<String> pics2 = new ArrayList<>();

        System.out.println("old start");
        for(byte[] pic :pics){
            pics2.add(ImageUtils.byte2Base64StringFun(pic));
        }
        int all = pics2.size();
        int right = 0;
        for (String pic : pics2) {
            MultiValueMap<String, String> requestEntity = new LinkedMultiValueMap<String, String>();
            requestEntity.set("imagestr", pic);
            String url = "http://172.27.9.145:5009/extract_upload";
//            String url = "http://172.27.2.99:8081/s/receive";
//            pic = "imagestr=\""+pic+"\"";
            try {
                res = restTemplate.postForObject(url, requestEntity, String.class);
            }catch (Exception e){
                requestEntity.get("imagestr");
            }
            JSONObject jso  =JSONObject.parseObject(res);

            if(jso.containsKey("category"))
            if (jso.get("category").toString().startsWith("class2"))
                right++;
//            System.out.println("sdf");








        }
        System.out.println("----over all algorithm rate----old");
        System.out.println(all);
        System.out.println(right);

        return ((double)right/(double)all);
    }

}