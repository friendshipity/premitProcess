package com.szl.syj.mongo;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.mongodb.client.model.Filters.eq;

/**
 * Created by Administrator on 2016/12/13.
 */
@Component
public class MongoOperator {
    private static String url = "172.27.9.142";
    private static String database = "network_foods";
    private static String userName = "network";
    private static String password = "network123.";

    private MongoClient mongoClient = null;
    private MongoDatabase mongoDatabase = null;
    public int count = 0;

    public void connect() {
        System.setProperty("DEBUG.MONGO", "false");

// Enable DB operation tracing
        System.setProperty("DB.TRACE", "false");
        MongoCredential credential = MongoCredential.createCredential(userName, database, password.toCharArray());
        mongoClient = new MongoClient(new ServerAddress(url, 27017), Arrays.asList(credential));
        mongoDatabase = mongoClient.getDatabase(database);
    }

    public static HashMap<String, String> map = new HashMap<String, String>();


    //"certificate_cache"
    public Map<String,String> getPic(String collection,String Md5) {
        Map<String,String> map = new HashMap<>();
        if (mongoClient == null) {
            connect();
        }
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(collection);
        FindIterable<Document> iterable = mongoCollection.find(eq("md5_code",Md5));;
        MongoCursor cursor = iterable.iterator();


        while (cursor.hasNext()) {
            Document document = (Document) cursor.next();
            map.put("base64",document.get("img_base64").toString());
//            documents.add(document);
        }
        cursor.close();
//        mongoClient.close();
        return map;
    }
    //"certificate_cache"
    public Map<String,String> getBatch(String collection,Bson keys,int batchSize) {
        Map<String,String> map = new HashMap<>();
        if (mongoClient == null) {
            connect();
        }
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(collection);


        FindIterable<Document> iterable = mongoCollection.find().batchSize(batchSize);;

        MongoCursor<Document> cursor = iterable.iterator();

        while (cursor.hasNext()) {
            Document document = (Document) cursor.next();
            map.put(document.get("_id").toString(),document.get("md5_code").toString());
//            documents.add(document);
        }
        cursor.close();
        mongoClient.close();
        return map;
    }

//    public getCompanyInfo(String collection, int cur, int groupNum) {
//        if (mongoClient == null) {
//            connect();
//        }
//
//        try {
//
//
//            //FileOutputStream outputStream2=new FileOutputStream(indexPath);
//            MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(collection);
//            FindIterable iterable = mongoCollection.find().skip(cur).limit(groupNum);
//            MongoCursor cursor = iterable.iterator();
//
//            while (cursor.hasNext()) {
//                Document document = (Document) cursor.next();
//                try {
//                    JSONObject data = JSONObject.fromObject(document);
//                    list.add(data);
//                } catch (Exception e1) {
//                    e1.printStackTrace();
//                }
//
//            }
//
//
//            cursor.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return list;
//
//    }

//    public void getData(String collection, int cur, int groupNum, MakeIndex makeIndex) {
//        if (mongoClient == null) {
//            connect();
//        }
//
//        try {
//
//
//            //FileOutputStream outputStream2=new FileOutputStream(indexPath);
//            MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(collection);
//            FindIterable iterable = mongoCollection.find().skip(cur).limit(groupNum);
//            MongoCursor cursor = iterable.iterator();
//            int index = 0;
//
//            while (cursor.hasNext()) {
//                Document document = (Document) cursor.next();
//
//                try {
//                    JSONObject data = JSONObject.fromObject(document);
//                    if (data.get("enterprise_basic") != null) {
//                        JSONObject basic = data.getJSONObject("enterprise_basic");
//                        String name = basic.getString("enterprise_name");
//
//                        String id = data.getString("_id");
//                        if (data.get("enterprise_main_members") != null) {
//                            JSONArray members = data.getJSONArray("enterprise_main_members");
//                            for (int i = 0; i < members.size(); i++) {
//                                JSONObject member = (JSONObject) members.get(i);
//                                String memberName = member.getString("name");
//                                String position = member.getString("position");
//                                memberName = memberName.replaceAll("\\s", "");
//                                String edge = id + "|" + name + "/" + memberName + "|" + position + "/0";
//                                byte[] b = edge.getBytes("utf-8");
//
//                                FileOutputStream outputStream = new FileOutputStream(new File(saveDir + File.separator + "data_" + cur / groupNum), true);
//                                outputStream.write(b, 0, b.length);
//                                outputStream.close();
//                                makeIndex.makeIndex(name, index, b.length, cur / groupNum);
//                                makeIndex.makeIndex(memberName, index, b.length, cur / groupNum);
//                                index += b.length;
//                                count++;
//
//                            }
//                        }
//                        if (data.get("enterprise_shareholder") != null) {
//                            JSONArray shareholders = data.getJSONArray("enterprise_shareholder");
//                            for (int i = 0; i < shareholders.size(); i++) {
//                                JSONObject shareholder = (JSONObject) shareholders.get(i);
//                                String shareholderName = shareholder.getString("shareholder_name");
//                                shareholderName = shareholderName.replaceAll("\\s", "");
//                                String edge = id + "|" + name + "/" + shareholderName + "/1";
//                                byte[] b = edge.getBytes("utf-8");
//
//                                FileOutputStream outputStream = new FileOutputStream(new File(saveDir + File.separator + "data_" + cur / groupNum), true);
//                                outputStream.write(b, 0, b.length);
//                                outputStream.close();
//                                makeIndex.makeIndex(name, index, b.length, cur / groupNum);
//
//                                makeIndex.makeIndex(shareholderName, index, b.length, cur / groupNum);
//                                index += b.length;
//                                count++;
//
//                            }
//                        }
//
//                    }
//
//
//                } catch (Exception e1) {
//                    e1.printStackTrace();
//                }
//
//            }
//
//
//            System.out.println(count);
//            cursor.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//    }

    public void close() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
        }
    }

    public static void main(String[] arges) {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger rootLogger = loggerContext.getLogger("org.mongodb.driver");
        rootLogger.setLevel(Level.OFF);


        MongoOperator operator = new MongoOperator();
        operator.connect();
//        Map<String,String> documents = operator.getPic("certificate_cache");

        System.currentTimeMillis();

    }
}
