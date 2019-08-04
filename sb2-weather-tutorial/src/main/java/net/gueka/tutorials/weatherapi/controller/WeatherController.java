package net.gueka.tutorials.weatherapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import net.gueka.tutorials.weatherapi.model.Weather;
import net.gueka.tutorials.weatherapi.service.WeatherService;

@Slf4j
@RestController("weather")
public class WeatherController {

    @Autowired
    WeatherService service;

    @GetMapping
    public ResponseEntity<Weather> getWeatherByZip(@RequestParam String code, @RequestParam String country){
        log.info("Requesting weather for zip {} and country {}", code, country);
        Weather weather = service.getByZip(code, country);
        if(weather.getMain() == null){
            return new ResponseEntity<Weather>(
                weather, 
                HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Weather>(
            weather, 
            HttpStatus.OK);
    }

}