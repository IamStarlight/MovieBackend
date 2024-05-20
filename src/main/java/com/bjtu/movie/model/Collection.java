package com.bjtu.movie.model;

import lombok.Data;

@Data
public class Collection {
    /*
    {"id": 10194,
    "name": "Toy Story Collection",
    "poster_path": "/7G9915LfUQ2lVfwMEEhDsn3kT4B.jpg",
    "backdrop_path": "/9FBwqcd9IRruEDUrTdcaafOMKUq.jpg"}
     */
    private Integer id;
    private String name;
    private String posterPath;
    private String backdropPath;
}
