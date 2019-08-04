package net.gueka.tutorials.weatherapi.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;
import net.gueka.tutorials.weatherapi.model.Weather;

@Slf4j
@Service
@CacheConfig(cacheNames={"weather"})
public class WeatherService {

    public static final String WEATHER_API_URL = "https://api.openweathermap.org/data/2.5/weather?zip=%s,%s&appid=%s&units=metric";

    @Value("${weather.appid}")
    String appid;

    @Autowired
    RestTemplate template;

    @Cacheable
    public Weather getByZip(String code, String country) {
        String URL = String.format(WEATHER_API_URL, code, country, appid);
        try{
            ResponseEntity<Weather> response = template.getForEntity(
                URL,
                Weather.class);
            return response.getBody();
        }catch (RestClientException e){
            log.error("Unable to get whether information for code: " + code, e);
        }
        // If something happend return an empty weather object
        return Weather.builder().build();
    }

    @CacheEvict(allEntries = true, value = {"weather"})
    @Scheduled(fixedDelay = 1000 * 60 * 10)
    public void reportCacheEvict() {
        log.info("Flush Cache " + new Date());
    }

}