package com.szl.syj.dict;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.*;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;
import com.szl.syj.Dao.CommDaoImpl;
import com.szl.syj.utils.CharHash;
import com.szl.syj.LSH.SimHash;
import com.szl.syj.utils.mapUtils.MapCount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by Administrator on 2018/4/4.
 */
@Component
public class HashMake {
    @Autowired
    private CommDaoImpl commDao;
    private List<String> Names;
    private List<String> Codes;

    public List<String> getFoodTypeLastName() {
        return FoodTypeLastName;
    }

    private List<String> FoodTypeLastName;

    public List<String> getNameLastNames() {
        return NameLastNames;
    }

    private List<String> NameLastNames;
    private Table<String, String, String> table;
    private HashMap<String, Set<String>> templateMap;


    public CommDaoImpl getCommDao() {
        return commDao;
    }

    public Table<String, String, String> getTable() {
        return table;
    }


    public Multimap<String, String> getPerson2names() {
        return person2names;
    }

    private Multimap<String, String> person2names;

    public List<String> getPersons() {
        return Persons;
    }

    public List<String> getNames() {
        return Names;
    }

    public List<String> getCodes() {
        return Codes;
    }

    private List<String> Persons;

    public void init() {
        List<Map<String, Object>> jsos = new ArrayList<>();
        jsos = commDao.getAll("id,qymc,xkzzsh,xm", "food_business");
        List<String> names = new ArrayList<>();
        List<SimHash> simHashesNames = new ArrayList<>();
        List<SimHash> simHashesCodes = new ArrayList<>();
        List<SimHash> simHashesPersons = new ArrayList<>();
        table = HashBasedTable.create();
        this.makeTable();
//        this.makeTemplate(new ArrayList<String>(table.column("businessType").values()),0,2);
//        List<String> starts = this.makeTemplate(new ArrayList<String>(table.column("foodType").values()), 2, 0);
//        List<String> ends = this.makeTemplate(new ArrayList<String>(table.column("foodType").values()), 0, 2);
//        List<String> whole = this.makeTemplate(new ArrayList<String>(table.column("foodType").values()));


        person2names = ArrayListMultimap.create();
        Names = new ArrayList<>();
        Codes = new ArrayList<>();
        Persons = new ArrayList<>();
        List<JSONObject> ljs = new ArrayList<>();
        for (Map jso : jsos) {
            String name = null;
            int nameHash = 0;
            String code = null;
            int codeHash = 0;
            String person = null;
            int personHash = 0;

            String id = null;
            try {
                id = jso.get("id").toString();
            } catch (Exception e) {
            }
            try {
                name = jso.get("qymc").toString();
                Names.add(name);
//                simHashesNames.add(new SimHash(name,64));
            } catch (Exception e) {
            }
            try {
                code = jso.get("xkzzsh").toString();
                Codes.add(code);
//                simHashesCodes.add(new SimHash(code,64));
            } catch (Exception e) {
            }
            try {
                person = jso.get("xm").toString();
                Persons.add(person);
//                simHashesPersons.add(new SimHash(person,64));
            } catch (Exception e) {
            }
            if (person != null) {
                person2names.put(person, id + "|" + name + "|" + code);
            }
        }
//        FoodTypeLastName = makeTemplate(new ArrayList<String>(table.column("foodType").values()), 0, 2);
        FoodTypeLastName = makeTemplateToken(new ArrayList<String>(table.column("foodType").values()), 0, 2);
        FoodTypeLastName.remove("食品");



        NameLastNames = makeTemplateToken(new ArrayList<String>(table.column("name").values()), 0, 4);
        NameLastNames.add("川菜馆");
        NameLastNames.add("饮品店");
        NameLastNames.add("食品有限公司");
        NameLastNames.add("电子商务有限公司");
        NameLastNames.add("花饭餐饮店");
        NameLastNames.add("商贸有限公司");
        NameLastNames.add("德克士快餐店");
        NameLastNames.add("快餐店");
        NameLastNames.add("冒菜店");
        NameLastNames.add("小吃店");
        NameLastNames.add("科技有限公司");
        NameLastNames.add("沈小福小吃");
        NameLastNames.add("便利店");
        NameLastNames.add("港式茶餐厅");
        NameLastNames.add("豆花饭餐饮店");
        NameLastNames.add("餐馆");
        NameLastNames.add("西餐");
        NameLastNames.add("川菜馆");
        NameLastNames.add("卤菜店");
        NameLastNames.add("餐饮有限公司");
        NameLastNames.add("贵宾餐馆");

        NameLastNames.remove("88");
        NameLastNames.remove("监督管理局");
        NameLastNames.remove("　　");
        NameLastNames.remove("食品销售");
        NameLastNames.remove(":食品销售");
        NameLastNames.remove("态食品销售");
        NameLastNames.remove("品销售");


//        高新区自贡特色烧牛肉店", ":JY25101090016740", ":舒琴日常"],
        // hash speed test
//        long s =System.currentTimeMillis();
//        String name = "高新自贡特色牛肉店";
//        SimHash simHash = new SimHash(name,64);
//        Map<String,Integer> distances = new HashMap<>();
//        for(SimHash simHasher:simHashesNames){
//            distances.put( simHasher.getToken(),simHash.hammingDistance(simHasher));
//        }
//        Map<String,Integer> top = CompareUtils.sortByValues(distances,false);
//        long e =System.currentTimeMillis();
//        System.out.println(e-s);
//        System.currentTimeMillis();
//
//         s =System.currentTimeMillis();


    }


    public void process() {
        List<Map<String, Object>> jsos = new ArrayList<>();
        jsos = commDao.getAll("id,qymc,xkzzsh,xm", "food_business");
        List<String> names = new ArrayList<>();
        List<JSONObject> ljs = new ArrayList<>();
        for (Map jso : jsos) {
            String name = null;
            double nameHash = 0.0;
            String code = null;
            double codeHash = 0.0;
            String person = null;
            double personHash = 0.0;

            String id = null;
            try {
                id = jso.get("id").toString();
            } catch (Exception e) {
            }
            try {
                name = jso.get("qymc").toString();
                nameHash = CharHash.charHash(name);
            } catch (Exception e) {
            }
            try {
                code = jso.get("xkzzsh").toString();
                if (code.length() == 16)
                    codeHash = CharHash.charHash2(code);
            } catch (Exception e) {
            }
            try {
                person = jso.get("xm").toString();
                personHash = CharHash.charHash(person);
            } catch (Exception e) {
            }


            //write hash
            if (id != null) {
                JSONObject js = new JSONObject();
                js.put("qymc_hash", nameHash);
                js.put("xkzzsh_hash", codeHash);
                js.put("xm_hash", personHash);
                js.put("id", id);
//                commDao.updateJsByIdm(js, "food_business", id);
                if (Integer.valueOf(id) % 1000 == 0) {
                    System.out.println(id);
                }
                ljs.add(js);

            }
        }

        commDao.updateToFoodBusiness(ljs);
    }


    public void makeTable() {
        List<Map<String, Object>> jsos = new ArrayList<>();
        jsos = commDao.getAll("id,qymc,xkzzsh,idno,fzjg,jycs,jyxm,ztyt,xm", "food_business");
        List<String> names = new ArrayList<>();
        List<JSONObject> ljs = new ArrayList<>();
        for (Map jso : jsos) {
            String name = "";
            String code = "";
            String code2 = "";
            String institution = "";
            String location = "";
            String foodType = "";
            String businessType = "";
            String person = "";

            String id = null;
            try {
                id = jso.get("id").toString();
            } catch (Exception e) {
            }
            try {
                name = jso.get("qymc").toString();
            } catch (Exception e) {
            }
            try {
                code = jso.get("xkzzsh").toString();
            } catch (Exception e) {
            }
            try {
                code2 = jso.get("idno").toString();
            } catch (Exception e) {
            }
            try {
                institution = jso.get("fzjg").toString();
            } catch (Exception e) {
            }
            try {
                location = jso.get("jycs").toString();
            } catch (Exception e) {
            }
            try {
                foodType = jso.get("jyxm").toString();
            } catch (Exception e) {
            }
            try {
                businessType = jso.get("ztyt").toString();
            } catch (Exception e) {
            }
            try {
                person = jso.get("xm").toString();
            } catch (Exception e) {
            }
            table.put(id, "name", name);
            table.put(id, "code", code);
            table.put(id, "code2", code2);
            table.put(id, "institution", institution);
            table.put(id, "location", location);
            table.put(id, "foodType", foodType);
            table.put(id, "businessType", businessType);
            table.put(id, "person", person);

        }
    }

    public List<String> makeTemplate(List<String> values, int start, int end) {
        MapCount<String> mc = new MapCount<>();
        for (String value : values) {

            List<Term> terms = HanLP.segment(value);
            if (terms.size() > 1) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < terms.size(); i++) {
                    if (i < start || i > terms.size() - end) {
                        sb.append(terms.get(i).word);
                    }
                }
                mc.add(sb.toString());
            }
        }
        return new ArrayList<>(mc.get().keySet());
    }

    public static String toToken(String str){
        StringBuilder token = new StringBuilder();
        for(int i=0;i<str.length();i++){
            token.append(str.charAt(i));
            token.append(" ");
        }
        return token.toString();
    }

    public List<String> makeTemplateToken(List<String> values, int start, int end) {
        MapCount<String> mc = new MapCount<>();
        for (String value : values) {

            StringTokenizer stringTokens = new StringTokenizer(toToken(value));
            if (stringTokens.countTokens() > 1) {
                int tokenLen = stringTokens.countTokens();
                StringBuilder sb = new StringBuilder();
                int index=0;
                while (stringTokens.hasMoreTokens()) {
                    index++;
                    String temp = stringTokens.nextToken();
                    if(index>=tokenLen-end && !temp.equals(")") && !temp.equals("）")&& !temp.equals("(")&& !temp.equals("（")&& !temp.equals(":"))
                        sb.append(temp);

                }


                if(!sb.toString().contains("督管理局") ||!sb.toString().contains("*") ||!sb.toString().contains("."))
                mc.add(sb.toString());
            }
        }
        return new ArrayList<>(mc.get().keySet());
    }

    public List<String> makeTemplate(List<String> values) {
        MapCount<String> mc = new MapCount<>();
        for (String value : values) {
            mc.add(value);
        }
        return new ArrayList<>(mc.get().keySet());
    }

    public static void main(String[] args) {


    }
}
