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

@Rule(name = "new-user", description = "New user has a " + NewUserRule.DISCOUNT + " discount for " + NewUserRule.NEW_USER_PROMOTION_DURATION + " months")
public class NewUserRule {

	public static final int NEW_USER_PROMOTION_DURATION = 6;
    public static final String DISCOUNT = "20%";
    
    private static final String NEW_USER_DISCOUNT_MESSAGE = "%d days left with %s discount";

    Calendar todayDate = new GregorianCalendar();
    Calendar maxDate;
    
    @Condition
    public boolean isNewUser(@Fact(PromoService.FACT_INIT_DATE) Date date) {
        
        Calendar initDate = dateToCalendar(date);
        maxDate = (Calendar) dateToCalendar(date);
        maxDate.add(Calendar.MONTH, NEW_USER_PROMOTION_DURATION);

        return todayDate.equals(initDate) || todayDate.after(initDate) && maxDate.after(todayDate);
    }

    private Calendar dateToCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    @Action
    public void message(Facts facts) {
        List<String> messages = Collections.checkedList(facts.get(PromoService.FACT_MESSAGES), String.class);

        long diff = maxDate.getTime().getTime() - todayDate.getTime().getTime();
        long days = diff / (1000 * 60 * 60 * 24);

        messages.add(String.format(NEW_USER_DISCOUNT_MESSAGE, days, DISCOUNT));
    }
    
}