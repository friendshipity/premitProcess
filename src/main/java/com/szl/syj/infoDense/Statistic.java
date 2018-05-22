package com.szl.syj.infoDense;

import com.szl.syj.utils.TextUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2018/4/11.
 */
public class Statistic {
    public static void main(String[] args){
        String path = "p_fuzzy6";
        List<File> fileList = Arrays.asList(new File(path).listFiles());
        List<String> densities = new ArrayList<>();

        for(File file:fileList){
            densities.add(file.getName().split("__")[0]);
        }
        TextUtils.writeList(densities,"py/densities1.txt");
    }
}
