package net.gueka.promo.endpoint;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class ServiceEndpointTest {

    TestRestTemplate restTemplate = new TestRestTemplate();
    
    @Test
    public void integrationTest() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_XML.toString());
        HttpEntity<String> entity = new HttpEntity<String>(getRequest(), headers);

		ResponseEntity<String> response = restTemplate.exchange(
				"http://localhost:8080/ws",
				HttpMethod.POST, entity, String.class);

        String expect = "promotionResponse";
		assertTrue("Should contain " + expect, response.getBody().contains(expect));
    }

    private String getRequest() {
        StringBuilder builder = new StringBuilder();
        builder.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tns=\"http://www.gueka.net/promo/schema\">");
        builder.append("<soapenv:Header/>");
        builder.append("<soapenv:Body>");
        builder.append("<tns:promotionRequest>");
        builder.append("<tns:data>");
        builder.append("<tns:userId>007</tns:userId>");
        builder.append("<tns:initDate>2019-04-05T09:00:00</tns:initDate>");
        builder.append("</tns:data>");
        builder.append("</tns:promotionRequest>");
        builder.append("</soapenv:Body>");
        builder.append("</soapenv:Envelope>");
        return builder.toString();
    }
}