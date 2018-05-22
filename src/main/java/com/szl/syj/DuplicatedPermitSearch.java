package com.szl.syj;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Multimap;
import com.szl.syj.mongo.MongoOperator;
import com.szl.syj.utils.ImageUtils;
import com.szl.syj.utils.TextUtils;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.*;

/**
 * Created by Administrator on 2018/5/15.
 */
public class DuplicatedPermitSearch {
    static private Connection conn = null;
    private MongoOperator operator;

    public static Connection getConnection() throws FileNotFoundException, SQLException {
        if (conn == null) {
            String driver = "com.mysql.cj.jdbc.Driver";
            conn = DriverManager.getConnection("jdbc:mysql://172.27.9.141/syj_netfood?serverTimezone=UTC", "syj", "syj123.");
        }
        return conn;
    }

    public static void close() throws SQLException {
        conn.close();
    }

    DuplicatedPermitSearch() {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger rootLogger = loggerContext.getLogger("org.mongodb.driver");
        rootLogger.setLevel(Level.OFF);
        operator = new MongoOperator();
        operator.connect();
    }

    public Multimap<String, String> getBase64(Multimap<String, String> tgResMap) {
        Multimap<String, String> resMap = ArrayListMultimap.create();
        Map<String, String> map = new HashMap<>();
        for (String sid : tgResMap.keySet()) {
            for (String md5 : tgResMap.get(sid)) {
                map = operator.getPic("certificate_cache", md5);
                resMap.put(sid,map.get("base64"));
            }
        }
        return resMap;
    }

    public static void main(String[] args) throws Exception {


        Multimap<String, String> tgResMap = ArrayListMultimap.create();
        Multimap<String, String> wmResMap = ArrayListMultimap.create();
        BiMap<String, String> MD5toIndex = HashBiMap.create();
        Multimap<String, String> sid2Index = ArrayListMultimap.create();
        Multimap<String, String> sid2RawId = ArrayListMultimap.create();
        HashMap<String, String> index2Sid = new HashMap<>();
        HashMap<String, String> rawId2Sid = new HashMap<>();

        String sql = "select sid,md5_code from tg_shop_certificate_4";
        PreparedStatement pst = DuplicatedPermitSearch.getConnection().prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            String sid = rs.getString(1);
            if (sid != null) {
                String md5 = rs.getString(2);
                tgResMap.put(sid, md5);
            }
        }


        String sql1 = "select sid,md5_code from wm_shop_certificate_5";
        PreparedStatement pst1 = DuplicatedPermitSearch.getConnection().prepareStatement(sql1);
        ResultSet rs1 = pst1.executeQuery();
        while (rs1.next()) {
            String sid = rs1.getString(1);
            if (sid != null) {
                String md5 = rs1.getString(2);
                wmResMap.put(sid, md5);
            }
        }


        List<String> texts = TextUtils.loadList("certMD5.csv");
        int counter = 1;
        for (String text : texts) {
            MD5toIndex.put(text, String.valueOf(counter++));
        }

        for (String key : tgResMap.keySet()) {
            for (String key1 : tgResMap.get(key)) {
                sid2Index.put(key, MD5toIndex.get(key1));
                index2Sid.put(MD5toIndex.get(key1), key);
                String rawId = String.valueOf(Integer.valueOf(MD5toIndex.get(key1)) % 2000) + "," + String.valueOf(Integer.valueOf(MD5toIndex.get(key1)) / 2000);
                rawId2Sid.put(rawId, key);
                sid2RawId.put(key, rawId);
            }
        }

        for (String key : wmResMap.keySet()) {
            for (String key1 : wmResMap.get(key)) {
                sid2Index.put(key, MD5toIndex.get(key1));
                index2Sid.put(MD5toIndex.get(key1), key);
                String rawId = null;
                try {
                    rawId = String.valueOf(Integer.valueOf(MD5toIndex.get(key1)) % 2000) + "," + String.valueOf(Integer.valueOf(MD5toIndex.get(key1)) / 2000);
                } catch (Exception E) {
//                    E.printStackTrace();
                }
                rawId2Sid.put(rawId, key);
                sid2RawId.put(key, rawId);
            }
        }

        System.currentTimeMillis();
        List<File> fileList = Arrays.asList(new File("D://data//res2//").listFiles());
        List<String> csvs = new ArrayList<>();
        for (File file : fileList) {
            try {
                csvs.addAll(TextUtils.loadList(file.getAbsolutePath()));
//            restfulGetSignatureTest64("base64/img_certMD5_base64_1_0.csv");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        HashMap<String, String> rawId2Sig = new HashMap<>();

        Multimap<String, String> resMap = ArrayListMultimap.create();
        Multimap<String, String> resMap2 = ArrayListMultimap.create();


        for (String csv : csvs) {
            if (csv.contains("null") && csv.contains("class")) {
                rawId2Sig.put(csv.split("\\,")[0] + "," + csv.split("\\,")[1], csv.split("\\,")[3]);
            }
        }


        for (String sid : sid2RawId.keySet()) {
            for (String raw : sid2RawId.get(sid)) {
                if (rawId2Sig.keySet().contains(raw)) {
                    resMap.put(sid, raw + "___" + rawId2Sig.get(raw));
                }
            }
        }

        for (String key : resMap.keySet()) {
            if (resMap.get(key).size() > 1) {
                for (String ans : resMap.get(key)) {
                    resMap2.put(key, ans);
                }
            }
        }


        Multimap<String, String> sig4rawId = ArrayListMultimap.create();
        Multimap<String, String> sig4rawIdLimit = ArrayListMultimap.create();
        Multimap<String, String> sig4rawIdLimitLimit = ArrayListMultimap.create();


        for (String sig : rawId2Sig.keySet()) {
            sig4rawId.put(rawId2Sig.get(sig), sig);
        }

        for (String sig : sig4rawId.keySet()) {
            if (sig4rawId.get(sig).size() > 1) {
                for (String ans : sig4rawId.get(sig)) {
                    sig4rawIdLimit.put(sig, ans);
                }
            }
        }
        Multimap<String, String> oneSig2manySids = ArrayListMultimap.create();
        Multimap<String, String> oneSig2manySidsLimit = ArrayListMultimap.create();

        HashMap<String, String> Many2one = new HashMap();
        for (String sig : sig4rawIdLimit.keySet()) {

            for (String raw : sig4rawIdLimit.get(sig)) {
                if (rawId2Sid.keySet().contains(raw)) {
                    oneSig2manySids.put(sig, rawId2Sid.get(raw));
                }
            }
            if (oneSig2manySids.containsKey(sig)) {
                if (oneSig2manySids.get(sig).size() > 1) {
                    if (!sig.equals("锦江区幸福人家农家乐卢勇热食类食品制售nullJY25101040047197")) {
                        for (String sid : oneSig2manySids.get(sig)) {
                            Many2one.put(sid, sig);
                            oneSig2manySidsLimit.put(sig, sid);
                        }
                        for (String rawId : sig4rawIdLimit.get(sig)) {
                            sig4rawIdLimitLimit.put(sig, rawId);
                            File dir = new File("res1/" + sig);
                            if (!dir.exists()) {
                                dir.mkdir();
                            }
                            int fileIndex = Integer.valueOf(rawId.split(",")[1]);
                            int fileId = Integer.valueOf(rawId.split(",")[0]);
                            String fileIndexStr = null;
                            fileIndexStr = String.valueOf(fileIndex);


                            String TargetBase64 = TextUtils.loadList("D:\\data\\img_certMD5_base64_" + fileIndexStr + ".csv").get(fileId);
//                            write file

                            byte[] binary = null;
                            binary = ImageUtils.base64String2ByteFun(TargetBase64);
                            BufferedImage bi = ImageUtils.bytes2ImageBuffer(binary);
                            try {
//                    ImageIO.write(bi, "jpg", new File("pic/0"+String.valueOf(i)+"/" + String.valueOf(counter++) + ".jpg"));
                                ImageIO.write(bi, "jpg", new File("res1/" + sig + "/" + rawId + "--" + rawId2Sid.get(rawId) + ".jpg"));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
        int counter1 = 1;
        for(String sig: oneSig2manySidsLimit.keySet()){
            Set<String> sids = new HashSet<>();
            for(String sid:oneSig2manySidsLimit.get(sig)){
                sids.add(sid.split("_")[0]);
            }
            if(sids.size()<oneSig2manySidsLimit.get(sig).size()){
                System.out.println(sig);
                System.out.println(oneSig2manySidsLimit.get(sig));
                System.out.println();
                counter1++;
            }
        }
        System.out.println(counter1);
        System.currentTimeMillis();

    }

}
