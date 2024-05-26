package com.bjtu.movie.constants;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum Gender {

    GENDER_FEMALE(1,"FEMALE"),
    GENDER_MALE(2,"MALE");

    @EnumValue
    private final Integer value;
    private final String desc;

    Gender(Integer value, String desc){
        this.value = value;
        this.desc = desc;
    }

    @Override
    public String toString(){
        return desc;
    }
}
