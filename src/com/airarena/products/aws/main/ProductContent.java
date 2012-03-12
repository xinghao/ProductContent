package com.airarena.products.aws.main;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.airarena.aws.products.api.util.BasicApiRequest;
import com.airarena.aws.products.api.util.BrowserNodeLookupRequest;
import com.airarena.aws.products.api.util.BrowserNodelLookupResponse;
import com.airarena.aws.products.api.util.ItemLookupRequest;
import com.airarena.aws.products.api.util.ItemLookupResponse;
import com.airarena.aws.products.api.util.ItemSearchRequest;
import com.airarena.aws.products.api.util.ItemSearchResponse;
import com.airarena.hibernate.util.InitDB;
import com.airarena.hibernate.util.MyEntityManager;
import com.airarena.hibernate.util.MyEntityManagerFactory;
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
		MyEntityManager mem = new MyEntityManager();
		mem.newModel(c);
		System.out.println(b.toString());
		for(int i=0; i < b.getChildrenSourceObjectId().size(); i++) {
			buildCategories(b.getChildrenSourceObjectId().get(i), c.getId());
		}
		
	}
	
	private static void buildAProduct(String sourceObjectId, Long categoryId, int version) {
		
		BasicApiRequest bar = new ItemLookupRequest(sourceObjectId, 1, 1);
		ItemLookupResponse b = (ItemLookupResponse) bar.call();
		b.setCategoryId(categoryId);
		Product p = Product.createOrUpdateFromAwsApi(b, version);
		
	}
	
	
	private static ItemSearchResponse buildOnePageProductsForCategory(Category c, int page, String sort, Long maxPrice, int version) {
		
		BasicApiRequest bar = new ItemSearchRequest(c.getSource_object_id(), BasicApiRequest.AWS_SEARCHINDEX_ELECTRONICS, page, sort, maxPrice);
		ItemSearchResponse b = (ItemSearchResponse) bar.call();
		System.out.println(b);
		
		int totalItemsFetched = 0;
	/*	
		for(int i = 0; i<10 && i< b.getTotalItems(); i++ ) {
			try {
				String productSourceObjectId = b.getItemsIdList().get(i);
				System.out.println(productSourceObjectId);
				buildAProduct(productSourceObjectId, c.getId(), version);
				totalItemsFetched++;
				Thread.sleep(800);
			} catch (Exception e) {				
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	*/	
		return b;
		
	}
	
	private static Long buildAllProductsForCategory(Category c, int version) {
		int totalPagesFetched = 0;
		Long currentLowPrice = 0L;
		int currentPageNumberInTen = 0;
		Long totalItemsFetched = 0L;
		while (true) {
			
			// build 1 page
			ItemSearchResponse b = buildOnePageProductsForCategory(c, currentPageNumberInTen + 1, null, currentLowPrice, version);
			totalPagesFetched++;
			currentPageNumberInTen++;
			totalItemsFetched = totalItemsFetched + b.getItemsIdList().size();
			// // quit if less than 10 item left in the page all already fetched all pages
			if (b.getItemsIdList().size() < 10 || b.getTotalPage() == currentPageNumberInTen) {
				return totalItemsFetched;
			}
			System.out.println("Fetch size: " + b.getItemsIdList().size());
			
			// reset page number and low price for every 10 pages.
			if (currentPageNumberInTen == 10) {
				currentPageNumberInTen = 0;
				currentLowPrice = b.getPageLowerNewPrice();
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
			
			
			List<Category> cs = Category.getLeafCatgories(1L);
			for(int i=0; i< cs.size(); i++) {
				Category c = cs.get(i);
				//if (c.getId() < maxCategoryIdScraped) continue;				
				System.out.println(c.getId());
				System.out.println("Total:" + buildAllProductsForCategory(c, 1));
				break;
			}
			
//			BasicApiRequest itml = new ItemLookupRequest("B004N866SU", 1, 1);
//			ItemLookupResponse ilrb = (ItemLookupResponse) itml.call();
//			System.out.println(ilrb.toString());
			
//			buildAProduct("B004N866SU", 1L);
			
/*			
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
				
			}
*/			
//			SessionService.getInstance().releaseSession();
			
	    } catch (IOException e) {
	    	_logger.error("can not find/read property file " + configFilePath);
	    	System.out.println("can not find/read property file" + configFilePath);
	    	return;
	    }
	    //http://docs.amazonwebservices.com/AWSECommerceService/2011-08-01/DG/RG_OfferSummary.html

	}

}
