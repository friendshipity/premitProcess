package com.szl.syj.infoDense;

import com.google.common.collect.Table;
import com.szl.syj.dict.HashMake;
import com.szl.syj.utils.TextUtils;
import org.apache.commons.math3.stat.StatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by Administrator on 2018/4/10.
 */
@Component
public class WordTransEntropy {
    @Autowired
    private HashMake hashMake;
    private Table<String,String,String> table;
    private double[][] proMatrix;
    private TreeMap<String, Integer> indexMap;

    public void init(String str) {
        table = hashMake.getTable();
        List<String> raws = new ArrayList<>(table.column(str).values());
        WordTransEntropy(raws);
    }
    public void init() {
        table = hashMake.getTable();
        List<String> raws = new ArrayList<>(table.column("name").values());
        List<String> raws1 = new ArrayList<>(table.column("code").values());
        List<String> raws2= new ArrayList<>(table.column("location").values());
        List<String> raws3= new ArrayList<>(table.column("institution").values());
        raws.addAll(raws1);
        raws.addAll(raws2);
        raws.addAll(raws3);
        WordTransEntropy(raws);
    }

    public void WordTransEntropy(List<String> raws) {

        HashSet<String> words = new HashSet<>(raws);
        // TextUtils.writeList(new ArrayList<>(words),"rawNamesUid.txt");
        TreeSet<String> chars = Trans.toChar(new ArrayList<>(words));
        int index = 0;
        indexMap = new TreeMap<>();
        for (String c : chars) {
            indexMap.put(c, index++);
        }
        proMatrix = new double[chars.size()][chars.size()];
        transFit(raws);
    }

    public static List<String> stopWords(List<String> ins) {
        List<String> outs = new ArrayList<>();
        for (String in : ins) {
            in = in.replaceAll("\t", "");
            in = in.replaceAll("\\(", "");
            in = in.replaceAll("\\)", "");
            in = in.replaceAll("（", "");
            in = in.replaceAll("）", "");
            in = in.replaceAll(" ", "");
            in = in.replaceAll("[\\pP\\pS\\pZ]+", "");
            in = in.replaceAll("[a-zA-Z]+", "");
            outs.add(in);
        }

        return outs;
    }

    public static TreeSet<String> toChar(List<String> words) {
        TreeSet<String> chars = new TreeSet<>();
        for (String word : words) {
            char[] toBeStored = word.toCharArray();
            for (char c : toBeStored) {
                chars.add(String.valueOf(c));
            }
        }
        return chars;
    }

    public void transFit(List<String> chars) {
        for (String charless : chars) {
            fit(charless);
        }
    }

    public CharactorNode word2node(String chars) {
        CharactorNode root = new CharactorNode();
        char[] toBeStored = chars.toCharArray();
        for (char c : toBeStored) {
            CharactorNode node1 = new CharactorNode(String.valueOf(c));
            CharactorNode tmp = root;
            while (tmp.getNext() != null) {
                tmp = tmp.getNext();
            }
            tmp.setNextNode(node1);
        }
        return root;
    }

    public void fit(String word) {
        CharactorNode tmp = word2node(word);
        while (tmp.getNext() != null) {
            String A = tmp.getCharactor();
            String B = tmp.getNext().getCharactor();
            tmp = tmp.getNext();
            if (A != null && B != null) {
                double vtmp = proMatrix[indexMap.get(A)][indexMap.get(B)];
                proMatrix[indexMap.get(A)][indexMap.get(B)] = vtmp + 1;
            }
        }
    }

    public List<Double> match(String word) {
        List<Double> outs = new ArrayList<>();
        CharactorNode tmp = word2node(word);
        while (tmp.getNext() != null) {
            String A = tmp.getCharactor();
            String B = tmp.getNext().getCharactor();
            tmp = tmp.getNext();
            if (A != null && B != null) {
                try {
                    indexMap.get(A);
                    indexMap.get(B);
                    outs.add(proMatrix[indexMap.get(A)][indexMap.get(B)]);
                } catch (Exception e) {
                    //System.err.println(A + "->" + B + "\t" + "no such character in dictionay");
                    //log.error(A + "->" + B + "\t" + "no such character in dictionay");
                    outs.add(0.0);
                }

            }
        }
        return outs;
    }


    public Double infoDenseCheck(String str){

        List<Double> frqs = this.match(str);
        double[] frqs_m = new double[frqs.size()];
        for(int i = 0;i<frqs.size();i++){
            frqs_m[i]=frqs.get(i);
        }
        double density = StatUtils.mean(frqs_m);
        return density;
    }


    public static void main(String[] args){


    }
}
