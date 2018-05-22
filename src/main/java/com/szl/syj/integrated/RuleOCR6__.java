package com.szl.syj.integrated;

import com.baidu.aip.ocr.AipOcr;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.Callable;

/**
 * Created by Administrator on 2018/5/11.
 */

public class RuleOCR6__ implements Callable {
    private   String  base64;
    private String oid;
    private  JSONObject res;
    private  String clasz;
    public static final String APP_ID = "10917859";
    public static final String API_KEY = "HpFRWT59o4e2znSm39nli8S7";
    public static final String SECRET_KEY = "j1OcFUVOeLFzrqPIRrF7ieOSCcXkuUNj";
    public RuleOCR6__(String oid,String base64,String clasz) {
        if(clasz.startsWith("class2")) {
            this.oid = oid;
            this.base64 = base64;
            this.clasz = clasz;


        }
        else {
//            this.res = null;
            this.oid = oid;
            this.clasz=clasz;
        }
    }
    public Object call() throws Exception {
        if(clasz.startsWith("class2")){
            AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
            client.setConnectionTimeoutInMillis(2000);
            client.setSocketTimeoutInMillis(60000);
            HashMap<String, String> options = new HashMap<>();
            options.put("language_type", "CHN_ENG");
            options.put("detect_direction", "true");
            options.put("probability", "false");

            JSONObject res = null;
            try {
                res = client.basicGeneral(Base64.decodeBase64(base64), options);
            }catch (Exception e){
                System.err.println("baiduOCR api time out..");
            }
            this.res = res;

            return res.toString(2)+"|"+oid+"|"+clasz;
        }
        else{
            this.res = null;
            return "null|"+oid+"|"+clasz;
        }

    }

}
