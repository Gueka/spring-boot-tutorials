package net.gueka.promo.model.weather;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Main {

    private Double temp;
    private Integer pressure;
    private Integer humidity;
    @JsonProperty("temp_min")
    private Double tempMin;
    @JsonProperty("temp_max")
    private Double tempMax;

}