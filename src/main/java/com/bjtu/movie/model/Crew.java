package com.bjtu.movie.model;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.bjtu.movie.constants.Gender;
import lombok.Data;

@Data
public class Crew {
    /*
    {'credit_id': '52fe4284c3a36847f8024f49',
    'department': 'Directing',
    'gender': 2,
    'id': 7879,
    'job': 'Director',
    'name': 'John Lasseter',
    'profile_path': '/7EdqiNbr4FRjIhKHyPPdFfEEEFG.jpg'}
     */
    private String credit_id;
    private String department;
    @EnumValue
    private Gender gender;
    private Integer id;
    private String job;
    private String name;
    private String profile_path;
}
