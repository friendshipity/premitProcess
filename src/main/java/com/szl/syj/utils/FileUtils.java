package com.szl.syj.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by Administrator on 2018/3/12.
 */
public class FileUtils {
    public static List<byte[]> readFilsAsByte(String docsPath) {
        List<File> fileList = Arrays.asList(new File(docsPath).listFiles());
        List<byte[]> docs = new ArrayList<>();
        for (File docFile : fileList) {
            byte[] imageBinary = ImageUtils.getImageBinary(docFile.getAbsolutePath());
            docs.add(imageBinary);
        }
        return docs;
    }




    public static List<String> read(String docsPath) {
        List<File> fileList = Arrays.asList(new File(docsPath).listFiles());
        List<String> docs = new ArrayList<>();
        for (File docFile : fileList) {
            List<String> docLines = TextUtils.loadList(docFile.getAbsolutePath());
            int lineIndex = 1;
            StringBuilder lineDocSb = new StringBuilder();
            for (String line : docLines) {
                lineDocSb.append(line + " ");
            }
            docs.add(lineDocSb.toString());
        }
        return docs;
    }


    public static void writePics(List<File> fileList,String path) {
        try {
            for (File docFile : fileList) {
                BufferedImage bi = null;
                try {
                    bi = ImageIO.read(docFile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ImageIO.write(bi, "jpg", new File(path+docFile.getName()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
