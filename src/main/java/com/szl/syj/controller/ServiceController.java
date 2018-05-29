package com.szl.syj.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.szl.syj.DuplicatedPermitSearchRemote;
import com.szl.syj.DuplicatedPermitSearchRemoteTg;
import com.szl.syj.DuplicatedPermitSearchRemoteWm;
import com.szl.syj.integrated.RuleOCR6__;
import com.szl.syj.integrated.SinglePicProcess;
import com.szl.syj.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;
@EnableAsync
@RestController
public class ServiceController {


    @Autowired
    private SinglePicProcess singlePicProcess;
    @Autowired
    private Environment env;
    @Autowired
    private DuplicatedPermitSearchRemoteTg duplicatedPermitSearchRemoteTg;
    @Autowired
    private DuplicatedPermitSearchRemoteWm duplicatedPermitSearchRemoteWm;



    int ThreadNum = 10;
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

    @RequestMapping("/callServiceFakeDetectTest")
    String pingFakeDetect() {
        String res = "0";
//        double i = restfulFakeDetectTest("p_fuzzy");
        double i = restfulFakeDetectTest("p_feedback_errs");
//        double i = restfulFakeDetectTest("p_fuzzy5");

        return res;
    }

    @RequestMapping("/callServiceDensCheckTest")
    String pingDensCheck() {
        String res = "0";
//        double i = restfulFakeDetectTest("p_fuzzy");
        double i = restfulDensityCheckTest("p_liutong");

        return res;
    }

    @RequestMapping("/callServiceGetSignature")
    void pingGetSignature() {
        try {
            restfulGetSignatureTest("p_newOld");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/callServiceGetSignature64")
    void pingGetSignature64() throws FileNotFoundException {
        List<File> fileList = Arrays.asList(new File("base64" + File.separator).listFiles());

        for (File file : fileList) {
            try {
                restfulGetSignatureTest64(file.getAbsolutePath());
//            restfulGetSignatureTest64("base64/img_certMD5_base64_1_0.csv");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    //
    @RequestMapping("/callServiceGetSignatureSimple")
    void pingGetSignatureSimple() {
        try {
            restfulGetSignatureSimpleTest("p_newOld");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
//
//
//    @RequestMapping("/s/validation_old_bd_fb")
//    String pingvalidateOld11() {
//        String res = "0";
//        double i = restfulValidationOld("F:\\yang\\java\\Identification_recognition\\p_from_base643\\bd\\fb1\\");
//        return String.valueOf(i);
//    }
//
//    @RequestMapping("/s/validation_bd_fb")
//    String pingvalidateOld112() {
//        String res = "0";
//        double i = restfulValidation("F:\\yang\\java\\Identification_recognition\\p_from_base643\\bd\\fb1\\");
//        return String.valueOf(i);
//    }
//
//    @RequestMapping("/s/validation_old_bd_1")
//    String pingvalidateOld() {
//        String res = "0";
//        double i = restfulValidationOld("F:\\yang\\java\\Identification_recognition\\p_from_base643\\bd\\1\\");
//        return String.valueOf(i);
//    }
//
//    @RequestMapping("/s/validation_bd_1")
//    String pingvalidate() {
//        String res = "0";
//        double i = restfulValidation("F:\\yang\\java\\Identification_recognition\\p_from_base643\\bd\\1\\");
//        return String.valueOf(i);
//    }
//
//    @RequestMapping("/s/validation_old_elm_1")
//    String pingvalidateOld1() {
//        String res = "0";
//        double i = restfulValidationOld("F:\\yang\\java\\Identification_recognition\\p_from_base643\\elm\\1\\");
//        return String.valueOf(i);
//    }
//
//    @RequestMapping("/s/validation_elm_1")
//    String pingvalidate1() {
//        String res = "0";
//        double i = restfulValidation("F:\\yang\\java\\Identification_recognition\\p_from_base643\\elm\\1\\");
//        return String.valueOf(i);
//    }
//
//    @RequestMapping("/s/validation_old_mt_1")
//    String pingvalidateOld2() {
//        String res = "0";
//        double i = restfulValidationOld("F:\\yang\\java\\Identification_recognition\\p_from_base643\\mt\\1\\");
//        return String.valueOf(i);
//    }
//
//    @RequestMapping("/s/validation_mt_1")
//    String pingvalidate2() {
//        String res = "0";
//        double i = restfulValidation("F:\\yang\\java\\Identification_recognition\\p_from_base643\\mt\\1\\");
//        return String.valueOf(i);
//    }
//
//
//    @RequestMapping("/s/validation_old_bd_0")
//    String pingvalidateOld0() {
//        String res = "0";
//        double i = restfulValidationOld("F:\\yang\\java\\Identification_recognition\\p_from_base643\\bd\\0\\");
//        return String.valueOf(i);
//    }
//
//    @RequestMapping("/s/validation_bd_0")
//    String pingvalidate0() {
//        String res = "0";
//        double i = restfulValidation("F:\\yang\\java\\Identification_recognition\\p_from_base643\\bd\\0\\");
//        return String.valueOf(i);
//    }
//
//    @RequestMapping("/s/validation_old_elm_0")
//    String pingvalidateOld10() {
//        String res = "0";
//        double i = restfulValidationOld("F:\\yang\\java\\Identification_recognition\\p_from_base643\\elm\\0\\");
//        return String.valueOf(i);
//    }
//
//    @RequestMapping("/s/validation_elm_0")
//    String pingvalidate10() {
//        String res = "0";
//        double i = restfulValidation("F:\\yang\\java\\Identification_recognition\\p_from_base643\\elm\\0\\");
//        return String.valueOf(i);
//    }
//
//    @RequestMapping("/s/validation_old_mt_0")
//    String pingvalidateOld20() {
//        String res = "0";
//        double i = restfulValidationOld("F:\\yang\\java\\Identification_recognition\\p_from_base643\\mt\\0\\");
//        return String.valueOf(i);
//    }
//
//    @RequestMapping("/s/validation_mt_0")
//    String pingvalidate20() {
//        String res = "0";
//        double i = restfulValidation("F:\\yang\\java\\Identification_recognition\\p_from_base643\\mt\\0\\");
//        return String.valueOf(i);
//    }

    @RequestMapping("/s/validation_old_1000_1")
    String pingvalidateOld1000() {
        String res = "0";
        double i = restfulValidationOld("F:\\yang\\java\\Identification_recognition\\p_from_base646\\1\\");
        return String.valueOf(i);
    }

    @RequestMapping("/s/validation_1000_1")
    String pingvalidate1000() {
        String res = "0";
        double i = restfulValidation64("F:\\yang\\java\\Identification_recognition\\p_from_base646\\1\\");
        return String.valueOf(i);
    }

    @RequestMapping("/s/validation_old_1000_0")
    String pingvalidateOld10000() {
        String res = "0";
        double i = restfulValidationOld("F:\\yang\\java\\Identification_recognition\\p_from_base646\\0\\");
        return String.valueOf(i);
    }

    @RequestMapping("/s/validation_1000_0")
    String pingvalidate10000() {
        String res = "0";
        double i = restfulValidation64("F:\\yang\\java\\Identification_recognition\\p_from_base646\\0\\");
        return String.valueOf(i);
    }

    @RequestMapping("/s/vali")
    String vali() {
        String res = "0";
        double i = restfulValidation("F:\\yang\\springBootTest\\promitcess\\premitProcess\\p_fuzzy7");
        return String.valueOf(i);
    }

    @RequestMapping("/s/vali64")
    String vali64() {
        String res = "0";
        double i = restfulValidation("F:\\yang\\springBootTest\\promitcess\\premitProcess\\p_fuzzy7");
        return String.valueOf(i);
    }

    @Async
    @PostMapping("/extract_upload")
    public JSONObject permitClassify642(@RequestBody MultiValueMap<String, String> result) throws InterruptedException {

        String str = result.get("imagestr").toString();//取到base64编码格式的图片
        str = str.substring(1, str.length() - 1);
        byte[] bi = ImageUtils.base64String2ByteFun(str);//转换为byte[]格式
        int flag = 0;
        try {
            flag = singlePicProcess.RuleOCR(bi);//主要处理部分，返回图片分类结果
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject jso = new JSONObject();


        if (flag == 0) {
            jso.put("category", "class3");
            jso.put("errorno", "0");
        } else if (flag == 1) {

            jso.put("category", "class22");
            jso.put("errorno", "0");
        } else if (flag == 2) {
            jso.put("category", "class21");
            jso.put("errorno", "0");
        }
        return jso;
    }


    @Async
    @PostMapping("/s/receive")
    public int permitClassify(@RequestBody byte[] result) throws InterruptedException {

        int flag = 0;
        try {
            flag = singlePicProcess.RuleOCR(result);
//            System.out.println(e - s + "ms");

        } catch (Exception e) {

            System.err.println("err occurs");
            e.printStackTrace();
        }


        return flag;
    }


    @Async
    @PostMapping("/s/receive64")
    public int permitClassify64(@RequestBody String result) throws InterruptedException {
        byte[] bi = ImageUtils.base64String2ByteFun(result);
        int flag = 0;
        try {
            flag = singlePicProcess.RuleOCR(bi);
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
    @PostMapping("/fakeDetec")
    public boolean fakeDetect(@RequestBody byte[] result) throws InterruptedException {

        boolean flag = false;
        JSONObject jso = new JSONObject();
        try {
            long s = System.currentTimeMillis();
            flag = singlePicProcess.RuleOCR2(result);
            long e = System.currentTimeMillis();
            System.out.println(e - s + "ms");

        } catch (Exception e) {
            System.err.println("err occurs");
            e.printStackTrace();
        }
        return flag;
    }


    @Async
    @PostMapping("/getSignature")
    public String signature(@RequestBody byte[] result) throws InterruptedException {

        String sigNature = "";
        try {
            sigNature = singlePicProcess.RuleOCR4(result);
        } catch (Exception e) {
            System.err.println("err occurs");
            e.printStackTrace();
        }
        return sigNature;
    }

    @Async
    @PostMapping("/getSignatureSimple")
    public String signatureSimple(@RequestBody byte[] result) throws InterruptedException {

        String sigNature = "";
        try {
            sigNature = singlePicProcess.RuleOCR5(result);
        } catch (Exception e) {
            System.err.println("err occurs");
            e.printStackTrace();
        }
//        System.out.println(sigNature);
        return sigNature;
    }


    @Async
    @PostMapping("/densityCheck")
    public double densityCheck(@RequestBody byte[] result) throws InterruptedException {

        double flag = 0.0;
        JSONObject jso = new JSONObject();
        try {
            long s = System.currentTimeMillis();
            flag = singlePicProcess.RuleOCR3(result);
            long e = System.currentTimeMillis();
            System.out.println(e - s + "ms" + "_density:" + flag);
        } catch (Exception e) {
            System.err.println("err occurs");
            e.printStackTrace();
        }
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
        RestTemplate restTemplate = new RestTemplate();
        String image = "6a63f6246b600c33a081f124124c510fd8f9a165.jpg";
        byte[] imageBinaries = ImageUtils.getImageBinary(image);
        String bs64 = ImageUtils.byte2Base64StringFun(imageBinaries);
        MultiValueMap<String, String> requestEntity = new LinkedMultiValueMap<String, String>();
        requestEntity.set("imagestr", bs64);
//            String url = "http://172.27.9.145:5009/extract_upload";
//            String url = "http://172.27.2.99:8081/s/receive";
//            pic = "imagestr=\""+pic+"\"";


//        String pics =  TextUtils.loadCert264("base64/cert.csv");+

        String res = null;
        String url = "http://172.27.2.141:8081/extract_upload";
        try {
            res = restTemplate.postForObject(url, requestEntity, String.class);
        } catch (Exception e) {
            requestEntity.get("imagestr");
        }
        JSONObject jso = JSONObject.parseObject(res);
        System.out.println(res.toString());
        return 0;
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


//    public int restfulCallMuti() {
//        int flag = 0;
//        RestTemplate restTemplate = new RestTemplate();
////        List<String> pics =  TextUtils.loadCert264("base64/cert.csv");
////        List<byte[]> pics =  FileUtils.readFilsAsByte("F:\\yang\\java\\Identification_recognition\\p_from_base642");
//        List<byte[]> pics = FileUtils.readFilsAsByte("F:\\yang\\springBootTest\\promitcess\\premitProcess\\p_liutong");
//
//        for (byte[] pic : pics) {
////            String url = "http://172.27.2.141:8081/s/receive2";
//            String url = "http://172.27.2.99:8081/s/receive";
//            flag = restTemplate.postForObject(url, pic, int.class, "test");
//        }
////
////        String image = "test2.jpeg";
////        byte[] imageBinaries = ImageUtils.getImageBinary(image);
////        String url = "http://172.27.2.141:8080/s/receive2";
////        int flag = restTemplate.postForObject(url, imageBinaries, int.class,"test");
//
//        return flag;
//    }
//
//

    public double restfulFakeDetectTest(String path) {
        int flag = 0;
        JSONArray jsa = new JSONArray();
        RestTemplate restTemplate = new RestTemplate();
//        List<byte[]> pics = FileUtils.readFilsAsByte(path);


        List<File> fileList = Arrays.asList(new File(path).listFiles());
        int all = fileList.size();
        int index = 0;


//        TextUtils.writeList(dictProcess.getDictMap(),"dict.txt");

        boolean flagB = false;
        System.out.println("fake start");
        for (File file : fileList) {
            byte[] pic = ImageUtils.getImageBinary(file.getAbsolutePath());
            String url = "http://172.27.2.141:8081/fakeDetec";
            System.out.println(flag + "/" + index);
//            System.out.println(file.getAbsolutePath());
//            System.out.println(file.getName());
            boolean res = restTemplate.postForObject(url, pic, boolean.class, "test");
            if (res) {
                flag++;
                try {
                    BufferedImage bi = null;
                    try {
                        bi = ImageIO.read(file);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    ImageIO.write(bi, "jpg", new File("p_clear6/" + file.getName()));
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }

            } else {
                try {
                    BufferedImage bi = null;
                    try {
                        bi = ImageIO.read(file);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    ImageIO.write(bi, "jpg", new File("p_fuzzy6/" + file.getName()));
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
            index++;

        }
//        System.out.println((double) flag/(double) pics.size());
//        try {
//            File file = new File("JSON_RES1.json");
//            if (file.exists()) { // 如果已存在,删除旧文件
//                file.delete();
//            }
//            file.createNewFile();
//            Writer write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
//            write.write(jsa.toString());
//            write.flush();
//            write.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        String image = "test2.jpeg";
//        byte[] imageBinaries = ImageUtils.getImageBinary(image);
//        String url = "http://172.27.2.141:8080/s/receive2";
//        int flag = restTemplate.postForObject(url, imageBinaries, int.class,"test");

        return 0;
    }


    public double restfulGetSignatureTest64(String path) throws FileNotFoundException {
        RestTemplate restTemplate1 = new RestTemplate();
        RestTemplate restTemplate2 = new RestTemplate();
        FileReader fr = new FileReader(path);
        Scanner in = new Scanner(fr);
        if (path.split("_")[3].equals("0.csv"))
            in.nextLine();
        String line;
        String temp[];
        System.out.println("signature 64 start at " + path);
        PrintWriter pw = new PrintWriter(path.split("_")[3].toString());
        Set<String> set;

        String cachePath = "data" + File.separator + "cache.txt";
        File file = new File(cachePath);
        if (file.exists()) {
            set = new HashSet<>(TextUtils.loadList(cachePath));
        } else {
            set = new HashSet<>();
        }
        int counter = 1;
        while (in.hasNextLine()) {
            line = in.nextLine();

            String pic64 = line;
            boolean dup = false;
            String res = null;
            //category test
//            String url1 = "http://172.27.2.141:8081/s/receive";
            MultiValueMap<String, String> requestEntity = new LinkedMultiValueMap<String, String>();
            requestEntity.set("imagestr", pic64);
//            String url1 = "http://172.27.9.145:5009/extract_upload";
            String url1 = "http://172.27.2.99:5009/extract_upload";
            try {
                res = restTemplate1.postForObject(url1, requestEntity, String.class);
            } catch (Exception e) {
                requestEntity.get("imagestr");
            }


            JSONObject jso = JSONObject.parseObject(res);

            //signature write
            if (jso != null) {
                if (jso.containsKey("category")) {
                    if (jso.get("category").toString().startsWith("class2")) {
                        byte[] pic = ImageUtils.base64String2ByteFun(line);
//                    String url = "http://172.27.2.141:8081/getSignature";
                        String url = "http://172.27.2.100:8081/getSignature";
                        String sig = restTemplate2.postForObject(url, pic, String.class, "test");
                        try {
                            if (sig != null)
                                if (!sig.equals("") && !sig.equals("null")) {
                                    if (!set.contains(sig.split(",")[1])) {
                                        set.add(sig.split(",")[1]);
                                    } else {
                                        dup = true;
                                    }
                                }
                        } catch (Exception e) {
                            System.err.println("---" + sig);
                        }
                        pw.println(String.valueOf(counter) + "," + path.split("_")[3].replaceAll(".csv", "") + "," + jso.get("category").toString() + "," + sig + "," + dup);
                    } else {
                        pw.println(String.valueOf(counter) + "null");

                    }
                } else {
                    pw.println(String.valueOf(counter) + "null");
                }
            } else {
                pw.println(String.valueOf(counter) + "null");
            }
            counter++;
        }
        pw.close();
        TextUtils.writeList(new ArrayList<>(set), cachePath);
        System.out.println("signature 64 completed at " + path);
        return 0;
    }


    public double restfulGetSignatureTest(String path) throws FileNotFoundException {
        RestTemplate restTemplate1 = new RestTemplate();
        RestTemplate restTemplate2 = new RestTemplate();
        List<File> fileList = Arrays.asList(new File(path).listFiles());
        System.out.println("signature start");

        PrintWriter pw = new PrintWriter("signature1.csv");
        for (File file : fileList) {
            byte[] pic = ImageUtils.getImageBinary(file.getAbsolutePath());

            //category test
            String url1 = "http://172.27.2.141:8081/s/receive";
            int category = restTemplate1.postForObject(url1, pic, int.class, "test");

            //signature write
            if (category != 0) {
                String url2 = "http://172.27.2.141:8081/getSignature";
                String sig = restTemplate2.postForObject(url2, pic, String.class, "test");
                pw.println(file.getName() + "," + sig);
            }
        }
        pw.close();
        return 0;
    }


    public double restfulGetSignatureSimpleTest(String path) throws FileNotFoundException {
        RestTemplate restTemplate = new RestTemplate();
        List<File> fileList = Arrays.asList(new File(path).listFiles());
        System.out.println("simple signature start");
        PrintWriter pw = new PrintWriter("signatureSimple.csv");
        for (File file : fileList) {
            byte[] pic = ImageUtils.getImageBinary(file.getAbsolutePath());
            String url = "http://172.27.2.141:8081/getSignatureSimple";
            String sig = restTemplate.postForObject(url, pic, String.class, "test");
            pw.println(file.getName() + "," + sig);
        }
        pw.close();
        System.out.println("completed");
        return 0;
    }


    public double restfulDensityCheckTest(String path) {
        int flag = 0;
        JSONArray jsa = new JSONArray();
        RestTemplate restTemplate = new RestTemplate();
        List<File> fileList = Arrays.asList(new File(path).listFiles());
        int all = fileList.size();
        int index = 0;
        boolean flagB = false;
        System.out.println("density start");
        for (File file : fileList) {
            byte[] pic = ImageUtils.getImageBinary(file.getAbsolutePath());
            String url = "http://172.27.2.141:8081/densityCheck";
            double res = restTemplate.postForObject(url, pic, double.class, "test");


            try {
                BufferedImage bi = null;
                try {
                    bi = ImageIO.read(file);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                ImageIO.write(bi, "jpg", new File("p_fuzzy5/" + String.valueOf(res / 1000) + "__" + file.getName()));
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
//
            index++;

        }
//        System.out.println((double) flag/(double) pics.size());
//        try {
//            File file = new File("JSON_RES1.json");
//            if (file.exists()) { // 如果已存在,删除旧文件
//                file.delete();
//            }
//            file.createNewFile();
//            Writer write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
//            write.write(jsa.toString());
//            write.flush();
//            write.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        String image = "test2.jpeg";
//        byte[] imageBinaries = ImageUtils.getImageBinary(image);
//        String url = "http://172.27.2.141:8080/s/receive2";
//        int flag = restTemplate.postForObject(url, imageBinaries, int.class,"test");

        return 0;
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

        return ((double) right / (double) all);
    }

    ////
////
    public double restfulValidationOld(String path) {// base64
        String res = null;
        RestTemplate restTemplate = new RestTemplate();
        List<byte[]> pics = FileUtils.readFilsAsByte(path);
        List<String> pics2 = new ArrayList<>();

        System.out.println("old start");
        for (byte[] pic : pics) {
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
            } catch (Exception e) {
                requestEntity.get("imagestr");
            }
            JSONObject jso = JSONObject.parseObject(res);

            if (jso.containsKey("category"))
                if (jso.get("category").toString().startsWith("class2")) {
                    right++;

                    try {
                        BufferedImage bi = null;
                        try {
                            bi = ImageUtils.bytes2ImageBuffer(ImageUtils.base64String2ByteFun(pic));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        ImageIO.write(bi, "jpg", new File("p_xxy/" + UUID.randomUUID() + ".jpg"));
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                } else {
//                    try {
//                        BufferedImage bi = null;
//                        try {
//                            bi = ImageUtils.bytes2ImageBuffer(ImageUtils.base64String2ByteFun(pic));
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//                        ImageIO.write(bi, "jpg", new File("p_xxy" + UUID.randomUUID()+".jpg"));
//                    } catch (IOException ioe) {
//                        ioe.printStackTrace();
//                    }
                }

        }
        System.out.println("----over all algorithm rate----old");
        System.out.println(all);
        System.out.println(right);

        return ((double) right / (double) all);
    }


    public double restfulValidation64(String path) {
        String res = null;
        RestTemplate restTemplate = new RestTemplate();
        List<byte[]> pics = FileUtils.readFilsAsByte(path);
        List<String> pics2 = new ArrayList<>();

        System.out.println("new start");
        for (byte[] pic : pics) {
            pics2.add(ImageUtils.byte2Base64StringFun(pic));
        }
        int all = pics2.size();
        int right = 0;
        for (String pic : pics2) {
            MultiValueMap<String, String> requestEntity = new LinkedMultiValueMap<String, String>();
            requestEntity.set("imagestr", pic);
            String url = "http://172.27.2.99:8081/extract_upload";
//            String url = "http://172.27.2.99:8081/s/receive";
//            pic = "imagestr=\""+pic+"\"";
            try {
                res = restTemplate.postForObject(url, requestEntity, String.class);
            } catch (Exception e) {
                requestEntity.get("imagestr");
            }
            JSONObject jso = JSONObject.parseObject(res);

            if (jso.containsKey("category"))
                if (jso.get("category").toString().startsWith("class2"))
                    right++;
//            System.out.println("sdf");


        }
        System.out.println("----over all algorithm rate----");
        System.out.println(all);
        System.out.println(right);

        return ((double) right / (double) all);
    }


    /////////////////////////////////////////batch test
    @Async
    @PostMapping("/getSignatureBatch")
    public String signatureBatch( @RequestBody MultiValueMap<String, List<Triple>> in) throws InterruptedException, ExecutionException {
        List pairslist = in.get("pairs");
        int ThreadNum = pairslist.size();
        Triple[] pairs =new Triple[ThreadNum];
        for(int i  =0;i< pairslist.size();i++){
            pairs[i]=(Triple) pairslist.get(i);
        }


        ExecutorService pool = Executors.newFixedThreadPool(ThreadNum);
        Callable[] ocr6__ = new Callable[ThreadNum];
        for (int i = 0; i < ThreadNum; i++) {
            ocr6__[i] = new RuleOCR6__(pairs[i].getS().toString(), pairs[i].getV().toString(), pairs[i].getT().toString());
        }
        Future[] futures = new Future[ThreadNum];

        for (int i = 0; i < ThreadNum; i++) {
            futures[i] = pool.submit(ocr6__[i]);
        }
        List<String> ress = new ArrayList<>();
        for (int i = 0; i < ThreadNum; i++) {
            ress.add((String) futures[i].get());
        }
        pool.shutdown();
        List<String> results = new ArrayList<>();
        for (String res : ress) {
            String oid = res.split("\\|")[1];
            String restr = res.split("\\|")[0];
            String clasz = res.split("\\|")[2];
            String sigNature = "";
            if (clasz.startsWith("class2")) {
                try {
                    sigNature = singlePicProcess.RuleOCR6_over(restr);
                } catch (Exception e) {
                    System.err.println("err occurs");
                    e.printStackTrace();
                }
            }

            results.add(sigNature + "|" + oid+"|"+clasz);
        }
//        JSONArray jsa  =new JSONArray();
//        for(int j=0;j<results.size();j++){
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("sig",results.get(j));
//            jsa.add(jsonObject);
//        }
        String res ="";
        for(String result:results){
            res = res+","+result;
        }
        return res.substring(1,res.length());
    }


    ////////////////////////////////////
    //////////////////////combine  tets
    public double restfulGetSignatureTestBatch64(String path) throws FileNotFoundException {
        RestTemplate restTemplate1 = new RestTemplate();
        RestTemplate restTemplate2 = new RestTemplate();
        FileReader fr = new FileReader(path);
        Scanner in = new Scanner(fr);
        if (path.split("_")[3].equals("0.csv"))
            in.nextLine();
        String line;
        List<String> base64s = new ArrayList<>();
        while (in.hasNextLine()) {
            line = in.nextLine();
            String pic64 = line;
            base64s.add(pic64);
        }
        System.out.println("signature 64 start at " + path);

        Set<String> set;
        String cachePath = "data" + File.separator + "cache.txt";
        File file = new File(cachePath);
        if (file.exists()) {
            set = new HashSet<>(TextUtils.loadList(cachePath));
        } else {
            set = new HashSet<>();
        }
        // bucketing
        List<Triple<String, String,Integer>[]> base64sBuckets = new ArrayList<>();
        Triple<String, String,Integer>[] tempBucket = new Triple[ThreadNum];
        for(int i=0;i<base64s.size();i++){

            /////////////////
            // classifying
            //////////////////
            String classifyRes = null;
            String cate = null;
            MultiValueMap<String, String> requestEntity = new LinkedMultiValueMap<String, String>();
            requestEntity.set("imagestr", base64s.get(i));
//            String url1 = "http://172.27.9.145:5009/extract_upload";
            String url1 = "http://172.27.2.99:5009/extract_upload";
            try {
                classifyRes = restTemplate1.postForObject(url1, requestEntity, String.class);
            } catch (Exception e) {
                requestEntity.get("imagestr");
            }
            JSONObject jso = JSONObject.parseObject(classifyRes);
            //signature write
            if (jso != null) {
                if (jso.containsKey("category")) {
                    cate = jso.get("category").toString();
                } else {
                    cate = "null";
                }
            } else {
                cate = "null";
            }
            ///////////////////
            tempBucket[i % ThreadNum] = new Triple<>(cate, base64s.get(i),i);
            if (i != 0 && i % ThreadNum == ThreadNum - 1) {
                base64sBuckets.add(tempBucket.clone());
                tempBucket = new Triple[ThreadNum];
            }
        }


        PrintWriter pw = new PrintWriter(path.split("_")[3].toString());
        for (Triple[] base64sBucket : base64sBuckets) {
//            String url = "http://172.27.2.100:8081/getSignatureBatch";
            String url = "http://172.27.2.99:8083/getSignatureBatch";
            List<Triple> pairs = new ArrayList<>();
            for (Triple pair : base64sBucket) {
                pairs.add(pair);
            }
            List sigs = restTemplate2.postForObject(url, pairs, List.class, "test");
            for (Object sigl : sigs) {

                String sigo = (String)sigl;
                String sig = sigo.split("\\|")[0];
                String oid = sigo.split("\\|")[1];
                String clasz = sigo.split("\\|")[2];
                boolean dup = false;
                try {
                    if (sig != null)
                        if (!sig.equals("") && !sig.equals("null")) {
                            if (!set.contains(sig)) {
                                set.add(sig);
                            } else {
                                dup = true;
                            }
                        }
                } catch (Exception e) {
                    System.err.println("---" + sig);
                }
                pw.println(oid + "," + path.split("_")[3].replaceAll(".csv", "") + "," + clasz + "," + sig + "," + dup);
            }
        }
        pw.close();



//
//        for(String base64:base64s){
//            boolean dup = false;
//            String res = null;
//
//            //signature write
//            if (jso != null) {
//                if (jso.containsKey("category")) {
//                    if (jso.get("category").toString().startsWith("class2")) {
//                        byte[] pic = ImageUtils.base64String2ByteFun(res);
////                    String url = "http://172.27.2.141:8081/getSignature";
//                        String url = "http://172.27.2.100:8081/getSignature";
//                        String sig = restTemplate2.postForObject(url, pic, String.class, "test");
//                        try {
//                            if (sig != null)
//                                if (!sig.equals("") && !sig.equals("null")) {
//                                    if (!set.contains(sig.split(",")[1])) {
//                                        set.add(sig.split(",")[1]);
//                                    } else {
//                                        dup = true;
//                                    }
//                                }
//                        } catch (Exception e) {
//                            System.err.println("---" + sig);
//                        }
//                        pw.println(String.valueOf(counter) + "," + path.split("_")[3].replaceAll(".csv", "") + "," + jso.get("category").toString() + "," + sig + "," + dup);
//                    } else {
//                        pw.println(String.valueOf(counter) + "null");
//
//                    }
//                } else {
//                    pw.println(String.valueOf(counter) + "null");
//                }
//            } else {
//                pw.println(String.valueOf(counter) + "null");
//            }
//            counter++;
//        }

        TextUtils.writeList(new ArrayList<>(set), cachePath);
        System.out.println("signature 64 completed at " + path);
        return 0;
    }


    @RequestMapping("/callServiceGetSignatureBatch64")
    void pingGetSignatureBatch64() throws FileNotFoundException {
        List<File> fileList = Arrays.asList(new File("base64" + File.separator).listFiles());

        for (File file : fileList) {
            try {
                restfulGetSignatureTestBatch64(file.getAbsolutePath());
//            restfulGetSignatureTest64("base64/img_certMD5_base64_1_0.csv");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }



    //restful get
    public double restfulGetSignatureTestBatch264(String path) throws FileNotFoundException {
        RestTemplate restTemplate1 = new RestTemplate();
        RestTemplate restTemplate2 = new RestTemplate();
        FileReader fr = new FileReader(path);
        Scanner in = new Scanner(fr);
        if (path.split("_")[3].equals("0.csv"))
            in.nextLine();
        String line;
        List<String> base64s = new ArrayList<>();
        while (in.hasNextLine()) {
            line = in.nextLine();
            String pic64 = line;
            base64s.add(pic64);
        }
        System.out.println("signature 64 start at " + path);

        Set<String> set;
        String cachePath = "data" + File.separator + "cache.txt";
        File file = new File(cachePath);
        if (file.exists()) {
            set = new HashSet<>(TextUtils.loadList(cachePath));
        } else {
            set = new HashSet<>();
        }
        // bucketing
        List<Triple<String, String,Integer>[]> base64sBuckets = new ArrayList<>();
        Triple<String, String,Integer>[] tempBucket = new Triple[ThreadNum];
        for(int i=0;i<base64s.size();i++){

            /////////////////
            // classifying
            //////////////////
            String classifyRes = null;
            String cate = null;
            MultiValueMap<String, String> requestEntity = new LinkedMultiValueMap<String, String>();
            requestEntity.set("imagestr", base64s.get(i));
//            String url1 = "http://172.27.9.145:5009/extract_upload";
            String url1 = "http://172.27.2.99:5009/extract_upload";
            try {
                classifyRes = restTemplate1.postForObject(url1, requestEntity, String.class);
            } catch (Exception e) {
                requestEntity.get("imagestr");
            }
            JSONObject jso = JSONObject.parseObject(classifyRes);
            //signature write
            if (jso != null) {
                if (jso.containsKey("category")) {
                    cate = jso.get("category").toString();
                } else {
                    cate = "null";
                }
            } else {
                cate = "null";
            }
            ///////////////////
            tempBucket[i % ThreadNum] = new Triple<>(cate, base64s.get(i),i);
            if (i != 0 && i % ThreadNum == ThreadNum - 1) {
                base64sBuckets.add(tempBucket.clone());
                tempBucket = new Triple[ThreadNum];
            }
        }


        PrintWriter pw = new PrintWriter(path.split("_")[3].toString());
        for (Triple[] base64sBucket : base64sBuckets) {
//            String url = "http://172.27.2.100:8081/getSignatureBatch";
            String url = "http://172.27.2.99:8083/getSignatureBatch";
            List<Triple> pairs = new ArrayList<>();
            for (Triple pair : base64sBucket) {
                pairs.add(pair);
            }
            List sigs = restTemplate2.postForObject(url, pairs, List.class, "test");
            for (Object sigl : sigs) {

                String sigo = (String)sigl;
                String sig = sigo.split("\\|")[0];
                String oid = sigo.split("\\|")[1];
                String clasz = sigo.split("\\|")[2];
                boolean dup = false;
                try {
                    if (sig != null)
                        if (!sig.equals("") && !sig.equals("null")) {
                            if (!set.contains(sig)) {
                                set.add(sig);
                            } else {
                                dup = true;
                            }
                        }
                } catch (Exception e) {
                    System.err.println("---" + sig);
                }
                pw.println(oid + "," + path.split("_")[3].replaceAll(".csv", "") + "," + clasz + "," + sig + "," + dup);
            }
        }
        pw.close();



//
//        for(String base64:base64s){
//            boolean dup = false;
//            String res = null;
//
//            //signature write
//            if (jso != null) {
//                if (jso.containsKey("category")) {
//                    if (jso.get("category").toString().startsWith("class2")) {
//                        byte[] pic = ImageUtils.base64String2ByteFun(res);
////                    String url = "http://172.27.2.141:8081/getSignature";
//                        String url = "http://172.27.2.100:8081/getSignature";
//                        String sig = restTemplate2.postForObject(url, pic, String.class, "test");
//                        try {
//                            if (sig != null)
//                                if (!sig.equals("") && !sig.equals("null")) {
//                                    if (!set.contains(sig.split(",")[1])) {
//                                        set.add(sig.split(",")[1]);
//                                    } else {
//                                        dup = true;
//                                    }
//                                }
//                        } catch (Exception e) {
//                            System.err.println("---" + sig);
//                        }
//                        pw.println(String.valueOf(counter) + "," + path.split("_")[3].replaceAll(".csv", "") + "," + jso.get("category").toString() + "," + sig + "," + dup);
//                    } else {
//                        pw.println(String.valueOf(counter) + "null");
//
//                    }
//                } else {
//                    pw.println(String.valueOf(counter) + "null");
//                }
//            } else {
//                pw.println(String.valueOf(counter) + "null");
//            }
//            counter++;
//        }

        TextUtils.writeList(new ArrayList<>(set), cachePath);
        System.out.println("signature 64 completed at " + path);
        return 0;
    }

    @RequestMapping("/DuplicatedPermitSearch/{month}/{channel}")
    public String DPSR(@PathVariable("month") int month,@PathVariable("channel") String channel) {
        System.out.println("month=" + month);
        System.out.println("channel=" + channel);
        JSONObject res = new JSONObject();
        String flag = "0";

        if("wm".equals(channel)) {
            duplicatedPermitSearchRemoteWm.init(month);
            try {
                duplicatedPermitSearchRemoteWm.start();
            } catch (Exception e) {
                res.put("err", "1");
                e.printStackTrace();
                flag = "1";
            }
        }
        else if("tg".equals(channel)){
            duplicatedPermitSearchRemoteTg.init(month);
            try {
                res = duplicatedPermitSearchRemoteTg.dpsr();
            } catch (Exception e) {
                res.put("err", "1");
                e.printStackTrace();
                flag = "1";
            }

        }
        return flag;
    }
}