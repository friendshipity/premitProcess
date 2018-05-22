package com.szl.syj.core;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Multimap;
import com.google.common.collect.Table;
import com.google.gson.*;
import com.szl.syj.dict.DictProcess;
import com.szl.syj.dict.HashMake;
import com.szl.syj.utils.CompareUtils;
import com.szl.syj.utils.Similarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2018/3/19.
 */
@Component
public class PermitFake {


    private List<String> regs = new ArrayList<>();
    private List<String> regs1 = new ArrayList<>();
    private List<String> regsFake1 = new ArrayList<>();
    private List<String> regsFake2 = new ArrayList<>();
    private List<String> regsFake3 = new ArrayList<>();
    private List<String> regsFake4 = new ArrayList<>();
    private List<String> patterns = new ArrayList<>();


    private final String fixPattern1 = "经营者名称";
    private final String fixPattern2 = "许可证编号";
    private final String fixPattern3 = "社会信用代码";
    private final String fixPattern4 = "日常监督管理机构";
    private final String fixPattern5 = "法定代表人";
    private final String fixPattern6 = "日常监督管理人员";
    private final String fixPattern7 = "住所";
    private final String fixPattern8 = "投诉举报电话";
    private final String fixPattern9 = "经营场所";
    private final String fixPattern10 = "发证机关";
    private final String fixPattern11 = "主题业态";
    private final String fixPattern12 = "经营项目";
    private final String fixPattern13 = "签发人";


    private final String regExFake11 = "([\\s\\S]*)经.{0,3}者名称([\\s\\S]*)";
    private final String regExFake12 = "([\\s\\S]*)经营.{0,3}名称([\\s\\S]*)";
    private final String regExFake13 = "([\\s\\S]*)营.{0,3}名称([\\s\\S]*)";


    private final String regExFake21 = "([\\s\\S]*)许可证.{0,3}号([\\s\\S]*)";
    private final String regExFake22 = "([\\s\\S]*)可证.{0,3}号([\\s\\S]*)";


    private final String regExFake31 = "([\\s\\S]*).{1,2}代表人\\(负责人\\)([\\s\\S]*)";
    private final String regExFake34 = "([\\s\\S]*).{1,2}代表人\\(.{0,1}责人\\)([\\s\\S]*)";
    private final String regExFake33 = "([\\s\\S]*).{1,2}代表人\\(负.{0,1}人\\)([\\s\\S]*)";
    private final String regExFake32 = "([\\s\\S]*).{1,2}代表人.{0,3}人.{0,1}([\\s\\S]*)";


    private final String regExFake41 = "([\\s\\S]*).{0,2}营项目([\\s\\S]*)";
    private final String regExFake42 = "([\\s\\S]*)经.{0,2}项目([\\s\\S]*)";
    private final String regExFake43 = "([\\s\\S]*).{1,3}项目([\\s\\S]*)";
    private final String regExFake44 = "([\\s\\S]*)。{0,2}营项。{1}([\\s\\S]*)";
    private final String regExFake45 = "([\\s\\S]*)经.{0,3}目([\\s\\S]*)";
    private final String regExFake46 = "([\\s\\S]*)经.{0,2}项.{1}([\\s\\S]*)";
    private final String regExFake47 = "([\\s\\S]*)营.{0,3}目([\\s\\S]*)";


    private final String regExFake51 = "([\\s\\S]*)主体业态([\\s\\S]*)";
    private final String regExFake52 = "([\\s\\S]*)主体业态([\\s\\S]*)";
    private final String regExFake53 = "([\\s\\S]*)主体业态([\\s\\S]*)";
    private final String regExFake54 = "([\\s\\S]*)主体业态([\\s\\S]*)";


    private final String regEx11 = "([\\s\\S]*)食.{1,4}经营许([\\s\\S]*)";
    private final String regEx11_ = "([\\s\\S]*)食.{1,4}经营{0,2}可([\\s\\S]*)";

    private final String regEx21 = "([\\s\\S]*)食品.{1,4}营许([\\s\\S]*)";
    private final String regEx21_ = "([\\s\\S]*)食品.{1,4}营{0,2}可([\\s\\S]*)";

    private final String regEx31 = "([\\s\\S]*)食品经.{1,4}许([\\s\\S]*)";
    private final String regEx31_ = "([\\s\\S]*)食品经.{1,4}{0,2}可([\\s\\S]*)";

    private final String regEx321 = "([\\s\\S]*).{1,4}品经营许([\\s\\S]*)";
    private final String regEx321_ = "([\\s\\S]*).{1,4}品经营{0,2}可([\\s\\S]*)";

    private final String regEx3211 = "([\\s\\S]*)食.{1,2}经.{1,2}许可([\\s\\S]*)";
    private final String regEx3212 = "([\\s\\S]*).{1,2}品经.{1,2}许可([\\s\\S]*)";
    private final String regEx3213 = "([\\s\\S]*)食.{1,4}营许可([\\s\\S]*)";
    private final String regEx3214 = "([\\s\\S]*).{1,2}品.{1,2}营许可([\\s\\S]*)";

    private final String regEx111 = "食经营";
    private final String regEx211 = "食品营";
    private final String regEx311 = "食品经";
    private final String regEx811 = "品经营";


    private final String regEx4 = "餐饮服务";


    private final String regEx41 = "([\\s\\S]*)餐.{1,4}服务许([\\s\\S]*)";
    private final String regEx41_ = "([\\s\\S]*)餐.{1,4}服务{0,2}可([\\s\\S]*)";

    private final String regEx51 = "([\\s\\S]*)餐饮.{1,4}务许([\\s\\S]*)";
    private final String regEx51_ = "([\\s\\S]*)餐饮.{1,4}务{0,2}可([\\s\\S]*)";

    private final String regEx61 = "([\\s\\S]*)餐饮服.{1,4}许([\\s\\S]*)";
    private final String regEx61_ = "([\\s\\S]*)餐饮服.{1,4}{0,2}可([\\s\\S]*)";

    private final String regEx654 = "([\\s\\S]*).{1,4}饮服务许([\\s\\S]*)";
    private final String regEx654_ = "([\\s\\S]*).{1,4}饮服务{0,2}可([\\s\\S]*)";

    private final String regEx4211 = "([\\s\\S]*)餐.{1,2}服.{1,2}许可([\\s\\S]*)";
    private final String regEx4212 = "([\\s\\S]*).{1,2}饮服.{1,2}许可([\\s\\S]*)";
    private final String regEx4213 = "([\\s\\S]*)餐.{2,4}务许可([\\s\\S]*)";
    private final String regEx4214 = "([\\s\\S]*).{1,2}饮.{1,2}务许可([\\s\\S]*)";


    private final String regEx411 = "餐服务";
    private final String regEx511 = "餐饮务";
    private final String regEx611 = "餐饮服";
    private final String regEx711 = "饮服务";

    private final String disAmbig1 = ".*?日";
    private final String disAmbig2 = ".*?日常";
    private final String disAmbig3 = ".*?监";
    private final String disAmbig4 = ".*?日常监";
    private final String disAmbig5 = ".*?5";
    private final String disAmbig6 = ".*?6";
    private final String disAmbig7 = ".*?7";
    private final String disAmbig8 = ".*?8";
    private final String disAmbig9 = ".*?9";
    private final String disAmbig10 = ".*?常监";
    private final String disAmbig11 = ".*?局";
    private final String disAmbig12 = ".*?住";


    private List<String> dicMap;
    private List<String> names;
    private List<String> codes;
    private List<String> persons;
    private List<String> foodTypes;
    private List<String> disAmbigs;
    private Multimap<String, String> person_keys;

    private Table<String, String, String> table;

    private List<String> regF1End;

//        regs.add(regEx1);

    @Autowired
    private DictProcess dictProcess;
    @Autowired
    private HashMake hashMake;

    public void init() {
        disAmbigs = new ArrayList<>();
        disAmbigs.add(disAmbig1);
        disAmbigs.add(disAmbig2);
        disAmbigs.add(disAmbig3);
        disAmbigs.add(disAmbig4);

        disAmbigs.add(disAmbig5);
        disAmbigs.add(disAmbig6);
        disAmbigs.add(disAmbig7);
        disAmbigs.add(disAmbig8);
        disAmbigs.add(disAmbig9);
        disAmbigs.add(disAmbig10);
        disAmbigs.add(disAmbig11);
        disAmbigs.add(disAmbig12);


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

        patterns.add(fixPattern1);
        patterns.add(fixPattern2);
        patterns.add(fixPattern3);
        patterns.add(fixPattern4);
        patterns.add(fixPattern5);
        patterns.add(fixPattern6);
        patterns.add(fixPattern7);
        patterns.add(fixPattern8);
        patterns.add(fixPattern9);
        patterns.add(fixPattern10);
        patterns.add(fixPattern11);
        patterns.add(fixPattern12);
        patterns.add(fixPattern13);


        regsFake1.add(regExFake11);
        regsFake1.add(regExFake12);
        regsFake1.add(regExFake13);

        regsFake2.add(regExFake21);
        regsFake2.add(regExFake22);

        regsFake3.add(regExFake31);
        regsFake3.add(regExFake32);
        regsFake3.add(regExFake33);
        regsFake3.add(regExFake34);


        regsFake4.add(regExFake41);
        regsFake4.add(regExFake42);
        regsFake4.add(regExFake43);
        regsFake4.add(regExFake44);
        regsFake4.add(regExFake45);
        regsFake4.add(regExFake46);
        regsFake4.add(regExFake47);


//        dictProcess.getData();
//        dicMap = dictProcess.dictMap();
        dicMap = hashMake.getNameLastNames();
        names = hashMake.getNames();
        codes = hashMake.getCodes();
        persons = hashMake.getPersons();
//        dicMap = dictProcess.d;
        person_keys = hashMake.getPerson2names();
        table = hashMake.getTable();
//        List<String> regF1Start = hashMake.makeTemplate(new ArrayList<String>(hashMake.getTable().column("foodType").values()), 2, 0);
        foodTypes = hashMake.getFoodTypeLastName();
    }


    public static String regMatch(List<String> regs, String str) {
        String flag = null;
        for (String reg : regs) {
            Pattern pattern = Pattern.compile(reg);
            Matcher matcher = pattern.matcher(str);
            if (matcher.matches()) {
                flag = reg;
                break;
            }

        }
        return flag;
    }

    public List<String> getRegs(String str) {
        List<String> regF1Start = hashMake.makeTemplate(new ArrayList<String>(hashMake.getTable().column(str).values()), 2, 0);
        List<String> regF1End = hashMake.makeTemplate(new ArrayList<String>(hashMake.getTable().column(str).values()), 0, 2);
        List<String> combinations = new ArrayList<>();
        for (String start : regF1Start) {
            for (String end : regF1End) {
                combinations.add(start + ".*?" + end);
            }
        }

        return new ArrayList<>();
    }

    public List<String> regExsFood(String str) {

//        List<String> regf1 = getRegs("foodType");
//        reg1Flag = regMatch(regF1End, str);
//        String res1 = null;
//        List<String> elements1 = new ArrayList<>();
//        if (reg1Flag != null) {
//            try {
////                reg1Flag = reg1Flag.replace("([\\s\\S]*)", "");
//
//                Pattern pattern = Pattern.compile(reg1Flag);
//                Matcher matcher = pattern.matcher(str);
//                if (matcher.find()) {
//                    elements1.add(matcher.group().replaceAll(reg1Flag, ""));
////                        break;
//                }
//
//            } catch (ConcurrentModificationException ce) {
//                ce.printStackTrace();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        if (!elements1.isEmpty()) {
//            if (elements1.get(0).length() < 50)
//                res1 = elements1.get(0);
//            else
//                System.err.println(elements1.get(0));
//        }
//        return new ArrayList<>();


        // reg matches

        String reg4Flag = null;

        reg4Flag = regMatch(regsFake4, str);


        String res4 = null;
        // extraction
        //1
        List<String> elements1 = new ArrayList<>();

        if (reg4Flag != null) {
            try {
                reg4Flag = reg4Flag.replace("([\\s\\S]*)", "");
                for (String lastName : foodTypes) {
                    String regEx = reg4Flag + ".*" + lastName;
                    Pattern pattern = Pattern.compile(regEx);
                    Matcher matcher = pattern.matcher(str);
                    if (matcher.find()) {
                        elements1.add(matcher.group().replaceAll(reg4Flag, ""));
//                        break;
                    }
                }
            } catch (ConcurrentModificationException ce) {
                ce.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (!elements1.isEmpty()) {
            if (elements1.get(0).length() < 50)
                res4 = elements1.get(0);
            else
                System.err.println(elements1.get(0));
        }

        List<String> ress = new ArrayList<>();
        ress.add(res4);
        return ress;
    }

    public List<String> regExs(String str) {

        // System.out.println(sim.wordSimi("abcds","abdsfcgds"));
        double score = 0;

        // reg matches
        boolean candidateFlag = false;
        String reg1Flag = null;
        String reg2Flag = null;
        String reg3Flag = null;
        reg1Flag = regMatch(regsFake1, str);
        reg2Flag = regMatch(regsFake2, str);
        reg3Flag = regMatch(regsFake3, str);

        String res1 = null;
        String res2 = null;
        String res3 = null;
        // extraction
        //1
        List<String> elements1 = new ArrayList<>();
        List<String> elements2 = new ArrayList<>();
        List<String> elements3 = new ArrayList<>();
        if (reg1Flag != null) {
            try {
                reg1Flag = reg1Flag.replace("([\\s\\S]*)", "");
                for (String lastName : dicMap) {

                    String regEx = reg1Flag + ".*" + lastName;
                    Pattern pattern = Pattern.compile(regEx);
                    Matcher matcher = pattern.matcher(str);
                    if (matcher.find()) {
                        elements1.add(matcher.group().replaceAll(reg1Flag, ""));
//                        break;
                    }
                }
            } catch (ConcurrentModificationException ce) {
                ce.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (!elements1.isEmpty()) {
            int size =elements1.size();
            res1 = elements1.get(size-1);
//            if (elements1.get(0).length() < 50)
//                res1 = elements1.get(0);
//            else
//                System.err.println(elements1.get(0));
        }


        if (reg2Flag != null) {

            reg2Flag = reg2Flag.replace("([\\s\\S]*)", "");
            String regEx = reg2Flag + ".{17}";
            Pattern pattern = Pattern.compile(regEx);
            Matcher matcher = pattern.matcher(str);
            if (matcher.find()) {
                elements2.add(matcher.group().replaceAll(reg2Flag, ""));

            }

        }
        if (!elements2.isEmpty()) {
            res2 = elements2.get(0);
        }


        if (reg3Flag != null) {
            reg3Flag = reg3Flag.replace("([\\s\\S]*)", "");
            String regEx = reg3Flag + ".{5}";
            Pattern pattern = Pattern.compile(regEx);
            Matcher matcher = pattern.matcher(str);
            if (matcher.find()) {
                elements3.add(matcher.group().replaceAll(reg3Flag, ""));

            }
        }
        if (!elements3.isEmpty()) {
            res3 = elements3.get(0);
        }

        List<String> ress = new ArrayList<>();
        ress.add(res1);
        ress.add(res2);
        ress.add(res3);
        return ress;
    }

    public List<String> policy(String str) {

        int flag = 0;

        List<String> res = regExs(str);
        List<String> res2 = regExsFood(str);
        res.addAll(res2);
        return res;
    }

    public String cancelCN(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            int k = (int) str.charAt(i);
            if ((k >= 65 && k <= 90) || (k >= 97 && k <= 122) || (k >= 48 && k <= 57)) {
                stringBuilder.append(str.charAt(i));
            }

        }
        return stringBuilder.toString();
    }

    public boolean fakeDetect(List<String> list) {
        return this.method4(list);
    }

    public boolean signature(List<String> list) {
        return this.method5(list);
    }



    public boolean method1(List<String> list) {
        boolean flag = false;
        boolean flag1 = false;
        boolean flag2 = false;
        boolean flag3 = false;


        if (list.get(0) != null) {
            String name_ = list.get(0);
            name_ = name_.replaceAll(":", "");


            Map<String, Double> distances = new HashMap<>();
            for (String name : names) {
                distances.put(name, Similarity.wordSimi(name_, name));
            }
            Map<String, Double> top = CompareUtils.sortByValues(distances, true);
            if (new ArrayList<Double>(top.values()).get(0) > 0.8) {
                flag1 = true;
            }
        }

        if (list.get(1) != null) {
            String code_ = list.get(1);
            code_ = cancelCN(code_);

            Map<String, Double> distances = new HashMap<>();
            for (String code : codes) {
                distances.put(code, Similarity.wordSimi(code_, code));
            }
            Map<String, Double> top = CompareUtils.sortByValues(distances, true);
            if (new ArrayList<Double>(top.values()).get(0) > 0.8) {
                flag2 = true;
            }
        }

        if (list.get(2) != null) {

            String person_ = list.get(2);
            person_ = person_.replaceAll(":", "");
            List<String> elements = new ArrayList<>();
            for (String disAmbig : disAmbigs) {
                String disAmbigWord = disAmbig.replaceAll("\\.\\*\\?", "");
                Pattern pattern = Pattern.compile(disAmbig);
                Matcher matcher = pattern.matcher(person_);
                if (matcher.find()) {
                    elements.add(matcher.group().replaceAll(disAmbigWord, ""));
                    break;
                }
            }
            if (!elements.isEmpty())
                person_ = elements.get(0);

            Map<String, Double> distances = new HashMap<>();
            for (String person : persons) {
                distances.put(person, Similarity.wordSimi(person_, person));
            }
            Map<String, Double> top = CompareUtils.sortByValues(distances, true);
            if (new ArrayList<Double>(top.values()).get(0) > 0.8) {
                System.out.println(new ArrayList<>(top.keySet()).get(0).toString());
                flag3 = true;
            }
        }


        if ((flag1 && flag2) || (flag3 && flag2) || (flag1 && flag3))
            flag = true;


        System.out.println(flag);
        System.out.println("-----------");
        System.out.println(flag1 + "-" + flag2 + "-" + flag3);
        System.out.println(list.get(0) + "---" + list.get(1) + "---" + list.get(2));
        return flag;


    }


    public boolean method2(List<String> list) {
        boolean flag = false;
        boolean flag1 = false;
        boolean flag2 = false;
        boolean flag3 = false;


        String person_key = null;
        if (list.get(2) != null) {
            String person_ = list.get(2);
            person_ = person_.replaceAll(":", "");
            List<String> elements = new ArrayList<>();
            for (String disAmbig : disAmbigs) {
                String disAmbigWord = disAmbig.replaceAll("\\.\\*\\?", "");
                Pattern pattern = Pattern.compile(disAmbig);
                Matcher matcher = pattern.matcher(person_);
                if (matcher.find()) {
                    elements.add(matcher.group().replaceAll(disAmbigWord, ""));
                    break;
                }
            }
            if (!elements.isEmpty())
                person_ = elements.get(0);

            Map<String, Double> distances = new HashMap<>();
            for (String person : persons) {
                distances.put(person, Similarity.wordSimi(person_, person));
            }
            Map<String, Double> top = CompareUtils.sortByValues(distances, true);
            if (new ArrayList<Double>(top.values()).get(0) > 0.7) {
                flag3 = true;
                person_key = new ArrayList<String>(top.keySet()).get(0);
            }
        }

        Collection<String> batch = null;
        try {
            batch = person_keys.get(person_key);
        } catch (Exception e) {
        }
        // name check and code check
        if (batch.size() > 0) {

            String name_ = null;
            String code_ = null;
            try {
                name_ = list.get(0);
            } catch (Exception e) {
            }
            try {
                code_ = list.get(1);
            } catch (Exception e) {
            }
            if (name_ != null && code_ != null) {
                name_ = name_.replaceAll(":", "");
                code_ = code_.replaceAll(":", "");
                Map<String, Double> distances1 = new HashMap<>();
                Map<String, Double> distances2 = new HashMap<>();
                for (String info : batch) {
                    String name_info = info.split("\\|")[1];
                    String code_info = info.split("\\|")[2];
                    distances1.put(name_info + "|" + code_info, Similarity.wordSimi(name_, name_info));
                    distances2.put(name_info + "|" + code_info, Similarity.wordSimi(code_, code_info));
                }
                Map<String, Double> top = CompareUtils.sortByValues(distances1, true);
                String n_c = null;
                String c_n = null;
                n_c = new ArrayList<String>(top.keySet()).get(0);
                if (new ArrayList<Double>(top.values()).get(0) > 0.7) {
                    flag1 = true;
                }
                top = CompareUtils.sortByValues(distances2, true);
                c_n = new ArrayList<String>(top.keySet()).get(0);
                if (new ArrayList<Double>(top.values()).get(0) > 0.8) {
                    flag2 = true;
                }

                if (n_c.split("\\|")[1].equals(c_n.split("\\|")[1]))
                    flag = true;
            } else if ((name_ != null && code_ == null) || (name_ == null && code_ != null)) {
                String info = null;
                if (name_ != null && code_ == null)
                    info = name_;
                else if (name_ == null && code_ != null)
                    info = code_;

                info = info.replaceAll(":", "");
                Map<String, Double> distances1 = new HashMap<>();
                for (String infos : batch) {
                    String name_info = infos.split("\\|")[1];
                    String code_info = infos.split("\\|")[2];

                    if (name_ != null)
                        distances1.put(name_info + "|" + code_info, Similarity.wordSimi(info, name_info));
                    else
                        distances1.put(name_info + "|" + code_info, Similarity.wordSimi(info, code_info));
                }
                Map<String, Double> top = CompareUtils.sortByValues(distances1, true);

                if (new ArrayList<Double>(top.values()).get(0) > 0.8) {
                    if (name_ != null)
                        flag1 = true;
                    else
                        flag2 = true;

                    flag = true;
                }
            }
        }


        System.out.println(flag);
        System.out.println("-----------");
        System.out.println(flag1 + "-" + flag2 + "-" + flag3);
        System.out.println(list.get(0) + "---" + list.get(1) + "---" + list.get(2));
        return flag;


    }

    public boolean method3(List<String> list) {

        boolean flag3 = false;
        String person_key = null;
        double person_value = 0.0;
        String person_ = null;
        String name_ = null;
        String code_ = null;
        if (list.get(0) != null) {
            name_ = list.get(0);
            name_ = name_.replaceAll(":", "");
        }
        if (list.get(1) != null) {
            code_ = list.get(1);
            code_ = cancelCN(code_);
        }

        if (list.get(2) != null) {
            person_ = list.get(2);
            person_ = person_.replaceAll(":", "");
            List<String> elements = new ArrayList<>();
            for (String disAmbig : disAmbigs) {
                String disAmbigWord = disAmbig.replaceAll("\\.\\*\\?", "");
                Pattern pattern = Pattern.compile(disAmbig);
                Matcher matcher = pattern.matcher(person_);
                if (matcher.find()) {
                    elements.add(matcher.group().replaceAll(disAmbigWord, ""));
                    break;
                }
            }
            if (!elements.isEmpty())
                person_ = elements.get(0);

        }

//        String a = name_ + code_+ person_;
        StringBuilder sb = new StringBuilder();
        if (name_ != null)
            sb.append(name_);
        if (code_ != null)
            sb.append(code_);
        if (person_ != null)
            sb.append(person_);
        String a = null;
        Map<String, Double> distances = new HashMap<>();
        for (String row : table.rowKeySet()) {
            String name_t = "";
            String code_t = "";
            String person_t = "";
            if (name_ != null)
                name_t = table.row(row).get("name");
            if (code_ != null)
                code_t = table.row(row).get("code");
            if (person_ != null)
                person_t = table.row(row).get("person");
            a = name_t + code_t + person_t;
            distances.put(a, Similarity.wordSimi(a, sb.toString()));
        }
        Map<String, Double> top = CompareUtils.sortByValues(distances, true);
        if (new ArrayList<Double>(top.values()).get(0) > 0.7) {
            flag3 = true;
            person_key = new ArrayList<String>(top.keySet()).get(0);
            person_value = new ArrayList<Double>(top.values()).get(0);
        }


        System.out.println("----------------------");
//        System.out.println(flag1 + "-" + flag2 + "-" + flag3);
        System.out.println(flag3);
        System.out.println(person_key);
        System.out.println(name_ + "---" + code_ + "---" + person_);
        System.out.println(person_value);
        return flag3;


    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    private String signature = "";

    public boolean method4(List<String> list) {
        signature = "";
        boolean flag3 = false;
        String person_key = null;
        double person_value = 0.0;
        String person_ = null;
        String name_ = null;
        String code_ = null;
        String foodType_ = null;
        if (list.get(0) != null) {
            name_ = list.get(0);
            name_ = name_.replaceAll(":", "");
        }
        if (list.get(1) != null) {
            code_ = list.get(1);
            code_ = cancelCN(code_);
        }
        if (list.get(3) != null) {
            foodType_ = list.get(3);
            foodType_ = foodType_.replaceAll(":", "");
            foodType_ = foodType_.replaceAll("签发人", "");
        }

        if (list.get(2) != null) {
            person_ = list.get(2);
            person_ = person_.replaceAll(":", "");
            List<String> elements = new ArrayList<>();
            for (String disAmbig : disAmbigs) {
                String disAmbigWord = disAmbig.replaceAll("\\.\\*\\?", "");
                Pattern pattern = Pattern.compile(disAmbig);
                Matcher matcher = pattern.matcher(person_);
                if (matcher.find()) {
                    elements.add(matcher.group().replaceAll(disAmbigWord, ""));
                    break;
                }
            }
            if (!elements.isEmpty())
                person_ = elements.get(0);
        }

//        String a = name_ + code_+ person_;
        StringBuilder sb = new StringBuilder();
        if (name_ != null)
            sb.append(name_);
        if (code_ != null)
            sb.append(code_);
        if (person_ != null)
            sb.append(person_);
        if (foodType_ != null)
            sb.append(foodType_);
        String a = null;
        Map<String, Double> distances = new HashMap<>();
        for (String row : table.rowKeySet()) {
            String name_t = "";
            String code_t = "";
            String person_t = "";
            String foodType_t = "";
            if (name_ != null)
                name_t = table.row(row).get("name");
            if (code_ != null)
                code_t = table.row(row).get("code");
            if (person_ != null)
                person_t = table.row(row).get("person");
            if (foodType_ != null)
                foodType_t = table.row(row).get("foodType");
            a = name_t + code_t + person_t + foodType_t;
            distances.put(a, Similarity.wordSimi(a, sb.toString()));
        }
        Map<String, Double> top = CompareUtils.sortByValues(distances, true);
        if (new ArrayList<Double>(top.values()).get(0) > 0.7) {
            flag3 = true;
            person_key = new ArrayList<String>(top.keySet()).get(0);
            person_value = new ArrayList<Double>(top.values()).get(0);
            signature = person_key;
        }


//        System.out.println("----------------------");
////        System.out.println(flag1 + "-" + flag2 + "-" + flag3);
//        System.out.println(flag3);
//        System.out.println(person_key);
//        System.out.println(name_ + "---" + code_ + "---" + person_+"---"+foodType_);
//        System.out.println(person_value);

        return flag3;


    }

    public boolean method5(List<String> list) {//duplicate premit
        signature = "";
        boolean flag3 = false;
        String person_key = null;
        double person_value = 0.0;
        String person_ = null;
        String name_ = null;
        String code_ = null;
        String foodType_ = null;
        if (list.get(0) != null) {
            name_ = list.get(0);
            name_ = name_.replaceAll(":", "");
        }
        if (list.get(1) != null) {
            code_ = list.get(1);
            code_ = cancelCN(code_);
        }
        if (list.get(3) != null) {
            foodType_ = list.get(3);
            foodType_ = foodType_.replaceAll(":", "");
            foodType_ = foodType_.replaceAll("签发人", "");
        }

        if (list.get(2) != null) {
            person_ = list.get(2);
            person_ = person_.replaceAll(":", "");
            List<String> elements = new ArrayList<>();
            for (String disAmbig : disAmbigs) {
                String disAmbigWord = disAmbig.replaceAll("\\.\\*\\?", "");
                Pattern pattern = Pattern.compile(disAmbig);
                Matcher matcher = pattern.matcher(person_);
                if (matcher.find()) {
                    elements.add(matcher.group().replaceAll(disAmbigWord, ""));
                    break;
                }
            }
            if (!elements.isEmpty())
                person_ = elements.get(0);
        }

//        String a = name_ + code_+ person_;
        StringBuilder sb = new StringBuilder();
        if (name_ != null)
            sb.append(name_);
        if (code_ != null)
            sb.append(code_);
        if (person_ != null)
            sb.append(person_);
        if (foodType_ != null)
            sb.append(foodType_);
        String a = null;
        Map<String, Double> distances = new HashMap<>();
        for (String row : table.rowKeySet()) {
            String name_t = "";
            String code_t = "";
            String person_t = "";
            String foodType_t = "";
            if (name_ != null)
                name_t = table.row(row).get("name");
            if (code_ != null)
                code_t = table.row(row).get("code");
            if (person_ != null)
                person_t = table.row(row).get("person");
            if (foodType_ != null)
                foodType_t = table.row(row).get("foodType");
            a = name_t + code_t + person_t + foodType_t;
            distances.put(a+"|"+row, Similarity.wordSimi(a, sb.toString()));
        }
        Map<String, Double> top = CompareUtils.sortByValues(distances, true);
        if (new ArrayList<Double>(top.values()).get(0) > 0.7) {
            flag3 = true;
            String rowId = new ArrayList<String>(top.keySet()).get(0).split("\\|")[1];

            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(table.row(rowId).get("name"));
            stringBuffer.append(table.row(rowId).get("person"));
            stringBuffer.append(table.row(rowId).get("foodType"));
            stringBuffer.append(table.row(rowId).get("|"));
            stringBuffer.append(table.row(rowId).get("code"));
            person_value = new ArrayList<Double>(top.values()).get(0);
            signature = stringBuffer.toString();
        }


//        System.out.println("----------------------");
////        System.out.println(flag1 + "-" + flag2 + "-" + flag3);
//        System.out.println(flag3);
//        System.out.println(person_key);
//        System.out.println(name_ + "---" + code_ + "---" + person_+"---"+foodType_);
//        System.out.println(person_value);

        return flag3;


    }
    public List<String> wordsDetect(String jsonString) {
        int flag = 0;
        JsonParser parser = new JsonParser();
        JsonElement jsonObject = new JsonObject();
        try {
            jsonObject = (JsonElement) parser.parse(jsonString);
        } catch (JsonIOException e) {
            e.printStackTrace();
        }
        JsonArray wordsJsa = new JsonArray();
        try {
            wordsJsa = jsonObject.getAsJsonObject().get("words_result").getAsJsonArray();
        } catch (Exception e) {
        }

        String words = "";
        for (JsonElement jse : wordsJsa) {
            words = words + jse.getAsJsonObject().get("words").toString().replaceAll("\"", "");
        }
        this.init();
        List<String> res = this.policy(words);
        JSONObject jso = new JSONObject();
        jso = JSONObject.parseObject(jsonObject.toString());
        jso.put("res", res);
        return res;

    }

    public String rawString(String jsonString) {
        int flag = 0;
        JsonParser parser = new JsonParser();
        JsonElement jsonObject = new JsonObject();
        try {
            jsonObject = (JsonElement) parser.parse(jsonString);
        } catch (JsonIOException e) {
            e.printStackTrace();
        }
        JsonArray wordsJsa = new JsonArray();
        try {
            wordsJsa = jsonObject.getAsJsonObject().get("words_result").getAsJsonArray();
        } catch (Exception e) {
        }

        String words = "";
        for (JsonElement jse : wordsJsa) {
            words = words + jse.getAsJsonObject().get("words").toString().replaceAll("\"", "");
        }


        return words;

    }
}
