package net.gueka.rules.definition;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class MasterNameRuleTest {

    @ParameterizedTest
    @ValueSource(strings = { "Marco", "marco", "MARCO", "mArCo", " marco", "mArco ", " MARCO " })
    public void testValidMasterName(String name) throws Exception {
        // Given
        MasterNameRule rule = new MasterNameRule();

        // When
        Boolean result = rule.isAwesome(name);

        // Then
        assertTrue(result);
    }

    @ParameterizedTest
    @ValueSource(strings = { "Peter", " Ann", "keishon", " gueka", "Juan", " javier ", " PABLO" })
    public void testInvalidMasterName(String name) throws Exception {
        // Given
        MasterNameRule rule = new MasterNameRule();

        // When
        Boolean result = rule.isAwesome(name);

        // Then
        assertFalse(result);
    }
}