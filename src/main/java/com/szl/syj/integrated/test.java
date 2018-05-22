package com.szl.syj.integrated;

import com.baidu.aip.ocr.AipOcr;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/3/12.
 */
public class test {
    //设置APPID/AK/SK1
//    public static final String APP_ID = "10912333";
//    public static final String API_KEY = "7R8UIulfb4zqOgbaru6AuX9u";
//    public static final String SECRET_KEY = "G4D7OVRCqZhAK3SKEz9v2W33wCkZ1zuo";


    //2

//    public static final String APP_ID = "10902575";
//    public static final String API_KEY = "8Es3zOCQN5MLWZhzDr4ylYM3";
//    public static final String SECRET_KEY = "rnGgrSrU0C7Pr4MWmQlshm3sgXvFFfHS";

    //3
//    public static final String APP_ID = "10359432";
//    public static final String API_KEY = "5ErnUFTuXf6SEXKFArTKGLBu";
//    public static final String SECRET_KEY = "nCiq6BrRhW0Ayab271Dlx6djXlaLcMS4";

    //4
    public static final String APP_ID = "10917859";
    public static final String API_KEY = "HpFRWT59o4e2znSm39nli8S7";
    public static final String SECRET_KEY = "j1OcFUVOeLFzrqPIRrF7ieOSCcXkuUNj";

    public static void highreso(List<byte[]> imagesbinaries, AipOcr client) {
        List<String> reses = new ArrayList<>();

        for (byte[] imagebinary : imagesbinaries) {
            HashMap<String, String> options = new HashMap<String, String>();
            options.put("detect_direction", "true");
            options.put("probability", "true");


            JSONObject res = client.basicAccurateGeneral(imagebinary, options);
            System.out.println(res.toString(2));
            reses.add(res.toString(2));
        }

    }


    public static JSONArray basicGeneral(List<byte[]> imagesbinaries, AipOcr client) {
        List<String> reses = new ArrayList<>();
        JSONArray array = new JSONArray();
        for (byte[] imagebinary : imagesbinaries) {
            HashMap<String, String> options = new HashMap<String, String>();
            options.put("language_type", "CHN_ENG");
            options.put("detect_direction", "true");
            options.put("probability", "false");


            JSONObject res = client.basicGeneral(imagebinary, options);
//            System.out.println(res.toString(2));
//            reses.add(res.toString(2));
            array.put(res);
        }
        return array;
    }

    public static JSONArray basicGeneral2(List<String> images, AipOcr client) {
        List<String> reses = new ArrayList<>();
        JSONArray array = new JSONArray();
        for (String image : images) {
            HashMap<String, String> options = new HashMap<String, String>();
            options.put("language_type", "CHN_ENG");
            options.put("detect_direction", "true");
            options.put("probability", "false");


            JSONObject res = client.basicAccurateGeneral(image, options);
//            System.out.println(res.toString(2));
//            reses.add(res.toString(2));
            array.put(res);
        }
        return array;
    }




    public static void main(String[] args) {

//
////        String path = "p_falsePics2/";
////        String path = "p_rightPics1/";
////        String path = "pics/";
////        String loadPath = "p_anti_yellow2/";
////        String writePath = "p_falsePics3/";
////        String loadPath = "pics/";
////        String writePath = "p_falsePics4/";
//        System.setProperty("aip.log4j.conf", "conf/log4j.properties");
////        String falsePicsPath = "p_falsePics4/";
//        String falsePicsPath = "p_falsePics/";
//        String loadPath = "p_anti_yellow3/";
//        String writePath = "p_falsePics5/";
//        String jsonPath = "OCRJsonAntiYellowLightTemp.json";
////        String jsonPath = "OCRJSONF.json";
//        //clear
//        TextUtils.deleteUnderDir(loadPath);
//        ColorAntiYellow colorAntiYellow = new ColorAntiYellow();
//        colorAntiYellow.antiYellow(falsePicsPath,loadPath);
//
//        TextUtils.deleteUnderDir(writePath);
//
//
//
//
//
//        List<byte[]> imagesbinaries = FileUtils.readFilsAsByte(loadPath);
//
//
//        //string
//        List<File> fileList = Arrays.asList(new File(loadPath).listFiles());
////        List<String> filePathList = new ArrayList<>();
////        for(File file:fileList){
////            filePathList.add(file.getAbsolutePath());
////        }
//
//
//
//        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
//
//        client.setConnectionTimeoutInMillis(2000);
//        client.setSocketTimeoutInMillis(60000);
//
//
//        JSONArray res = basicGeneral(imagesbinaries, client);
//        int index =0;
//        List<String> ress = new ArrayList<>();
//        for(Object re:res){
//            ((JSONObject)re).put("fileName",fileList.get(index++).getName());
//            ress.add(((JSONObject)re).toString(2));
//        }
//
//        try {
//            File file = new File(jsonPath);
//            if (file.exists()) { // 如果已存在,删除旧文件
//                file.delete();
//            }
//            file.createNewFile();
//            Writer write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
//            write.write(res.toString());
//            write.flush();
//            write.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        PermitClassification permitClassification = new PermitClassification();
//        permitClassification.permitClassify(loadPath,jsonPath, writePath);
//
//
//        System.out.println("over");
    }



}
