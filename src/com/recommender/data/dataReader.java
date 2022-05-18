package com.recommender.data;

import java.io.*;
import java.util.*;

public class dataReader {
    /**
     * BufferedReader 读取
     * @param Filepath
     * @return userDataStruct 嵌套Hashmap
     */

    public static Map<String, Map<String, Double>> readRatingData(String Filepath){
        String line = "";
        String cvsSplitBy = ",";
        int count = 0;
        int index = 0;
        String flag = "";
        String[] Line = new String[1];
        Map<String,Map<String,Double>> userDataStruct = new HashMap<String,Map<String,Double>>();
        List<Map<String, Double>> putMapList = new ArrayList<Map<String, Double>>();
        putMapList.add(new HashMap<>());
        try (BufferedReader br = new BufferedReader(new FileReader(Filepath))) {

            while ((line = br.readLine()) != null) {

                Line = line.split(cvsSplitBy);//将逗号作为分隔符 划分一行 得到数组[]
                if(count!=0) {
                    putMapList.get(index).put(Line[1],Double.parseDouble(Line[2]));
                    if(!Objects.equals(Line[0], flag))
                    {
                        userDataStruct.put(flag,putMapList.get(index));
                        putMapList.add(new HashMap<>());
                        flag = Line[0];
                        index++;
                    }
                }
                count++;

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return userDataStruct;
    }

    //public static
    public static Map<String, Map<String,String>> readMovieData(String Filepath) {
        String line = "";
        String cvsSplitBy = ",";
        int count = 0;
        int index = 0;
        String flag = "";
        String[] Line = new String[1];
        Map<String, Map<String, String>> movieDataStruct = new HashMap<String, Map<String, String>>();
        List<Map<String, String>> putMapList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(Filepath))) {
            while ((line = br.readLine()) != null) {
                Line = line.split(cvsSplitBy);//将逗号作为分隔符 划分一行 得到数组[] csv中所有非分隔符的逗号已经处理为空格
                if (count != 0) {
                    putMapList.add(new HashMap<>());
                    putMapList.get(index).put(Line[1], Line[2]);
                    movieDataStruct.put(Line[0],putMapList.get(index));
                    index++;
                }
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movieDataStruct;
    }
}
