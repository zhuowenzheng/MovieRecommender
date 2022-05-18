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

    public User set(String movieName, Double score){
        this.movieList.add(new Movie(movieName,score));
        return this;
    }
    //查找
    public Movie find(String movieName){
        for(Movie movie: movieList){
            if(movie.movieName.equals(userId)){
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
