package net.gueka.promo.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesPattern;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import net.gueka.promo.schema.Data;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class OfferServiceTest {

    private static final String NEW_USER_REGEX_MESSAGE = "^[0-9]{1,3} days left with 20% discount";
    
    @Autowired
    OfferService service;

    @ParameterizedTest
    @ValueSource(ints = { -30, 0, -61, -91, -122, -152, -180 })
    public void testNewUserPromotion(Integer days) throws Exception {
        // Given
        Data data = generateDummyRequest(days);

        // When
        List<String> offers = service.getOffers(data);

        // Then
        assertTrue("Has to return at leas a message", offers.size() > 0);
        assertThat("Has to return first message with regex: " + NEW_USER_REGEX_MESSAGE, offers.get(0),
                matchesPattern(NEW_USER_REGEX_MESSAGE));
        assertEquals("Has only 1 message", 1, offers.size());
    }

    @ParameterizedTest
    @ValueSource(ints = {-214, -284, -458, -762, 61})
    public void testNoPromotion(Integer days) throws Exception {
        // Given
        Data data = generateDummyRequest(days);
        
        // When
        List<String> offers = service.getOffers(data);
        
        // Then
        assertEquals("Shouldn't have any promotion message", 0, offers.size());
    }

    @ParameterizedTest
    @ValueSource(ints = { -356, -714, -1070, -1436 })
    public void testMemberNewYear(Integer days) throws Exception {
        // Given
        Data data = generateDummyRequest(days);
        
        // When
        List<String> offers = service.getOffers(data);
        
        // Then
        String message = OfferServiceMock.NEW_YEAR_MEMBER_DISCOUNT_MESSAGE;
        assertTrue("Has to return at leas a message", offers.size() > 0);
        assertEquals("Has to return first message as " + message, message, offers.get(0));
        assertEquals("Has only 1 message", 1, offers.size());
    }

    private Data generateDummyRequest(Integer days) throws DatatypeConfigurationException {
        GregorianCalendar gcal = new GregorianCalendar();
        gcal.add(Calendar.DAY_OF_YEAR, days);
        XMLGregorianCalendar date = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
        Data data = new Data();
        data.setUserId("007");
        data.setInitDate(date);
        return data;
    }

}