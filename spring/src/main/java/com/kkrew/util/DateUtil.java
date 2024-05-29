package com.kkrew.util;

import java.sql.Date;
import java.util.Calendar;
import java.util.TimeZone;

import org.springframework.stereotype.Component;

@Component
public class DateUtil {
	
	public Date getStartDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
		
		return new Date(calendar.getTimeInMillis());
	}
	
	public Date getEndDate(int period) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
		
		calendar.add(Calendar.DATE, period);
		
		return new Date(calendar.getTimeInMillis());
	}
}
