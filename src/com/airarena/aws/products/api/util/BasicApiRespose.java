package com.airarena.aws.products.api.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BasicApiRespose {
	
	public static Date reviewDate(String date) throws ParseException {
		SimpleDateFormat simpleDateFormat =
	        new SimpleDateFormat("MMMM dd yyyyy");
		
		return simpleDateFormat.parse(date);
	}
	
	public abstract boolean isValidate();

	public static boolean validateString(String source) {
		if (source != null && !source.isEmpty()) return true;
		return false;
	}
	
	public static boolean validateMap(Map hs) {
		if (hs != null && !hs.isEmpty()) return true;
		return false;
	}
	
	public static boolean validateList(List l) {
		if (l != null && !l.isEmpty()) return true;
		return false;		
	}
}
