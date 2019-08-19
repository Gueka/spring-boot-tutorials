package net.gueka.rules.definition;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;
import org.jeasy.rules.api.Facts;

import net.gueka.rules.service.PromoService;

@Rule(name = "year", description = "Every year since the user init his account gets a discount")
public class YearRule {

    public static final String NEW_YEAR_MEMBER_DISCOUNT_MESSAGE = "Has 10% new year member discount";
    
    @Condition
    public boolean isUserYear(@Fact(PromoService.FACT_INIT_DATE) Date date) {
        Calendar todayDate = new GregorianCalendar();
        Calendar initDate = dateToCalendar(date);
        
        return initDate.get(Calendar.MONTH) == todayDate.get(Calendar.MONTH) && 
            initDate.get(Calendar.YEAR) != todayDate.get(Calendar.YEAR);
    }

    private Calendar dateToCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }
    
    @Action
    public void share(Facts facts) {
        List<String> messages = Collections.checkedList(facts.get(PromoService.FACT_MESSAGES), String.class);
        messages.add(NEW_YEAR_MEMBER_DISCOUNT_MESSAGE);
    }
}