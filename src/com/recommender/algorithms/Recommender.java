package com.recommender.algorithms;
import com.recommender.entity.Movie;
import com.recommender.entity.User;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.Collections;

import static java.lang.Math.min;


public class Recommender {
    /** 在给定userId的情况下，计算其他用户和它的距离并排序
     * @param userId
     * @return
     */
    private Map<Double, String> computeNearestNeighbor(String userId, List<User> users) {
        Map<Double, String> distances = new TreeMap<>();

        User u1 = new User(userId);
        for (User user:users) {
            if (userId.equals(user.userId)) {
                u1 = user;
            }
        }

        for (User u2 : users) {
            if (!u2.userId.equals(userId)) {
                double distance = pearson_dis(u2.movieList, u1.movieList);
                distances.put(distance, u2.userId);
                //System.out.println("call method:userId:"+u2.userId + "distance:"+ distance );
            }

        }
        System.out.println("该用户与其他用户的皮尔森相关系数 -> " + distances);
        return distances;
    }


    /**
     * 计算2个打分序列间的pearson距离
     * 选择公式四进行计算
     * @param rating1
     * @param rating2
     * @return
     */
    private double pearson_dis(List<Movie> rating1, List<Movie> rating2) {
        //需要统一标准

        int n=min(rating1.size(), rating2.size());

        List<Double> rating1ScoreCollect = rating1.stream().map(A -> A.rating).collect(Collectors.toList());
        List<Double> rating2ScoreCollect = rating2.stream().map(A -> A.rating).collect(Collectors.toList());

        rating1ScoreCollect.sort(Collections.reverseOrder());
        rating2ScoreCollect.sort(Collections.reverseOrder());

        System.out.println(rating1ScoreCollect);
        System.out.println(rating2ScoreCollect);
        System.out.println();


        double Ex= rating1ScoreCollect.stream().mapToDouble(x->x).sum();
        double Ey= rating2ScoreCollect.stream().mapToDouble(y->y).sum();
        double Ex2=rating1ScoreCollect.stream().mapToDouble(x->Math.pow(x,2)).sum();
        double Ey2=rating2ScoreCollect.stream().mapToDouble(y->Math.pow(y,2)).sum();
        double Exy= IntStream.range(0,n).mapToDouble(i->rating1ScoreCollect.subList(0,n).get(i)*rating2ScoreCollect.subList(0,n).get(i)).sum();
        double numerator=Exy-Ex*Ey/n;
        double denominator=Math.sqrt((Ex2-Math.pow(Ex,2)/n)*(Ey2-Math.pow(Ey,2)/n));

        //System.out.println("Ex, Ey, Ex2, Ey2, Exy, numerator, denominator"+Ex+" "+Ey+" "+Ex2+" "+Ey2+" "+Exy+" "+numerator+" "+denominator);

        if (denominator==0||n==0) return 0.0;
        return numerator/denominator;
    }


    public List<Movie> recommend(String userId, List<User> users) {
        //找到最近邻
        Map<Double, String> distances = computeNearestNeighbor(userId, users);
        String nearest = distances.values().iterator().next();
        System.out.println("最近邻 -> " + nearest);

        //找到最近邻看过，但是我们没看过的电影，计算推荐
        User neighborRatings = new User();
        for (User user:users) {
            if (nearest.equals(user.userId)) {
                neighborRatings = user;
            }
        }
        System.out.println("最近邻看过的电影 -> " + neighborRatings.movieList);

        User userRatings = new User();
        for (User user:users) {
            if (userId.equals(user.userId)) {
                userRatings = user;
            }
        }
        System.out.println("用户看过的电影 -> " + userRatings.movieList);

        //根据自己和邻居的电影计算推荐的电影
        List<Movie> recommendationMovies = new ArrayList<>();
        for (Movie movie : neighborRatings.movieList) {
            if (userRatings.find(movie.movieName) == null) {
                recommendationMovies.add(movie);
            }
        }
        Collections.sort(recommendationMovies);
        return recommendationMovies;
    }
}
