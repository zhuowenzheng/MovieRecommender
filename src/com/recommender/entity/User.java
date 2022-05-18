package com.recommender.entity;

import java.util.ArrayList;
import java.util.List;

//User class
public class User {
    public String userId;
    public List<Movie> movieList = new ArrayList<>();

    //构造函数
    public User(){}

    public User(String userId){
        this.userId = userId;
    }

    public User set(String movieId, String Name, Double rating){
        this.movieList.add(new Movie(movieId,Name,rating));
        return this;
    }

    //查找
    public Movie find(String movieId){
        for(Movie movie: movieList){
            if(movie.movieId.equals(userId)){
                return movie;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                '}';
    }


}
