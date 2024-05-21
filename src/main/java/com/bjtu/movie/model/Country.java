package com.bjtu.movie.model;

import lombok.Data;

@Data
public class Country {
    /*
    [{"name": "United States of America",
    "iso_3166_1": "US"}]
     */
    private String name;
    private String iso31661;
}
