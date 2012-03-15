package com.airarena.product.test;

import com.airarena.aws.products.api.util.BasicApiRequest;
import com.airarena.aws.products.api.util.BrowserNodeLookupRequest;
import com.airarena.aws.products.api.util.BrowserNodelLookupResponse;
import com.airarena.aws.products.api.util.ItemLookupRequest;
import com.airarena.aws.products.api.util.ItemLookupResponse;
import com.airarena.aws.products.api.util.ItemSearchRequest;
import com.airarena.aws.products.api.util.ItemSearchResponse;

public class TestItemLookUp {
	private static String[] sourceObjectIdList = {"B004N866SU"};//"B004LDG9IO", "B004N866SU"}; 
	private static String[] categoryIdList = {"493964"};//"B004LDG9IO", "B004N866SU"};
	private static String[] browseIdList = {"172532"};
	
	
	public static boolean test() {
		
		
		
		boolean success = true;
		
		
		//validate category parsing code
		for (String browserId : browseIdList) {
			BasicApiRequest bar = new BrowserNodeLookupRequest(browserId);
			BrowserNodelLookupResponse b = (BrowserNodelLookupResponse) bar.call();
			success = b.isValidate();
		}
		// Validate search parsing code
		for (String sourceCategoryId : categoryIdList) {
			BasicApiRequest itms = new ItemSearchRequest(sourceCategoryId, BasicApiRequest.AWS_SEARCHINDEX_ELECTRONICS, 1, null, 1L);
			ItemSearchResponse itsr = (ItemSearchResponse)itms.call();
			success = itsr.isValidate();
		}
		
		
		// Validate lookup response parsing code
		for (String sourceObjectId : sourceObjectIdList) {
			BasicApiRequest itml = new ItemLookupRequest(sourceObjectId, 1, 1);
			ItemLookupResponse ilrb = (ItemLookupResponse) itml.call();
			//ilrb.setCategory();
			System.out.println(ilrb);
			
			success = ilrb.isValidate();
		}
		
		return success;
	}
	
//	private boolean testTechnicalDetail(String sourceObjectId) {
//		
//	}
}
