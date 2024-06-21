package com.bjtu.movie.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Country implements Serializable {
    /*
    [{"name": "United States of America",
    "iso_3166_1": "US"}]
     */
    private String name;
    private String iso31661;
}
