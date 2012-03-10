package com.airarena.products.aws.main;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

import com.airarena.aws.products.api.util.BasicApiRequest;
import com.airarena.aws.products.api.util.BrowserNodeLookupRequest;
import com.airarena.aws.products.api.util.BrowserNodelLookupResponse;
import com.airarena.aws.products.api.util.ItemLookupRequest;
import com.airarena.aws.products.api.util.ItemLookupResponse;
import com.airarena.aws.products.api.util.ItemSearchRequest;
import com.airarena.aws.products.api.util.ItemSearchResponse;
import com.airarena.hibernate.util.InitDB;
import com.airarena.hibernate.util.SessionService;
import com.airarena.products.conf.LoadConfig;
import com.airarena.products.model.Category;
import com.airarena.products.model.Product;

public class ProductContent {
	
	private static final Logger _logger = Logger.getLogger(ProductContent.class);
	
	private static final String logFilePath = "conf/log4j.properties";
	
	private static final String configFilePath = "conf/productcontent.properties";
	
	
	private static void buildCategories(String sourceObjectId, Long parentId) {

		BasicApiRequest bar = new BrowserNodeLookupRequest(sourceObjectId);
		BrowserNodelLookupResponse b = (BrowserNodelLookupResponse) bar.call();
		Category c = new Category(b.getName(), sourceObjectId, null, parentId, b.getChildrenSourceObjectId().size(), -1);
		SessionService ss = SessionService.getInstance();
		ss.newModel(c);
		System.out.println(b.toString());
		for(int i=0; i < b.getChildrenSourceObjectId().size(); i++) {
			buildCategories(b.getChildrenSourceObjectId().get(i), c.getId());
		}
		
	}
	
	private static void buildAProduct(String sourceObjectId, Long categoryId) {
		
		BasicApiRequest bar = new ItemLookupRequest(sourceObjectId, 1, 1);
		ItemLookupResponse b = (ItemLookupResponse) bar.call();
		b.setCategoryId(categoryId);
		Product p = Product.createFromAwsApi(b);
		
	}
	
	
	private static void buildAllProductsForCategory(Category c) {

		BasicApiRequest bar = new ItemSearchRequest(c.getSource_object_id(), BasicApiRequest.AWS_SEARCHINDEX_ELECTRONICS, 1, null, null);
		ItemSearchResponse b = (ItemSearchResponse) bar.call();
		for(int i = 0; i< 5 && i< b.getTotalItems(); i++ ) {
			try {
				String productSourceObjectId = b.getItemsIdList().get(i);
				System.out.println(productSourceObjectId);
				buildAProduct(productSourceObjectId, c.getId());
			
				Thread.sleep(800);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Load config
		try{
			LoadConfig.loadConfig(configFilePath, logFilePath);
			
//		    Init db: create aws provider or samething.
//			InitDB.insertInitData();
//			buildCategories("493964", null);
//			BasicApiRequest itms = new ItemSearchRequest("172665", BasicApiRequest.AWS_SEARCHINDEX_ELECTRONICS, 1, null, null);
//			ItemSearchResponse itsr = (ItemSearchResponse)itms.call();
//			System.out.println(itsr.toString());
//			System.out.println(b.toString());
//			BasicApiRequest itml = new ItemLookupRequest("B004QK7HI8", 1, 1);
//			ItemLookupResponse ilrb = (ItemLookupResponse) itml.call();
//			System.out.println(ilrb.toString());
			
//			buildAProduct("B004QK7HI8", 1L);
			int version = 1;
			Long maxCategoryIdScraped = Product.getMaxCategoryIdAlreadyExist(version);
			if (maxCategoryIdScraped == null) {
				maxCategoryIdScraped = 0L;
			}
			if (maxCategoryIdScraped > 0) {
				Product.deleteAllProductsForCategory(version, maxCategoryIdScraped);
			}
			

			List<Category> cs = Category.getLeafCatgories(1L);
			for(int i=0; i< cs.size(); i++) {
				Category c = cs.get(i);
				if (c.getId() < maxCategoryIdScraped) continue;				
				System.out.println(c.getId());
				buildAllProductsForCategory(c);
//				
			}
			
//			SessionService.getInstance().releaseSession();
			
	    } catch (IOException e) {
	    	_logger.error("can not find/read property file " + configFilePath);
	    	System.out.println("can not find/read property file" + configFilePath);
	    	return;
	    }

	}

}
