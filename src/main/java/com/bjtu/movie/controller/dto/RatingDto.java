package com.bjtu.movie.controller.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.Data;

@Data
public class RatingDto {
    private Integer userId;
    private Integer movieId;
    @DecimalMin("0.0") @DecimalMax("10.0")
    private Double rating;
}
