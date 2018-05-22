package com.szl.syj.core;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.*;
import com.szl.syj.infoDense.WordTransEntropy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Administrator on 2018/4/11.
 */
@Component
public class InfoDens {
    @Autowired
    private WordTransEntropy wordTransEntropy;


    public double process(String str){
      return wordTransEntropy.infoDenseCheck(str);
    }

    public double densityValue(String jsonString) {
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
        double density = this.process(words);
        return density;

    }


}
