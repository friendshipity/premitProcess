package com.szl.syj;

import com.szl.syj.utils.TextUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2018/5/15.
 */
public class MergeRes {
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
        TextUtils.writeList(csvs,"mergeResults1.csv");
    }
}
