package net.gueka.promo.model.validator;

import java.util.Calendar;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import net.gueka.promo.model.weather.Weather;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidatorUser {
    String userId;
    String name;
    String surname;
    Date initDate;
    Calendar calendarDate;
    Weather currentWeather;
}