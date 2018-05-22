package com.szl.syj;

import com.szl.syj.dict.HashMake;
import com.szl.syj.utils.TextUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/5/16.
 */
public class IndexPicMerge {
    public static void main(String[] args){
        List<File> fileList = Arrays.asList(new File("D://data//res2//").listFiles());
        List<String> csvs = new ArrayList<>();
        for (File file : fileList) {
            try {
                csvs.addAll(TextUtils.loadList(file.getAbsolutePath()));
//            restfulGetSignatureTest64("base64/img_certMD5_base64_1_0.csv");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        HashMap<String,String> rawId2Sig = new HashMap<>();

        for(String csv:csvs){
            if(csv.contains("null")&&csv.contains("class")){
                rawId2Sig.put(csv.split("\\,")[0]+","+csv.split("\\,")[1],csv.split("\\,")[3]);
            }
        }

        System.currentTimeMillis();
    }
}
