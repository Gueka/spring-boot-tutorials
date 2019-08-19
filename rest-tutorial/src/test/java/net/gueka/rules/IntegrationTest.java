package net.gueka.rules;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesPattern;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import net.gueka.rules.model.UserInfo;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class IntegrationTest {

	private static final String NEW_USER_REGEX_MESSAGE = "^[0-9]{1,3} days left with 20% discount";

	TestRestTemplate restTemplate = new TestRestTemplate();
	
	@LocalServerPort
	int port;
    
    @Test
    public void integrationTest() throws Exception {
       
        HttpEntity<UserInfo> entity = new HttpEntity<UserInfo>(getRequest());

		ResponseEntity<List<String>> response = restTemplate.exchange(
				"http://localhost:" + port + "/validate/promo",
				HttpMethod.POST, entity, new ParameterizedTypeReference<List<String>>() {
				});

		assertTrue("Has to return at leas a message", response.getBody().size() > 0);
        assertThat("Has to return first message with regex: " + NEW_USER_REGEX_MESSAGE, response.getBody().get(0),
                matchesPattern(NEW_USER_REGEX_MESSAGE));
        assertEquals("Has only 1 message", 1, response.getBody().size());
    }

    private UserInfo getRequest() {
		return UserInfo.builder()
			.userId(UUID.randomUUID().toString())
			.initDate(new Date())
			.build();
    }
}