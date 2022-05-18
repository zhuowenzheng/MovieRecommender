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
        //List<String[]> DataList = new ArrayList<String[]>();
        Map<String,Map<String,Double>> userDataStruct = new HashMap<String,Map<String,Double>>();
        List<Map<String, Double>> putMapList = new ArrayList<Map<String, Double>>();
        putMapList.add(new HashMap<>());
        try (BufferedReader br = new BufferedReader(new FileReader(Filepath))) {

            while ((line = br.readLine()) != null) {

                Line = line.split(cvsSplitBy);//将逗号作为分隔符 划分一行 得到数组[]
                //System.out.println(line.getClass());
                if(count!=0) {
                    //DataList.add(Line);
                    putMapList.get(index).put(Line[1],Double.parseDouble(Line[2]));

                    //System.out.println("Id:"+Line[0]+" Movie:"+Line[1]+" Rating: "+Line[2]+ "  TimeStamp:"+Line[3]);
                    //System.out.println(userDataStruct.keySet());
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
}
