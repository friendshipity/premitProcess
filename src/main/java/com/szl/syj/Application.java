package com.szl.syj;

import com.szl.syj.dict.DictProcess;
import com.szl.syj.dict.HashMake;
import com.szl.syj.infoDense.WordTransEntropy;
import com.szl.syj.integrated.SinglePicProcess;
import com.szl.syj.utils.FileUtils;
import com.szl.syj.utils.ImageUtils;
import com.szl.syj.utils.TextUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@SpringBootApplication
@ComponentScan

public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext app = SpringApplication.run(Application.class);
        app.getBean(DictProcess.class).getDictMap();// init dicMap


//        List<String> pics =  TextUtils.loadCert164("base64/cert.csv");
//        byte[] bi = ImageUtils.base64String2ByteFun(pics.get(0));
//        System.out.println("123");
//        String image = "test2.jpeg";
//        byte[] imageBinaries = ImageUtils.getImageBinary(image);
//
//        SinglePicProcess singlePicProcess = app.getBean(SinglePicProcess.class);
//        singlePicProcess.RuleOCR(imageBinaries);


        app.getBean(HashMake.class).init();

        WordTransEntropy wordTransEntropy = app.getBean(WordTransEntropy.class);
        wordTransEntropy.init();
//        System.out.println(wordTransEntropy.infoDenseCheck("百度外专用D更专用百原"));
//        System.out.println(wordTransEntropy.infoDenseCheck("经营场所四川省成都市成华区双庆路8号成都万象"));
//        app.getBean(HashMake.class).process();
//        DictProcess dictProcess = app.getBean(DictProcess.class);
//        dictProcess.getLenthMax();
////        dictProcess.getData();
////        dictProcess.getDictMap();
//
//        System.currentTimeMillis();


//        RestTemplate restTemplate = new RestTemplate();
//        List<byte[]> pics = FileUtils.readFilsAsByte("p_liutong");
//        int all = pics.size();
//        System.out.println("start");
//        for (byte[] pic : pics) {
//            String url = "http://172.27.2.141:8081/fakeDetec";
//            restTemplate.postForObject(url, pic, int.class, "test");
//
//        }


//

        System.out.println("service running..");


    }
}
