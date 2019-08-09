package net.gueka.tutorials.weatherapi.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import net.gueka.tutorials.weatherapi.model.Weather;

@SpringBootTest
public class WeatherServiceTest {

	@Mock
	RestTemplate template;
	
	@InjectMocks
	WeatherService service = new WeatherService();

	@DisplayName("Should mock cp value and get the test weather everytime.")
	@ParameterizedTest
    @ValueSource(strings = { "000", "12345", "a", "1234567890987654321" })
    public void getWeather(String cp) throws Exception {
		Weather weather = Weather.builder().name("test").build();
        Mockito
          .when(template.getForEntity(
			String.format(WeatherService.WEATHER_API_URL, cp, "", "null"),
			Weather.class))
          .thenReturn(new ResponseEntity<Weather>(weather, HttpStatus.OK));

		Weather response = service.getByZip(cp, "");

		assertTrue(response.getName() == "test", "Has to return at leas a message");
    }

}
