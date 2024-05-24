package com.bjtu.movie.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class MovieCalender {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private Integer number;
    private String ids;
//    private List<Integer> idList;
    private List<Map<String,Object>> movieList;
}
