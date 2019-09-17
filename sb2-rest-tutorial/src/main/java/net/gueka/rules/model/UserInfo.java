package net.gueka.rules.model;

import java.util.Calendar;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.gueka.rules.model.weather.WeatherView;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {

    String userId;

    String name;

    String surname;

    Date initDate;

    Calendar calendarDate;

    WeatherView currentWeather;
}