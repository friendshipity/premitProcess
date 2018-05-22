package com.szl.syj.core;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator odn 2018/3/14.
 */
public class regTest {


    public static void main(String[] args) {
        String str = "许可证经营者名称:上海领御餐饮管理有限公司成都六许可证编号";
//        String str1 = "食品经营";
//        String regEx41 = "([\\s\\S]*)食品.{1,4}营([\\s\\S]*)";
//        String regEx51 = "餐饮.务";
//        String regEx61 = "餐饮服.";
//        Pattern pattern = Pattern.compile(regEx41);
//        Matcher matcher = pattern.matcher(str);
//        System.out.println(matcher.matches());


        List<String> elements = new ArrayList<>();
        String regEx = "([\\s\\S]*)经.者名称.{10}";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            elements.add(matcher.group());
        }
        System.currentTimeMillis();

    }
}
