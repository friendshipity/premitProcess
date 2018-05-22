package com.szl.syj.dict;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;
import com.szl.syj.Dao.CommDaoImpl;
import com.szl.syj.utils.CompareUtils;
import com.szl.syj.utils.mapUtils.MapCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

import com.hankcs.*;

/**
 * Created by Administrator on 2018/4/3.
 */
@Component
public class DictProcess {

    @Autowired
    private CommDaoImpl commDao;
    private HashSet<String> words;
    private TreeSet<Integer> nameLen;
    private MapCount<String> mc;
    private List<String> dictMap;



    public DictProcess() {
        nameLen = new TreeSet<>();
        words = new HashSet<>();
        mc = new MapCount<>();
        dictMap = new ArrayList<>();
    }


    public List<String> getDict() {
        return new ArrayList<>(words);
    }

    public List<String> getDictMap(int i) {
        this.getData(i);
        HashMap<String, Integer> hm = mc.get();
        Map<String, Integer> om = CompareUtils.sortByValues(hm, true);
        List<String> lastNames = new ArrayList<>();
        if (i == 2) {
            lastNames.addAll(om.keySet());
        } else if (i == 1) {

        }
        //filter
        List<String> filterNames = new ArrayList<>();




        for (String name : lastNames) {
//            if(name.contains(")") ||name.equals("　")||name.equals("：")||name.equals(".")||name.equals("1"))
            if (name.contains(")") || name.contains("(") || name.contains("）"))
                continue;
            else
                filterNames.add(name);
        }

        //user define
        filterNames.add("川菜馆");
        filterNames.remove("监督管理局");
        filterNames.remove("部*");
        filterNames.remove("部.");
        dictMap.addAll(filterNames);
        return filterNames;
    }

    public List<String> getDictMap() {
        getDictMap(2);
//        getDictMap(1);
        return dictMap;
    }


    public List<String> dictMap() {
        return this.dictMap;
    }


    public void getData(int i) {
        List<Map<String, Object>> jsos = new ArrayList<>();
        jsos = commDao.getAll("id,qymc", "food_business");
        List<String> names = new ArrayList<>();
        for (Map jso : jsos) {
            String name = null;
            name = jso.get("qymc").toString();
            List<Term> terms = HanLP.segment(name);
            if (terms.size() > 1) {
                words.add(terms.get(terms.size() - 1).word);
//            mc.add(terms.get(terms.size()-1).word);
                if (i == 1)
                    mc.add(terms.get(terms.size() - 1).word);
                else if (i == 2)
                    mc.add(terms.get(terms.size() - 2).word + terms.get(terms.size() - 1).word);
            }
        }
    }

    public void getLenthMax() {
        List<Map<String, Object>> jsos = new ArrayList<>();
        jsos = commDao.getAll("id,qymc", "food_business");
        List<String> names = new ArrayList<>();
        for (Map jso : jsos) {
            String name = null;
            name = jso.get("qymc").toString();

            if (name.length() > 1) {
                nameLen.add(name.length());
            }
            if (name.length() > 30) {
                System.out.println(name);
            }
        }
    }


}
