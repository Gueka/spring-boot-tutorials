package net.gueka.rules.definition;

import java.util.Collections;
import java.util.List;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;
import org.jeasy.rules.api.Facts;

import net.gueka.rules.service.PromoService;

@Rule(name = "master-name", description = "If the rule has same name as my master it has 5% discount" )
public class MasterNameRule {

	private static final String MARCO = "marco";
    private static final String MESSAGE = "You got a 5% discount for your awesome name";

    @Condition
    public boolean isAwesome(@Fact(PromoService.FACT_NAME) String name) {
        return MARCO.equals(name.trim().toLowerCase());
    }
    
    @Action
    public void message(Facts facts) {
        List<String> messages = Collections.checkedList(facts.get(PromoService.FACT_MESSAGES), String.class);
        messages.add(MESSAGE);
    }
}