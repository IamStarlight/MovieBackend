package com.bjtu.movie.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Language implements Serializable {
    /*
    [{"name": "English",
    "iso_639_1": "en"}]
     */
    private String name;
    private String iso6391;
}
