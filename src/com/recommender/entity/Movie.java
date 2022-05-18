package com.recommender.entity;

import java.util.Objects;

public class Movie implements Comparable<Movie> {
    public String movieName;
    public String movieId;
    public Double rating;
    public Movie(String movieName, Double rating) {
        this.movieName = movieName;
        this.rating = rating;
    }
@Override
    public String toString() {
        return "Movie{" +
        "movieName='" + movieName + '\'' +
        ", rating=" + rating +
        '}';
    }

@Override
//注意jdk7以上的sort三个要求：自反、传递、对称
    public int compareTo(Movie o) {
        //相等时必须返回0否则报contract error
        if(Objects.equals(rating, o.rating)) return 0;
        return rating > o.rating ? -1 : 1;
    }
}
