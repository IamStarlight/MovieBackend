package com.bjtu.movie.model;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.bjtu.movie.constants.Gender;
import lombok.Data;

@Data
public class Cast {
    /*
    {'cast_id': 27,
    'character': 'TV Announcer (voice)',
    'credit_id': '52fe4284c3a36847f8024fc5',
    'gender': 2,
    'id': 37221,
    'name': 'Penn Jillette',
    'order': 12,
    'profile_path': '/zmAaXUdx12NRsssgHbk1T31j2x9.jpg'}
     */
    private Integer cast_id;
    private String character;
    private String credit_id;
    @EnumValue
    private Gender gender;
    private Integer id;
    private String name;
    private Integer order;
    private String profile_path;
}
