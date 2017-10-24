package com.example.zhuocong.comxzc9.entity.yingxun;

import java.util.List;

/**
 * Created by wunaifu on 2017/9/19.
 */
public class ResultMovies {

    String uid;
    String name;
    String telephone;
    String address;
    String rating;
    LocationMovies location;
    List<ReviewMovies> review;
    List<MoviesMovies> movies;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public LocationMovies getLocation() {
        return location;
    }

    public void setLocation(LocationMovies location) {
        this.location = location;
    }

    public List<ReviewMovies> getReview() {
        return review;
    }

    public void setReview(List<ReviewMovies> review) {
        this.review = review;
    }

    public List<MoviesMovies> getMovies() {
        return movies;
    }

    public void setMovies(List<MoviesMovies> movies) {
        this.movies = movies;
    }

    @Override
    public String toString() {
        return "ResultMovies{" +
                "uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", telephone='" + telephone + '\'' +
                ", address='" + address + '\'' +
                ", rating='" + rating + '\'' +
                ", location=" + location +
                ", review=" + review +
                ", movies=" + movies +
                '}';
    }
}
