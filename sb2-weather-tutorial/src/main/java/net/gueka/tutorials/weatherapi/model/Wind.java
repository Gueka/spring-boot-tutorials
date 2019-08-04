package net.gueka.tutorials.weatherapi.model;

import lombok.Data;

@Data
public class Wind {

    private Double speed;
    private Integer deg;
    private Double gust;

}