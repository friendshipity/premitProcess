package com.szl.syj.utils;


import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/7/7.
 */
public class TextUtils {
    public static void writeList(List<String> list, String fileName) {
        try {
            File file = new File(fileName);

            if (file.exists()) { // 如果已存在,删除旧文件
                file.delete();
            }
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            for (String out : list) {
                writer.write(out);
                writer.write("\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeArrayMap(TreeMap<String, double[]> map, String filePath) {
        try {
            File file1 = new File(filePath);
            FileWriter fileWriter = new FileWriter(file1);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            for (String out : map.keySet()) {
                double[] vectors = map.get(out);
                writer.write(out + " ");
                for (int i = 0; i < vectors.length; i++) {
                    if (i != vectors.length - 1) {
                        writer.write(String.valueOf(vectors[i]) + " ");
                    } else {
                        writer.write(String.valueOf(vectors[i]));
                    }
                }
                writer.write("\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeArray(double[][] mat, String filePath) {
        try {
            File file = new File(filePath);
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            for (int i = 0; i < mat.length; i++) {
                writer.write("\n");
                for (int j = 0; j < mat[0].length; j++)
                    writer.write(mat[i][j] + "\t\t");

            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeArrayMap2Csv(TreeMap<String, double[]> map, String filePath) {
        try {
            File file1 = new File(filePath);
            FileWriter fileWriter = new FileWriter(file1);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            for (String out : map.keySet()) {
                double[] vectors = map.get(out);
                writer.write(out + ",");
                for (int i = 0; i < vectors.length; i++) {
                    if (i != vectors.length - 1) {
                        writer.write(String.valueOf(vectors[i]) + ",");
                    } else {
                        writer.write(String.valueOf(vectors[i]));
                    }
                }
                writer.write("\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void writeMmap(TreeMap<String, TreeSet<String>> map, String fileName) {
        try {
            File file1 = new File(fileName + ".txt");
            FileWriter fileWritter1 = new FileWriter(file1.getName(), true);
            BufferedWriter writter1 = new BufferedWriter(fileWritter1);
            for (String key : map.keySet()) {
                for (String o : map.get(key)) {
                    if (key == null) {
                        writter1.write("empty" + "\t" + o);
                        writter1.write("\n");
                    } else
                        writter1.write(o + "\t" + key);
                    //                        writter1.write(key+"\t"+o.toString());
                    writter1.write("\n");

                }
            }
            writter1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeListMap(HashMap<Integer, List<String>> map, String fileName) {
        try {
            File file = new File(fileName + ".txt");
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            for (Integer cluster : map.keySet()) {
                writer.write(cluster + "\n");
                for (String keyWords : map.get(cluster)) {
                    writer.write(keyWords + "\n");
                }
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> loadList(String fileName) {
        List<String> elements = new ArrayList<>();
        try {
            String line;
            BufferedReader reader1 = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
            while ((line = reader1.readLine()) != null) {
                elements.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return elements;
    }

    public static List<String> loadList2(String fileName) {
        List<String> elements = new ArrayList<>();
        try {
            FileReader fr = new FileReader(fileName);
            Scanner in = new Scanner(fr);
            String line;
            while (in.hasNextLine()) {
                line = in.nextLine();
                elements.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return elements;
    }


    public static List<String> loadCert164(String fileName) {
        List<String> elements = new ArrayList<>();
        try {
            String line;
            BufferedReader reader1 = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
            while ((line = reader1.readLine()) != null) {
                try {
//                    int size = line.toCharArray().length;
//                    String head = line.split("\"")[0];
//                    js64 = line.substring(0,size-1);
//                    js64 = js64.replaceAll(head,"");
//                    js64 = js64.substring(2,js64.length()-1);
//                    js64 = js64.replaceAll("\"\"","\"");
                    String regEx1 = "[\\[\"img_base64\":\"]([\\s\\S]*?)[\\]\",\"md5_code\":]";
                    String regEx2 = "\"营业执照\",\"img_base64\"\":\"\"(.*?)\"\"md5_code";
                    Pattern pattern = Pattern.compile(regEx2);
                    Matcher matcher = pattern.matcher(line);
                    if (matcher.find()) {
                        elements.add(matcher.group(1));
                    }

                } catch (Exception e) {
                    System.err.println(line);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return elements;
    }

    public static List<String> loadCert264(String fileName) {
        List<String> elements = new ArrayList<>();
        try {
            String line;
            BufferedReader reader1 = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
            while ((line = reader1.readLine()) != null) {
                try {
//

                    String regEx1 = "[\\[\"img_base64\":\"]([\\s\\S]*?)[\\]\",\"md5_code\":]";
                    String regEx2 = "\"\"食品经营许可\"\",\"\"img_base64\"\":\"\"(.*?)\"\",\"\"md5_code";
                    Pattern pattern = Pattern.compile(regEx2);
                    Matcher matcher = pattern.matcher(line);
                    if (matcher.find()) {
                        elements.add(matcher.group(1));
                    }

                } catch (Exception e) {
                    System.err.println(line);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return elements;
    }


    public static String ReadFile(String Path) {
        BufferedReader reader = null;
        String laststr = "";
        try {
            FileInputStream fileInputStream = new FileInputStream(Path);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                laststr += tempString;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return laststr;
    }


    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("deleted " + fileName);
                return true;
            } else {
                System.err.println("deleted " + fileName + " fail");
                return false;
            }
        } else {
            System.err.println("deleted：" + fileName + "doesn't exist");
            return false;
        }
    }

    public static void deleteUnderDir(String fileName) {
        File file = new File(fileName);
        deleteUnderDir(file);
    }

    public static void deleteUnderDir(File file) {

        File[] files = file.listFiles();
        if (files != null)
            for (int i = 0; i < files.length; i++) {
                deleteUnderDir(files[i]);
                files[i].delete();
            }

    }

    public static void deletedir(File file) {

        if (file.isFile() || file.list().length == 0) {
            file.delete();
        } else {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                deletedir(files[i]);
                files[i].delete();
            }

//            if(file.exists())         //如果文件本身就是目录 ，就要删除目录
//                file.delete();
        }
    }

}
