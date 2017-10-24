package com.example.zhuocong.comxzc9.entity.yingxun;

import java.util.List;

/**
 * Created by wunaifu on 2017/9/19.
 */
public class MoviesMovies {

    String movie_name;
    String movie_type;
    String movie_nation;
    String movie_director;
    String movie_starring;
    String movie_release_dat;
    String movie_picture;
    String movie_length;
    String movie_description;
    String movie_score;
    String movie_message;
    String movie_tags;
    List<TimeTable> time_table;

    public String getMovie_name() {
        return movie_name;
    }

    public void setMovie_name(String movie_name) {
        this.movie_name = movie_name;
    }

    public String getMovie_type() {
        return movie_type;
    }

    public void setMovie_type(String movie_type) {
        this.movie_type = movie_type;
    }

    public String getMovie_nation() {
        return movie_nation;
    }

    public void setMovie_nation(String movie_nation) {
        this.movie_nation = movie_nation;
    }

    public String getMovie_director() {
        return movie_director;
    }

    public void setMovie_director(String movie_director) {
        this.movie_director = movie_director;
    }

    public String getMovie_starring() {
        return movie_starring;
    }

    public void setMovie_starring(String movie_starring) {
        this.movie_starring = movie_starring;
    }

    public String getMovie_release_dat() {
        return movie_release_dat;
    }

    public void setMovie_release_dat(String movie_release_dat) {
        this.movie_release_dat = movie_release_dat;
    }

    public String getMovie_picture() {
        return movie_picture;
    }

    public void setMovie_picture(String movie_picture) {
        this.movie_picture = movie_picture;
    }

    public String getMovie_length() {
        return movie_length;
    }

    public void setMovie_length(String movie_length) {
        this.movie_length = movie_length;
    }

    public String getMovie_description() {
        return movie_description;
    }

    public void setMovie_description(String movie_description) {
        this.movie_description = movie_description;
    }

    public String getMovie_score() {
        return movie_score;
    }

    public void setMovie_score(String movie_score) {
        this.movie_score = movie_score;
    }

    public String getMovie_message() {
        return movie_message;
    }

    public void setMovie_message(String movie_message) {
        this.movie_message = movie_message;
    }

    public String getMovie_tags() {
        return movie_tags;
    }

    public void setMovie_tags(String movie_tags) {
        this.movie_tags = movie_tags;
    }

    public List<TimeTable> getTime_table() {
        return time_table;
    }

    public void setTime_table(List<TimeTable> time_table) {
        this.time_table = time_table;
    }

    @Override
    public String toString() {
        return "MoviesMovies{" +
                "movie_name='" + movie_name + '\'' +
                ", movie_type='" + movie_type + '\'' +
                ", movie_nation='" + movie_nation + '\'' +
                ", movie_director='" + movie_director + '\'' +
                ", movie_starring='" + movie_starring + '\'' +
                ", movie_release_dat='" + movie_release_dat + '\'' +
                ", movie_picture='" + movie_picture + '\'' +
                ", movie_length='" + movie_length + '\'' +
                ", movie_description='" + movie_description + '\'' +
                ", movie_score='" + movie_score + '\'' +
                ", movie_message='" + movie_message + '\'' +
                ", movie_tags='" + movie_tags + '\'' +
                ", time_table=" + time_table +
                '}';
    }
}
