package net.gueka.rules.config;

import java.io.InputStream;
import java.io.InputStreamReader;

import org.jeasy.rules.api.Rules;
import org.jeasy.rules.mvel.MVELRuleFactory;
import org.jeasy.rules.support.YamlRuleDefinitionReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import net.gueka.rules.definition.NewUserRule;
import net.gueka.rules.definition.YearRule;

@Configuration
public class Config {
    
    @Bean
    public Rules dateRules(){
        Rules rules = new Rules();
        rules.register(new YearRule());
        rules.register(new NewUserRule());
        return rules;
    }

    @Bean
    public Rules nameRules() throws Exception {
        MVELRuleFactory ruleFactory = new MVELRuleFactory(new YamlRuleDefinitionReader());
        InputStream resource = new ClassPathResource("rules/name.yml").getInputStream();
        return ruleFactory.createRules(new InputStreamReader(resource));
    }

    @Bean
    public Rules weatherRules() throws Exception {
        MVELRuleFactory ruleFactory = new MVELRuleFactory(new YamlRuleDefinitionReader());
        InputStream resource = new ClassPathResource("rules/weather.yml").getInputStream();
        return ruleFactory.createRules(new InputStreamReader(resource));
    }
}