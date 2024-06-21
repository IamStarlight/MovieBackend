package com.bjtu.movie.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Info implements Serializable {
    /*
    {"id": 10738,
    "name": "diner"}
     */
    private Integer id;
    private String name;
}
