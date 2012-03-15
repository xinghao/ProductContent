package com.airarena.product.test;

import com.airarena.aws.products.api.util.BasicApiRequest;
import com.airarena.aws.products.api.util.ItemLookupRequest;
import com.airarena.aws.products.api.util.ItemLookupResponse;

public class TestItemLookUp {
	private static String[] sourceObjectIdList = {"B004LDG9IO", "B004N866SU"}; 
	
	
	public static boolean test() {
		boolean success = true;
		for (String sourceObjectId : sourceObjectIdList) {
			BasicApiRequest itml = new ItemLookupRequest(sourceObjectId, 1, 1);
			ItemLookupResponse ilrb = (ItemLookupResponse) itml.call();
			System.out.println(ilrb);
			
			if (ilrb.getTechnicalDetailList().isEmpty()) {
				System.out.println("Technical Detail test failed!");
				success = false;
			}
			//System.out.println(ilrb.getReview());
			if (ilrb.getReview() == null || !ilrb.getReview().isValidate()) {
				System.out.println("Review test failed!");
				success = false;				
			}
			
		}
		return success;
	}
	
//	private boolean testTechnicalDetail(String sourceObjectId) {
//		
//	}
}
