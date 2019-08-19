package net.gueka.rules.definition;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class NewUserRuleTest {

    @ParameterizedTest
    @ValueSource(ints = { -30, 0, -61, -91, -122, -152, -180 })
    public void testValidNewUser(Integer days) throws Exception {

        // Given
        Date date = getDate(days);
        NewUserRule rule = new NewUserRule();

        // When
        Boolean result = rule.isNewUser(date);

        // Then
        assertTrue(result);
    }

    @ParameterizedTest
    @ValueSource(ints = {-214, 1, -186, -762, 61})
    public void testInvalidNewUser(Integer days) throws Exception {

        // Given
        Date date = getDate(days);
        NewUserRule rule = new NewUserRule();

        // When
        Boolean result = rule.isNewUser(date);

        // Then
        assertFalse(result);
    }
    
    private Date getDate(Integer days) throws DatatypeConfigurationException {
        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.DAY_OF_YEAR, days);
        return cal.getTime();
    }
    
}