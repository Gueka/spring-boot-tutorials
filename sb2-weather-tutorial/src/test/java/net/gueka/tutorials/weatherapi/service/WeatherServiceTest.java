package net.gueka.tutorials.weatherapi.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import net.gueka.tutorials.weatherapi.model.Weather;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class WeatherServiceTest {

	@Mock
	RestTemplate template;
	
	@InjectMocks
	WeatherService service = new WeatherService();

	@Test
    public void getWeather() throws Exception {
		Weather weather = Weather.builder().name("test").build();
        Mockito
          .when(template.getForEntity(
			String.format(WeatherService.WEATHER_API_URL, "000", "us", "null"),
			Weather.class))
          .thenReturn(new ResponseEntity<Weather>(weather, HttpStatus.OK));

		Weather response = service.getByZip("000", "us");

		assertTrue(response.getName() == "test", "Has to return at leas a message");
    }

}
