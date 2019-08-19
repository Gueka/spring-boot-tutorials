package net.gueka.rules.listener;

import java.util.Collections;
import java.util.List;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngineListener;

import lombok.Data;
import net.gueka.rules.service.PromoService;

@Data
public class MessageEngineListener implements RulesEngineListener {

    private List<String> messages;

    @Override
    public void beforeEvaluate(Rules rules, Facts facts) {
        // NTD
    }

    @Override
    public void afterExecute(Rules rules, Facts facts) {
        messages = Collections.checkedList(facts.get(PromoService.FACT_MESSAGES), String.class);
    }

}