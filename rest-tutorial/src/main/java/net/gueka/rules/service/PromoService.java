package net.gueka.rules.service;

import java.util.ArrayList;
import java.util.List;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.springframework.stereotype.Service;

import net.gueka.rules.definition.MasterNameRule;
import net.gueka.rules.definition.NewUserRule;
import net.gueka.rules.definition.YearRule;
import net.gueka.rules.listener.MessageEngineListener;
import net.gueka.rules.model.UserInfo;

@Service
public class PromoService {

    public static final String FACT_MESSAGES = "messages";
    public static final String FACT_INIT_DATE = "initDate";
    public static final String FACT_NAME = "name";

    public List<String> getMessages(UserInfo info){
        DefaultRulesEngine rulesEngine = new DefaultRulesEngine();
        MessageEngineListener listener = new MessageEngineListener();
        rulesEngine.registerRulesEngineListener(listener);
        rulesEngine.fire(getRules(), getFacts(info));
        return listener.getMessages();
    }

    public Rules getRules() {
        Rules rules = new Rules();
        rules.register(new MasterNameRule());
        rules.register(new YearRule());
        rules.register(new NewUserRule());
        return rules;
    }

    public Facts getFacts(UserInfo info) {
        Facts facts = new Facts();
        facts.put(FACT_MESSAGES, new ArrayList<String>());
        facts.put(FACT_INIT_DATE, info.getInitDate());
        facts.put(FACT_NAME, info.getName());
        return facts;
    }
}