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
        //Map<String, Map<String, String>> MovieList = dataReader.ReadMethod("/Users/alexzheng/Documents/RecommenderProject/src/com/recommender/data/ml-latest-small/movies.csv");
        //System.out.println(RatingDataList);

        //display
        for(int i = 1;i<RatingDataList.size();i++){
            String userId = String.valueOf(i);
            User currentUser = new User(userId);
            users.add(currentUser);
            Iterator iter = RatingDataList.get(userId).entrySet().iterator();
            //System.out.println("----------------");
            while(iter.hasNext()){
                //System.out.println(userId);
                Map.Entry entry = (Map.Entry) iter.next();
                String key = (String) entry.getKey();
                Double value = (Double) entry.getValue();
                //System.out.println("key:"+key+" "+"value:"+value);
                currentUser.set(key,value);
            }
            //System.out.println("uid:"+currentUser.userId+" list:"+currentUser.movieList);
            //System.out.println(Arrays.toString(MovieList.get(i)));
            //ArrayList<>
            //users.add(new User());
        }



        Recommender recommender = new Recommender();
        List<Movie> recommendationMovies = recommender.recommend("1", users);
        System.out.println("-----------------------");
        System.out.println("推荐结果如下：");
        //recommendationMovies.sort(Collections.reverseOrder());
        int counter = 0;
        for (Movie movie : recommendationMovies) {
            if(movie.rating>=4.0&&counter<10){
                System.out.println("电影："+movie.movieName+" ,评分："+movie.rating);
                counter++;
            }
        }
    }
}
