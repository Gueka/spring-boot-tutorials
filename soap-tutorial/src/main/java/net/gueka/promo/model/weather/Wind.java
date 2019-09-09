package net.gueka.promo.model.weather;

import lombok.Data;

@Data
public class Wind {

    private Double speed;
    private Integer deg;
    private Double gust;

}