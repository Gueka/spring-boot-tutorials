package net.gueka.promo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;
import net.gueka.promo.model.Location;
import net.gueka.promo.model.UserInfo;
import net.gueka.promo.model.validator.ValidatorUser;
import net.gueka.promo.model.weather.Weather;
import net.gueka.promo.model.weather.WeatherView;
import net.gueka.promo.schema.Data;

@Slf4j
@Profile("!test")
@Service
public class OfferServiceImpl implements OfferService {

    @Value("${user.service.url}")
    String userURL;

    @Value("${weather.service.url}")
    String weatherURL;

    @Value("${validator.service.url}")
    String rulesURL;

    RestTemplate template = new RestTemplate();

    public List<String> getOffers(Data data) {

        UserInfo userInfo = getUserInfo(data);
        Weather currentWeather = getWeather(userInfo.getLocation());
        userInfo.setCurrentWeather(currentWeather);
        List<String> offers = getRulesApplied(getValidatorUser(userInfo));
        
		return offers;
    }

    private ValidatorUser getValidatorUser(UserInfo user) {
        return ValidatorUser.builder()
            .surname(user.getSurname())
            .currentWeather(
                CollectionUtils.isEmpty(user.getCurrentWeather().getWeather()) ? 
                new WeatherView() : 
                user.getCurrentWeather().getWeather().get(0) )
            .name(user.getName())
            .userId(user.getId().toString())
            .initDate(user.getInitDate())
            .build();
    }

    private List<String> getRulesApplied(ValidatorUser ValidatorUser) {
        HttpEntity<ValidatorUser> entity = new HttpEntity<ValidatorUser>(ValidatorUser);
        ResponseEntity<List<String>> response = template.exchange(
            rulesURL,
            HttpMethod.POST, entity, new ParameterizedTypeReference<List<String>>() {
            });
        return response.getBody();
    }

    private Weather getWeather(Location location) {
        log.info("Requesting weather info for zipcode: {}", location.getZipCode());
        try {
            ResponseEntity<Weather> response = template.exchange(
                weatherURL + "?code=" + location.getZipCode() + "&country=" + location.getCountry(),
                HttpMethod.GET, null, new ParameterizedTypeReference<Weather>() {});
            return response.getBody();
        }catch(Exception e){
            log.warn("No weather info where found for zipcode: {}", location.getZipCode());
        }
        return Weather.builder().build();
    }

    private UserInfo getUserInfo(Data data) {
        log.info("Requesting user info for id: {}", data.getUserId());
        ResponseEntity<UserInfo> response = template.exchange(
                userURL + "?id=" + data.getUserId(),
				HttpMethod.GET, null, new ParameterizedTypeReference<UserInfo>() {});
        log.info("Response received. User id: {}", response.getBody().getId());
        return response.getBody();
    }
    
}