package com.bjtu.movie.utils;

import lombok.SneakyThrows;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {
    public static String getNowTimeString() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(System.currentTimeMillis());
    }

    @SneakyThrows
    public static Date getYearStartDate(String startYear){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
       return df.parse(startYear + "-01-01");
    }

    @SneakyThrows
    public static Date getYearEndDate(String endYear){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.parse(endYear + "-12-31");
    }

}
