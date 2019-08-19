package net.gueka.promo.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.stereotype.Service;

import net.gueka.promo.schema.Data;

@Service
public class OfferService {

    private static final int NEW_USER_PROMOTION_DURATION = 6;
    
	public static final String NEW_YEAR_MEMBER_DISCOUNT_MESSAGE = "Has 10% new year member discount";
	public static final String NEW_USER_DISCOUNT_MESSAGE = "%d days left with %s discount";

    public List<String> getOffers(Data data) {
		List<String> offers = new ArrayList<>();
		hasNewUserPromotion(data, offers);
		hasYearPromotion(data, offers);
		return offers;
	}
	
	private void hasNewUserPromotion(Data data, List<String> offers) {
		Calendar todayDate = new GregorianCalendar();
		Calendar initDate = data.getInitDate().toGregorianCalendar();
		Calendar maxDate = (Calendar) data.getInitDate().toGregorianCalendar().clone();
		maxDate.add(Calendar.MONTH, NEW_USER_PROMOTION_DURATION);
		if (todayDate.after(initDate) && maxDate.after(todayDate)) {
			long diff = maxDate.getTime().getTime() - todayDate.getTime().getTime();
			long days = diff / (1000 * 60 * 60 * 24);
			offers.add(String.format(NEW_USER_DISCOUNT_MESSAGE, days, "20%"));
		}
	}

	private void hasYearPromotion(Data data, List<String> offers) {
		Calendar todayDate = new GregorianCalendar();
        Calendar initDate = data.getInitDate().toGregorianCalendar();
        
        if(initDate.get(Calendar.MONTH) == todayDate.get(Calendar.MONTH) && 
            initDate.get(Calendar.YEAR) != todayDate.get(Calendar.YEAR)){
			offers.add(NEW_YEAR_MEMBER_DISCOUNT_MESSAGE);
		}
	}
}