package com.recommender;

import com.recommender.algorithms.Recommender;
import com.recommender.entity.Movie;
import com.recommender.entity.User;
import com.recommender.data.dataReader;

import java.util.*;

/**
 * @author alexzheng
 * @date 10 May 2022
 */

public class Demo {
    public static void main(String[] args){
        List<User> users = new ArrayList<>();

        //Format: [String:userId, String:movieId, Double:Rating]
        Map<String, Map<String, Double>> RatingDataList = dataReader.readRatingData("/Users/alexzheng/Documents/RecommenderProject/src/com/recommender/data/ml-latest-small/ratings.csv");
        //Format: [String:movieId, String:movieName, String:genres]
        Map<String, Map<String, String>> MovieList = dataReader.readMovieData("/Users/alexzheng/Documents/RecommenderProject/src/com/recommender/data/ml-latest-small/movies.csv");



        //display
        for(int i = 1;i<RatingDataList.size();i++){
            String userId = String.valueOf(i);
            User currentUser = new User(userId);
            users.add(currentUser);
            for (Map.Entry<String, Double> stringDoubleEntry : RatingDataList.get(userId).entrySet()) {
                String key = (String) ((Map.Entry) stringDoubleEntry).getKey();
                Double value = (Double) ((Map.Entry) stringDoubleEntry).getValue();
                for(String name:MovieList.get(key).keySet()) {
                    currentUser.set(key,name,value);
                }


            }
        }



        Recommender recommender = new Recommender();
        System.out.println("请输入用户ID:(最大为"+RatingDataList.size()+"):");
        var sc=new Scanner(System.in);
        String id=sc.nextLine();
        List<Movie> recommendationMovies = recommender.recommend(id, users);
        System.out.println("-----------------------");
        System.out.println("推荐结果如下：");
        int counter = 0;
        String name = "";
        String genres = "";
        for (Movie movie : recommendationMovies) {
            if(movie.rating>=4.0&&counter<10){
                for(String key:MovieList.get(movie.movieId).keySet()) {
                    name = key;
                }
                for(String value:MovieList.get(movie.movieId).values()){
                    genres = value;
                }
                System.out.println("电影：" + name);
                System.out.println("种类: " + genres);
                System.out.println("评分: "+movie.rating);
                System.out.println();
                counter++;
            }
        }
        System.out.println("-----------------------");
    }
}
