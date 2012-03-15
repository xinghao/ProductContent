package com.airarena.aws.products.api.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BasicApiRespose {
	
	public static Date reviewDate(String date) throws ParseException {
		SimpleDateFormat simpleDateFormat =
	        new SimpleDateFormat("MMMM dd yyyyy");
		
		return simpleDateFormat.parse(date);
	}
	

}
