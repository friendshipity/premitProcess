package com.szl.syj.utils;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/11/17.
 */
public class TopicMap extends HashMap<String, Integer[]> {
    public HashMap<String, Integer[]> map;
    public HashMap<String, Integer[]> getMap() {
        return map;
    }
    public TopicMap(HashMap<String, Integer[]> orimap) {
        map = orimap;
    }

    //    public void merge(HashMap<String, Integer[]> newMap) {
    public void merge(TopicMap newTmap) {
        HashMap<String, Integer[]> newMap = newTmap.getMap();
        if (map != null)
            for (String word : newMap.keySet()) {
                if (map.keySet().contains(word))
                    for (int i = 0; i < map.get(word).length; i++) {
                        map.get(word)[i] += newMap.get(word)[i];
                    }
                else {
                    map.put(word, newMap.get(word));
                }
            }
        else {
            System.err.println("topic map didn't initiate..");
        }
    }


    public static void main(String[] args) {
        //OCRtest
        HashMap<String, Integer[]> map = new HashMap<>();
        Integer[] iArray = new Integer[5];
        iArray[0] = 1;
        iArray[1] = 2;
        iArray[2] = 3;
        iArray[3] = 4;
        iArray[4] = 5;
        Integer[] iArray_ = iArray.clone();
        map.put("a", iArray);
        map.put("b", iArray_);

        TopicMap tMap = new TopicMap(map);
        HashMap<String, Integer[]> map1 = new HashMap<>();
        Integer[] iArray1 = new Integer[5];
        iArray1[0] = 1;
        iArray1[1] = 2;
        iArray1[2] = 3;
        iArray1[3] = 4;
        iArray1[4] = 5;
        Integer[] iArray1_ = iArray1.clone();
        map1.put("a", iArray1);
        map1.put("b", iArray1_);


        TopicMap nMap = new TopicMap(map1);
        tMap.merge(nMap);
        System.out.print("123");

    }
}

