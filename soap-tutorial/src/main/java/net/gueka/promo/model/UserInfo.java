package net.gueka.promo.model;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.gueka.promo.model.weather.Weather;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {

    UUID id;
    String name;
    String surname;
    Date initDate;

    List<String> tags;

    Location location;

    Weather currentWeather;

}