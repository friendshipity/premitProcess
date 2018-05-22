package com.szl.syj.core;

import com.baidu.aip.ocr.AipOcr;
import com.google.gson.*;
import com.szl.syj.utils.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2018/3/13.
 */
@Component
public class PermitClassification {
    @Autowired

    private static final double _r = 0.5;
    private int mainNameFalseNum = 0;
    private static final String type1 = "食品经营许可证";
    private static final String type2 = "餐饮服务许可证";
    //4
    public static final String APP_ID = "10917859";
    public static final String API_KEY = "HpFRWT59o4e2znSm39nli8S7";
    public static final String SECRET_KEY = "j1OcFUVOeLFzrqPIRrF7ieOSCcXkuUNj";

    public static String mainWordsExtract(String str) {
        //p1
        if (str.contains(type1))
            return type1;
        if (str.contains(type2))
            return type2;

        return null;
    }

    public static int regExs(String str) {

        List<String> regs = new ArrayList<>();
        List<String> regs1 = new ArrayList<>();
        String regEx1 = "食品经营";
        String regEx11 = "([\\s\\S]*)食.{1,4}经营许([\\s\\S]*)";
        String regEx11_ = "([\\s\\S]*)食.{1,4}经营{0,2}可([\\s\\S]*)";

        String regEx21 = "([\\s\\S]*)食品.{1,4}营许([\\s\\S]*)";
        String regEx21_ = "([\\s\\S]*)食品.{1,4}营{0,2}可([\\s\\S]*)";

        String regEx31 = "([\\s\\S]*)食品经.{1,4}许([\\s\\S]*)";
        String regEx31_ = "([\\s\\S]*)食品经.{1,4}{0,2}可([\\s\\S]*)";

        String regEx321 = "([\\s\\S]*).{1,4}品经营许([\\s\\S]*)";
        String regEx321_ = "([\\s\\S]*).{1,4}品经营{0,2}可([\\s\\S]*)";

        String regEx3211 = "([\\s\\S]*)食.{1,2}经.{1,2}许可([\\s\\S]*)";
        String regEx3212 = "([\\s\\S]*).{1,2}品经.{1,2}许可([\\s\\S]*)";
        String regEx3213 = "([\\s\\S]*)食.{1,4}营许可([\\s\\S]*)";
        String regEx3214 = "([\\s\\S]*).{1,2}品.{1,2}营许可([\\s\\S]*)";

        String regEx111 = "食经营";
        String regEx211 = "食品营";
        String regEx311 = "食品经";
        String regEx811 = "品经营";


        String regEx4 = "餐饮服务";


        String regEx41 = "([\\s\\S]*)餐.{1,4}服务许([\\s\\S]*)";
        String regEx41_ = "([\\s\\S]*)餐.{1,4}服务{0,2}可([\\s\\S]*)";

        String regEx51 = "([\\s\\S]*)餐饮.{1,4}务许([\\s\\S]*)";
        String regEx51_ = "([\\s\\S]*)餐饮.{1,4}务{0,2}可([\\s\\S]*)";

        String regEx61 = "([\\s\\S]*)餐饮服.{1,4}许([\\s\\S]*)";
        String regEx61_ = "([\\s\\S]*)餐饮服.{1,4}{0,2}可([\\s\\S]*)";

        String regEx654 = "([\\s\\S]*).{1,4}饮服务许([\\s\\S]*)";
        String regEx654_ = "([\\s\\S]*).{1,4}饮服务{0,2}可([\\s\\S]*)";

        String regEx4211 = "([\\s\\S]*)餐.{1,2}服.{1,2}许可([\\s\\S]*)";
        String regEx4212 = "([\\s\\S]*).{1,2}饮服.{1,2}许可([\\s\\S]*)";
        String regEx4213 = "([\\s\\S]*)餐.{2,4}务许可([\\s\\S]*)";
        String regEx4214 = "([\\s\\S]*).{1,2}饮.{1,2}务许可([\\s\\S]*)";


        String regEx411 = "餐服务";
        String regEx511 = "餐饮务";
        String regEx611 = "餐饮服";
        String regEx711 = "饮服务";
//        regs.add(regEx1);
        regs.add(regEx11);
        regs.add(regEx21);
        regs.add(regEx31);
        regs.add(regEx321);
        regs.add(regEx11_);
        regs.add(regEx21_);
        regs.add(regEx31_);
        regs.add(regEx321_);
        regs.add(regEx3211);
        regs.add(regEx3212);
        regs.add(regEx3213);
        regs.add(regEx3214);
//        regs.add(regEx111);
//        regs.add(regEx211);
//        regs.add(regEx311);
//        regs.add(regEx811);
//
//
//
//        regs1.add(regEx4);
        regs1.add(regEx41);
        regs1.add(regEx51);
        regs1.add(regEx61);
        regs1.add(regEx654);
        regs1.add(regEx41_);
        regs1.add(regEx51_);
        regs1.add(regEx61_);
        regs1.add(regEx654_);
        regs1.add(regEx4211);
        regs1.add(regEx4212);
        regs1.add(regEx4213);
        regs1.add(regEx4214);
//        regs1.add(regEx411);
//        regs1.add(regEx511);
//        regs1.add(regEx611);
//        regs1.add(regEx711);


        int flag = 0;
        if (!str.contains("食品流通") && !str.contains("灵活就业"))
            for (String reg : regs) {
                Pattern pattern = Pattern.compile(reg);
                Matcher matcher = pattern.matcher(str);
                if (matcher.matches()) {
                    flag = 1;
                    return flag;
                }
            }
//        if (!str.contains("食品流通") && !str.contains("灵活就业")){
//            if (str.contains(regEx1) ||
//                    str.contains(regEx111) ||
//                    str.contains(regEx211) ||
//                    str.contains(regEx311) ||
//                    str.contains(regEx811)) {
//                if(!str.contains("食品经营部")) {
//                    flag = 1;
//                    return flag;
//                }
//            }
//        }

        if (!str.contains("食品流通") && !str.contains("灵活就业")){

            for (String reg : regs1) {
                Pattern pattern = Pattern.compile(reg);
                Matcher matcher = pattern.matcher(str);
                if (matcher.matches()) {
//                    if (!str.contains("餐饮服务者") && !str.contains("餐饮服务经营者")&& !str.contains("食品经营部")) {
                    if (!str.contains("餐饮服务者") && !str.contains("餐饮服务经营者")) {
                        flag = 2;
                        return flag;
                    }
                }
            }
        }

//        if (!str.contains("食品流通") && !str.contains("灵活就业")){
//            if (str.contains(regEx4) ||
//                    str.contains(regEx411) ||
//                    str.contains(regEx511) ||
//                    str.contains(regEx611) ||
//                    str.contains(regEx711)) {
//                if (!str.contains("餐饮服务者") && !str.contains("餐饮服务经营者") && !str.contains("食品经营部")) {
//                    flag = 2;
//                    return flag;
//                }
//            }
//        }


        return flag;
    }

    public static int policy(String str) {

        String word = mainWordsExtract(str);
        int flag = 0;


        if (word == null) {
            flag = regExs(str);
//            flag = 5;
        } else {
            if (word.equals(type1))
                flag = 1;
            if (word.equals(type2))
                flag = 2;
        }
        return flag;
    }


    public static JSONArray basicGeneral(List<byte[]> imagesbinaries, AipOcr client) {
        List<String> reses = new ArrayList<>();
        JSONArray array = new JSONArray();
        for (byte[] imagebinary : imagesbinaries) {
            HashMap<String, String> options = new HashMap<String, String>();
            options.put("language_type", "CHN_ENG");
            options.put("detect_direction", "true");
            options.put("probability", "false");


            JSONObject res = client.basicAccurateGeneral(imagebinary, options);
//            System.out.println(res.toString(2));
//            reses.add(res.toString(2));
            array.put(res);
        }
        return array;
    }


    public int permitClassify(String jsonString) {
        int flag = 0;
        JsonParser parser = new JsonParser();
        JsonElement jsonObject = new JsonObject();
        try {
            jsonObject = (JsonElement) parser.parse(jsonString);
        } catch (JsonIOException e) {
            e.printStackTrace();
        }

        JsonArray wordsJsa = new JsonArray();
        String words = "";
        try {
            wordsJsa = jsonObject.getAsJsonObject().get("words_result").getAsJsonArray();
        } catch (Exception e) {
        }
        // words find
        int size = wordsJsa.size();
        int index = 0;
        int jumpOutIndex = (int) (size * _r);
        for (JsonElement jse : wordsJsa) {
            words = words + jse.getAsJsonObject().get("words").toString().replaceAll("\"", "");
            if (++index > jumpOutIndex)
                break;
        }
        flag = policy(words);
        return flag;

    }


    public void permitClassify(String loadPath, String JsonPath, String writePath) {
        JsonParser parser = new JsonParser();

        JsonArray array = new JsonArray();
        try {
            array = (JsonArray) parser.parse(new FileReader(JsonPath));
        } catch (JsonIOException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        String path = loadPath;

        List<File> fileList = Arrays.asList(new File(path).listFiles());

        int sizeA = array.size();
        int indexA = 0;
        int indexB = 0;
        List<File> falseNameList = new ArrayList<>();
//        for (JsonElement jso : array) {
        for (JsonElement jso : array) {

            JsonArray wordsJsa = new JsonArray();
            String words = "";
            try {
                wordsJsa = jso.getAsJsonObject().get("words_result").getAsJsonArray();
            } catch (Exception e) {
            }
            // words find
            int size = wordsJsa.size();
            int index = 0;
            int jumpOutIndex = (int) (size * _r);
            for (JsonElement jse : wordsJsa) {
                words = words + jse.getAsJsonObject().get("words").toString().replaceAll("\"", "");
                if (++index > jumpOutIndex)
                    break;
            }
            int flag = policy(words);
            if (flag != 0) {
                indexA++;
            }
            if (flag == 0) {
                falseNameList.add(fileList.get(indexB));
            }
            indexB++;
        }


        System.out.println(sizeA - indexA);
        FileUtils.writePics(falseNameList, writePath);


    }

    public static void main(String[] args) {

        /*
        //load text
//        JSONArray jsonArray = (JsonContext);
        JsonParser parser = new JsonParser();

        JsonArray array = new JsonArray();
        try {
            array = (JsonArray) parser.parse(new FileReader("OCRJsonAntiYellowLight.json"));
        } catch (JsonIOException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }





//        List<byte[]> imagesbinaries = FileUtils.readFilsAsByte(path);
//        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
//        client.setConnectionTimeoutInMillis(2000);
//        client.setSocketTimeoutInMillis(60000);
//        System.setProperty("aip.log4j.conf", "conf/log4j.properties");
//        JSONArray res = basicGeneral(imagesbinaries, client);
////        int index =0;
//        List<String> ress = new ArrayList<>();
//        for(Object re:res){
//            ress.add(((JSONObject)re).toString(2));
//        }


//        String path = "pics/";
        String path = "p_anti_yellow1/";

        List<File> fileList = Arrays.asList(new File(path).listFiles());

        int sizeA = array.size();

        int indexA = 0;
        int indexB = 0;
        List<File> falseNameList = new ArrayList<>();
//        for (JsonElement jso : array) {
        for (JsonElement jso : array) {

            JsonArray wordsJsa = new JsonArray();
            String words = "";
            try {
                wordsJsa = jso.getAsJsonObject().get("words_result").getAsJsonArray();
            } catch (Exception e) {
            }
            // words find
            int size = wordsJsa.size();
            int index = 0;
            int jumpOutIndex = (int) (size * _r);
            for (JsonElement jse : wordsJsa) {
                words = words + jse.getAsJsonObject().get("words").toString().replaceAll("\"", "");
                if (++index > jumpOutIndex)
                    break;
            }
            int flag = policy(words);
            if (flag != 0) {
                indexA++;
            }
//            if (flag==5) {
            if (flag == 0) {
                falseNameList.add(fileList.get(indexB));
            }
            indexB++;
            System.currentTimeMillis();


        }
        System.out.println(sizeA - indexA);
        FileUtils.writePics(falseNameList,"p_falsePics3/");
*/
    }


}
