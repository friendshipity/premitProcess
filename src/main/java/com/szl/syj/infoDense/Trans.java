package com.szl.syj.infoDense;



import com.szl.syj.utils.TextUtils;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by Administrator on 2017/8/18.
 */
public class Trans {
    private double[][] proMatrix;
    private TreeMap<String, Integer> indexMap;
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Trans.class);

    public  Trans() {
        List<String> raws = stopWords(TextUtils.loadList("rawNames.txt"));
       Trans(raws);
    }

    public void Trans(List<String> raws) {
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
                    System.err.println(A + "->" + B + "\t" + "no such character in dictionay");
                    //log.error(A + "->" + B + "\t" + "no such character in dictionay");
                    outs.add(0.0);
                }

            }
        }
        return outs;
    }

    public static void main(String[] args) {


        Trans trans = new Trans();
        List<String> raws = TextUtils.loadList("THUOCL_food.txt");
        for (String raw : raws) {
            List<Double> frqs = trans.match(raw.split("\t")[0]);
            boolean flag = false;
            for (double frq : frqs) {
                if (frq != 0.0) {
                    flag = true;
                }
            }
            if (!flag)
                log.info(raw.split("\t")[0] + "\t" + frqs.toString());
        }
        System.out.println("123");
        // Matrix trans = new Matrix(proMatrix);
    }
}
