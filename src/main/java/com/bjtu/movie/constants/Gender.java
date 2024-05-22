package com.bjtu.movie.constants;

public enum Gender {
    GENDER_MALE(1,"MALE"),

    GENDER_FEMALE(2,"FEMALE");


    private Integer per;
    private String desc;

    Gender(Integer per, String desc){
        this.per = per;
        this.desc = desc;
    }

    public String toString(){
        return desc;
    }

    public Integer getValue() {
        return per;
    }
}
