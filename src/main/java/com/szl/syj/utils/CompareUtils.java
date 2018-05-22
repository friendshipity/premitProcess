package com.szl.syj.utils;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Administrator on 2017/10/10.
 */
public class CompareUtils {
    public static <K, V extends Comparable<V>> TreeMap<K, V> sortByRate(final Map<K, V> map) {
        Comparator<K> valueComparator = new Comparator<K>() {
            public int compare(K k1, K k2) {
                V v1 = map.get(k1);
                V v2 = map.get(k2);
                int posNum1 = Integer.valueOf(((String) v1).split("\t")[0]);
                int negNum1 = Integer.valueOf(((String) v1).split("\t")[1]);
                double rate1 = (double) posNum1 / (double) negNum1;

                int posNum2 = Integer.valueOf(((String) v2).split("\t")[0]);
                int negNum2 = Integer.valueOf(((String) v2).split("\t")[1]);
                double rate2 = (double) posNum2 / (double) negNum2;

                int compare = 0;
                if (rate1 >= rate2) {
                    compare = -1;
                } else {
                    compare = 1;
                }
                if (compare == 0) return 1;
                else return compare;
            }
        };
        TreeMap<K, V> sortedByValues = new TreeMap<K, V>(valueComparator);
        sortedByValues.putAll(map);
        return sortedByValues;
    }

    public static <K, V extends Comparable<V[]>> Map<K, V[]> sortByKeyIndex(final TreeMap<K, V[]> map) {
        Comparator<K> valueComparator = new Comparator<K>() {
            public int compare(K k1, K k2) {
                int compare = Integer.valueOf((String) k1).compareTo(Integer.valueOf((String) k2));
                if (compare == 0) return 1;
                else return compare;
            }
        };
        Map<K, V[]> sortedByValues = new TreeMap<K, V[]>(valueComparator);
        sortedByValues.putAll(map);
        return sortedByValues;
    }

    public static <K, V extends Comparable<V>> Map<K, V> sortByValues(final Map<K, V> map, boolean upDown) {
        Comparator<K> valueComparator = new Comparator<K>() {
            public int compare(K k1, K k2) {
                int compare = 0;
                if (upDown)
                    compare = map.get(k2).compareTo(map.get(k1));
                else
                    compare = map.get(k1).compareTo(map.get(k2));
                
                if (compare == 0) return 1;
                else return compare;
            }
        };
        Map<K, V> sortedByValues = new TreeMap<K, V>(valueComparator);
        sortedByValues.putAll(map);
        return sortedByValues;
    }


    public static <K, V extends Comparable<V>> Map<K, V> sortByValues(final Map<K, V> map) {
        Comparator<K> valueComparator =  new Comparator<K>() {
            public int compare(K k1, K k2) {
                int compare = map.get(k2).compareTo(map.get(k1));
                if (compare == 0) return 1;
                else return compare;
            }
        };
        Map<K, V> sortedByValues = new TreeMap<K, V>(valueComparator);
        sortedByValues.putAll(map);
        return sortedByValues;
    }
}
