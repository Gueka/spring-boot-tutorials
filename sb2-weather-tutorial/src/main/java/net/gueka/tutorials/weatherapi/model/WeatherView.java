package net.gueka.tutorials.weatherapi.model;

import lombok.Data;

@Data
public class WeatherView {

    private Integer id;
    private String main;
    private String description;
    private String icon;
}