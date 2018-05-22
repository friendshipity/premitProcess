package com.szl.syj.integrated;

import com.baidu.aip.ocr.AipOcr;

import com.szl.syj.LSH.SimHash;
import com.szl.syj.core.InfoDens;
import com.szl.syj.image.ColorAntiYellow;
import com.szl.syj.core.PermitClassification;
import com.szl.syj.core.PermitFake;
import com.szl.syj.utils.ImageUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/3/12.
 */
@Component
public class SinglePicProcess {


    @Autowired
    private PermitFake permitFake;
    @Autowired
    private PermitClassification permitClassification;
    @Autowired
    private InfoDens infoDens;


    public static final String APP_ID = "10917859";
    public static final String API_KEY = "HpFRWT59o4e2znSm39nli8S7";
    public static final String SECRET_KEY = "j1OcFUVOeLFzrqPIRrF7ieOSCcXkuUNj";




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

    public static JSONObject basicGeneral(byte[] imagesbinaries, AipOcr client) {
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("language_type", "CHN_ENG");
        options.put("detect_direction", "true");
        options.put("probability", "false");
        JSONObject res = client.basicGeneral(imagesbinaries, options);
        return res;
    }


    public static JSONObject basicGeneral(String base64, AipOcr client) {
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("language_type", "CHN_ENG");
        options.put("detect_direction", "true");
        options.put("probability", "false");
        JSONObject res = client.basicGeneral(base64, options);
        return res;
    }


    public int RuleOCR(String  image){
        byte[] imageBinaries = ImageUtils.getImageBinary(image);
        return RuleOCR(imageBinaries);

    }
    public int RuleOCR(byte[] imageBinary){// permit classification and conditionally image processing (anti-yellow)

        System.setProperty("aip.log4j.conf", "conf/log4j.properties");
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
//        String image = "test.jpg";
//        String image = "test2.jpeg";


//        ColorAntiYellow ColorAntiYellow = new ColorAntiYellow();

        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        JSONObject res = basicGeneral(imageBinary, client);

        int flag = permitClassification.permitClassify(res.toString(2));

        //anti-yellow
        if(flag == 0) {
            byte[] processedIB = null;
            try {
                processedIB = ColorAntiYellow.antiYellowSingleV1(imageBinary);
            } catch (Exception e) {
                e.printStackTrace();
            }



            res = basicGeneral(processedIB, client);
            flag = permitClassification.permitClassify(res.toString(2));


            //////////////////test anti-yellow func results////
//            try {
//                ImageIO.write(ImageUtils.bytes2ImageBuffer(processedIB), "jpg", new File("p_err2_antiYellow/"+ flag+"_"+UUID.randomUUID()+".jpg"));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            //////////////////
        }
//        System.out.println(flag);
        return flag;
    }


    //d permit  detect batch
//    public String RuleOCR6(String[] base64){
//
//    }

    public String RuleOCR6_over(String res){
        List<String> ress = null;
        try{
            ress=permitFake.wordsDetect(res);
        }catch (Exception e){

        }
        if (ress!=null) {
            permitFake.signature(ress);
            String signautre = permitFake.getSignature();
            return signautre;
        }
        else
            return null;
    }



    //d permit  detect
    public String RuleOCR4(byte[] imageBinary){
        System.setProperty("aip.log4j.conf", "conf/log4j.properties");
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        JSONObject res = basicGeneral(imageBinary, client);
        List<String> ress = permitFake.wordsDetect(res.toString(2));
        permitFake.signature(ress);
        String signautre = permitFake.getSignature();
        if(!signautre.equals("")) {
            SimHash simHash = new SimHash(SimHash.Token_CN(signautre), 64);
            return String.valueOf(simHash.getIntSimHash())+","+signautre;
        }
        else{
            return "";
        }
    }





    //permit detect
    public String RuleOCR5(byte[] imageBinary){
        System.setProperty("aip.log4j.conf", "conf/log4j.properties");
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        JSONObject res = new JSONObject();
        String ress = "";
        try {
            res = basicGeneral(imageBinary, client);
         ress = permitFake.rawString(res.toString(2));
        }catch (Exception e){}
        String signautre = "";
        if(!ress.equals("")) {
            SimHash simHash = new SimHash(SimHash.Token_CN(ress), 64);
            return String.valueOf(simHash.getIntSimHash());
        }
        else{
            return "";
        }
    }

    //fake detect
    public boolean RuleOCR2(byte[] imageBinary){// fake permit
        System.setProperty("aip.log4j.conf", "conf/log4j.properties");
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        JSONObject res = basicGeneral(imageBinary, client);

        List<String> ress = permitFake.wordsDetect(res.toString(2));

        return permitFake.fakeDetect(ress);


    }

    //density detect
    public double RuleOCR3(byte[] imageBinary){// fake permit
        System.setProperty("aip.log4j.conf", "conf/log4j.properties");
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        JSONObject res = basicGeneral(imageBinary, client);
        return infoDens.densityValue(res.toString(2));
    }



    public static void main(String[] args) {
//
//        System.setProperty("aip.log4j.conf", "conf/log4j.properties");
//
//        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
////        String image = "test.jpg";
//        String image = "test2.jpeg";
////        ColorAntiYellow ColorAntiYellow = new ColorAntiYellow();
//
//        client.setConnectionTimeoutInMillis(2000);
//        client.setSocketTimeoutInMillis(60000);
//        JSONObject res = basicGeneral(ImageUtils.getImageBinary(image), client);
//        PermitClassification permitClassification = new PermitClassification();
//        int flag = permitClassification.permitClassify(res.toString(2));
//
//        System.out.println("123");
//        //anti-yellow
//        if(flag == 0) {
//            byte[] processedIB = null;
//            try {
//                processedIB = ColorAntiYellow.antiYellowSingle(image);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            res = basicGeneral(processedIB, client);
//            flag = permitClassification.permitClassify(res.toString(2));
//        }
//        System.out.println(flag);
    }


}
