package com.szl.syj.utils;

/**
 * Compute the similarity value between two string
 *
 * Created by yangyanqi on 2016/9/28.
 */
public class Similarity {
    private static int lcsLength(String x, String y) {
        int[][] m = new int[x.length() + 1][y.length() + 1];
        for (int i = 0; i <= x.length(); i++)
            m[i][0] = 0;
        for (int i = 0; i <= y.length(); i++)
            m[0][i] = 0;
        for (int i = 1; i <= x.length(); i++) {
            for (int j = 1; j <= y.length(); j++) {
                if (x.charAt(i - 1) == y.charAt(j - 1)) {
                    m[i][j] = m[i - 1][j - 1] + 1;
                } else {
                    if (m[i - 1][j] >= m[i][j - 1]) {
                        m[i][j] = m[i - 1][j];
                    } else
                        m[i][j] = m[i][j - 1];
                }
            }
        }
        return m[x.length()][y.length()];
    }

    private static int ld(String x, String y) {
        int[][] ld = new int[x.length() + 1][y.length() + 1];
        for (int i = 1; i <= x.length(); i++) {
            ld[i][0] = i;
        }
        for (int i = 1; i <= y.length(); i++) {
            ld[0][i] = i;
        }
        for (int i = 1; i <= x.length(); i++)
            for (int j = 1; j <= y.length(); j++) {
                if (x.charAt(i - 1) == y.charAt(j - 1))
                    ld[i][j] = ld[i - 1][j - 1];
                else
                    ld[i][j] = Math.min(Math.min(ld[i - 1][j - 1], ld[i - 1][j]), ld[i][j - 1]) + 1;
            }

        return ld[x.length()][y.length()];
    }

    public static double wordSimi(String str1, String str2) {
        int lcs = lcsLength(str1, str2);
        int ld = ld(str1, str2);
        //  return lcsLength(str1,str2);
        return 1.0 * lcs / (lcs + ld);
    }
    /**
     * @Author yangyanqi
     * main similarity computation
     */
    public static double wordSimiWithLengthDecay(String str1, String str2) {
        int lcs = lcsLength(str1, str2);
        int ld = ld(str1, str2);
        double we = wordExtention(str1,str2);
        //return 1.0 * (lcs) / (lcs + ld + Math.min(str1.length(), str2.length()));
        return 1.0 * (lcs) / (lcs + ld + Math.min(str1.length(), str2.length()))+ (we*0.01)/Math.max(str1.length(), str2.length());
    }

    /**
     * @Author yangyanqi
     * Quantization of the "Extension" of a character in sentence
     */

    public static double wordExtention(String x, String y) {
        String a, b;
        if (y.length() >= x.length()) {
            a = x;
            b = y;
        } else {
            a = y;
            b = x;
        }

        int[][] m = new int[b.length() + 1][a.length() + 1];
        for (int i = 0; i <= a.length(); i++)
            m[0][i] = 0;
        for (int i = 0; i <= b.length(); i++)
            m[i][0] = 0;

        for (int i = 0; i <b.length(); i++) {
            for (int j = 0; j <a.length(); j++) {
                if (b.charAt(i) == a.charAt(j))
                    m[i][j] = 1;
                else
                    m[i][j] = 0;
            }
        }
        for (int i = 1; i < b.length(); i++) {
            for (int j = 1; j < a.length(); j++) {
                if(m[i-1][j-1]>=1 && m[i][j]!=0){
                    m[i][j]+=m[i-1][j-1];
                }
            }
        }
        int k=0;

        for (int i = 0; i < b.length(); i++) {
            for (int j = 0; j < a.length(); j++) {
                   k+= m[i][j];

            }
        }
        return k*1.0;

    }

    public static double wordLcs(String str1, String str2) {
        int lcs = lcsLength(str1, str2);
        return 1.0 * lcs;
    }

    public static double wordLd(String str1, String str2) {
        int ld = ld(str1, str2);
        return 1.0 * ld;
    }
}
