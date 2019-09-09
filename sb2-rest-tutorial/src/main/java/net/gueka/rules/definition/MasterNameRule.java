package net.gueka.rules.definition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;
import org.jeasy.rules.api.Facts;
import org.mockito.internal.matchers.Matches;

import lombok.Builder;
import lombok.Data;
import net.gueka.rules.service.PromoService;

@Rule(name = "master-name", description = "If the rule has same name as my master" )
public class MasterNameRule {

	private static final String MARCO = "marco";
    private static final String MESSAGE = ;

    private List<ValidName> awesomeNameList;

    public MasterNameRule() {
        awesomeNameList = new ArrayList();

        awesomeNameList.add(ValidName.builder()
            .id("1").message("Nice name, you have the same name as my master/creator.").pattern(Pattern.compile(".*marco*", Pattern.CASE_INSENSITIVE))
            .build());

        awesomeNameList.add(ValidName.builder()
            .id("2").message("You look like Batman,").pattern(Pattern.compile(".*bruce*wayne*"))
            .build());
    }

    @Condition
    public boolean isValid(@Fact(PromoService.FACT_NAME) String name) {
        return matchValidName(name) != null;
    }

    private ValidName matchValidName(String name){
        for (ValidName validName : awesomeNameList) {
            if(validName.pattern.matcher(name).matches()){
                return validName;
            }
        }
        return null;
    }
    
    @Action
    public void message(Facts facts) {
        List<String> messages = Collections.checkedList(facts.get(PromoService.FACT_MESSAGES), String.class);
        ValidName validName = match
        messages.add(MESSAGE);
    }

    @Data
    @Builder
    private class ValidName {
        String id;
        Pattern pattern;
        String message;
        
    }
}