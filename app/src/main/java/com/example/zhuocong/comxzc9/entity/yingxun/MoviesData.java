package com.example.zhuocong.comxzc9.entity.yingxun;

import java.util.List;

/**
 * Created by wunaifu on 2017/9/19.
 */
public class MoviesData {

    List<ResultMovies> result;
    int error_code;
    String reason;

    public List<ResultMovies> getResult() {
        return result;
    }

    public void setResult(List<ResultMovies> result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "MoviesData{" +
                "result=" + result +
                ", error_code=" + error_code +
                ", reason='" + reason + '\'' +
                '}';
    }
}
