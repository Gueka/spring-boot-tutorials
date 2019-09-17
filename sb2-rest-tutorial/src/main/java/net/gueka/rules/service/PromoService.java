package net.gueka.rules.service;

import java.util.ArrayList;
import java.util.List;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;
import net.gueka.rules.listener.MessageEngineListener;
import net.gueka.rules.model.UserInfo;

@Slf4j
@Service
public class PromoService {

    public static final String FACT_MESSAGES = "messages";
    public static final String FACT_INIT_DATE = "initDate";
    public static final String FACT_NAME = "name";
    public static final String FACT_WEATHER_CODE = "weather";

    @Autowired
    @Qualifier("dateRules")
    Rules dateRules;

    @Autowired
    @Qualifier("nameRules")
    Rules nameRules;

    @Autowired
    @Qualifier("weatherRules")
    Rules weatherRules;

    public List<String> getMessages(UserInfo info) {
        DefaultRulesEngine rulesEngine = new DefaultRulesEngine();
        MessageEngineListener listener = new MessageEngineListener();
        rulesEngine.registerRulesEngineListener(listener);
        
        log.info("Firing rules for user: {}", info.getUserId());
        Facts facts = getFacts(info);
        rulesEngine.fire(dateRules, facts);
        rulesEngine.fire(nameRules, facts);
        rulesEngine.fire(weatherRules, facts);

        return listener.getMessages();
    }

    public Facts getFacts(UserInfo info) {
        Facts facts = new Facts();
        facts.put(FACT_MESSAGES, new ArrayList<String>());
        if(info.getInitDate() != null){
            facts.put(FACT_INIT_DATE, info.getInitDate());
        }
        if(!StringUtils.isEmpty(info.getName())){
            facts.put(FACT_NAME, info.getName());
        }
        if(!StringUtils.isEmpty(info.getCurrentWeather())){
            facts.put(FACT_WEATHER_CODE, info.getCurrentWeather());
        }
        return facts;
    }
}