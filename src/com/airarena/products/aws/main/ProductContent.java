package com.airarena.products.aws.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;


import org.apache.log4j.Logger;

import com.airarena.aws.products.api.util.ApiConfiguration;
import com.airarena.aws.products.api.util.AwsApiException;
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
import com.airarena.product.test.TestItemLookUp;
import com.airarena.products.conf.ConfigElementTags;
import com.airarena.products.conf.LoadConfig;
import com.airarena.products.model.Category;
import com.airarena.products.model.Product;
import com.airarena.products.model.ScrapingStatus;
import com.airarena.scraper.ScrapingException;

public class ProductContent {
	
	private static final Logger _logger = Logger.getLogger(ProductContent.class);
	
	private static final String logFilePath = "conf/log4j.properties";
	
	private static final String configFilePath = "conf/productcontent.properties";
	
	private static HashMap<String, String> tmpList = new HashMap<String, String>();
	
	private static void buildCategories(String sourceObjectId, Long parentId, Long providerId, Long scraperVersion) {

		BasicApiRequest bar = new BrowserNodeLookupRequest(sourceObjectId);
		BrowserNodelLookupResponse b = (BrowserNodelLookupResponse) bar.call();
		//Category c = new Category(b.getName(), sourceObjectId, null, parentId, b.getChildrenSourceObjectId().size(), 1, -1);
		Category c = Category.createOrUpdateFromAwsApi(b, parentId, providerId, scraperVersion, true);

		//System.out.println(b.toString());
		for(int i=0; i < b.getChildrenSourceObjectId().size(); i++) {
			buildCategories(b.getChildrenSourceObjectId().get(i), c.getId(), providerId, scraperVersion);
		}
		
	}
	
	private static void multiProductBuilts(ItemSearchResponse b, Category c, long version) {

		try {
			ThreadGroup g = new ThreadGroup("MultipleLookupRequest");		
			List<ItemLookupResponse> multiIlrList = new ArrayList<ItemLookupResponse>();
			
			for(int i = 0; i<10 && i< b.getItemsIdList().size(); i++ ) {
	
					String productSourceObjectId = b.getItemsIdList().get(i);
					_logger.info(productSourceObjectId);
					ItemLookupRequest bar = new ItemLookupRequest(productSourceObjectId, 1, 1);
					bar.setMultiIlrList(multiIlrList);
					bar.setApiConf(ApiConfiguration.getInstance());
					Thread t = new Thread(g, bar, productSourceObjectId);
					t.start();
					//ItemLookupResponse ilrb = (ItemLookupResponse) bar.call();				
			}
			
			// wait 15 seconds before force quite
			int icount = 0;
			while(true) {
				if (g.activeCount() == 0) {
					_logger.info("all finished");					
					break;
				}
				
				Thread.sleep(1000);
				icount++;
				if (icount == 15) {
					g.interrupt();
					break;
				}
			}
			
			
			for(ItemLookupResponse ilrb : multiIlrList) {
				ilrb.setCategory(c);
				Product p = Product.createOrUpdateFromAwsApi(ilrb, version);
			}
			
		} catch (Exception e) {				
			// TODO Auto-generated catch block
			_logger.error(e);
		}
		
		/* mulitthread 
		

		for(int i = 0; i< b.getItemsIdList().size(); i++) {
			MyThread m = new MyThread();
			m.setCount(i);
			Thread f = new Thread(g, m, "hello" + i);
			f.start();
		}
		
		System.out.println("checking.........");
		while(true) {
			if (g.activeCount() == 0) {
				System.out.println("all finished");
				break;
			}
		}
		*/
	}
	
	private static void buildAProduct(String sourceObjectId, Category category, long version) {
		
		BasicApiRequest bar = new ItemLookupRequest(sourceObjectId, 1, 1);
		ItemLookupResponse b = (ItemLookupResponse) bar.call();
		b.setCategory(category);
		Product p = Product.createOrUpdateFromAwsApi(b, version);
		
	}
	
	
	private static ItemSearchResponse buildOnePageProductsForCategory(Category c, int page, String sort, Long maxPrice, long version) {
		
		MyEntityManagerFactory.releaseSession();
		
		BasicApiRequest bar = new ItemSearchRequest(c.getSource_object_id(), BasicApiRequest.AWS_SEARCHINDEX_ELECTRONICS, page, sort, maxPrice);
		ItemSearchResponse b = (ItemSearchResponse) bar.call();
//		System.out.println(b);
		
		int totalItemsFetched = 0;
		int ignore = 0;

// remove test info.		
//		for(int i = 0; i<10 && i< b.getItemsIdList().size(); i++ ) {
//			if (tmpList.containsKey(b.getItemsIdList().get(i))) {
//				ignore++;
//			}
//			tmpList.put(b.getItemsIdList().get(i), b.getItemsIdList().get(i));
//		}
//		_logger.info("ignore: " + ignore);


		/* mulitple thread */ 
		multiProductBuilts( b, c, version);
 

/* one thread		
		for(int i = 0; i<10 && i< b.getItemsIdList().size(); i++ ) {
			try {
				String productSourceObjectId = b.getItemsIdList().get(i);
				_logger.info(productSourceObjectId);
				buildAProduct(productSourceObjectId, c, version);
				totalItemsFetched++;
			} catch (Exception e) {				
				// TODO Auto-generated catch block
				_logger.error(e);
			}
		}
*/
		return b;
		
	}
	
	private static Long buildAllProductsForCategory(Category c, long version) {
		int totalPagesFetched = 0;
		Long currentLowPrice = 0L;
		int currentPageNumberInTen = 0;
		Long totalItemsFetched = 0L;
		Long previousLowPrice = -1L;

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
			_logger.info("Fetch size: " + b.getItemsIdList().size());
			
			// reset page number and low price for every 10 pages.
			if (currentPageNumberInTen == 10) {
				currentPageNumberInTen = 0;
				
				_logger.info("Previews price: " + previousLowPrice.longValue());
				_logger.info("Current price: " + currentLowPrice.longValue());
				if (currentLowPrice.longValue() == previousLowPrice.longValue()) {
					_logger.info("!!!!!!!!!!!!!!!!! products missing for price: " + currentLowPrice + " !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
					currentLowPrice =  currentLowPrice + 1;
				} else {
					previousLowPrice = currentLowPrice;
					currentLowPrice = b.getPageMaxNewPrice();
				}
//				if (currentLowPrice == b.getPageMaxNewPrice()) {
//					_logger.info("!!!!!!!!!!!!!!!!! products missing for price: " + currentLowPrice + " !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//					currentLowPrice += 1;
//				} else {
//					currentLowPrice = b.getPageMaxNewPrice();
//				}
			}
		}
		
	}
	
	public static void start(boolean isResume) {
		
		InitDB.insertInitData();
		ScrapingStatus ss = null;
		Category resumeFromCategory = null; 
		try {
			ss = ScrapingStatus.nextNewScrapingVersion(isResume);
			
			// running api test.
			if (TestItemLookUp.test()) {
				_logger.info("Test passed!");
			} else {
				throw new AwsApiException("Test failed!");
			}					
			
			boolean isBuildCategories = true;
			if (isResume) {
				long scraperVersion = ss.getScraper_version().getScraper_version();
				_logger.info("current scraper version: " +scraperVersion);
				//resumeFromCategory = ScrapingStatus.getMaxCategoryIdAlreadyExist(scraperVersion);
				resumeFromCategory = ss.getCategory();
				_logger.info("resume mode, max category id: " + resumeFromCategory.getId());
				if (resumeFromCategory != null) {
					isBuildCategories = false;
				}
			}
			
			if (isBuildCategories) {
				buildCategories((String) System.getProperty(ConfigElementTags.AWS_BROWSER_NODE_ID_ELECTRONIC),  null, 1L, ss.getScraper_version().getScraper_version());
			}

			List<Category> cs = Category.getLeafCatgories(ss.getScraper_version().getScraper_version());
			System.out.println("current scrapping start from: " + resumeFromCategory.getId());
			for(int i=1; i<= cs.size(); i++) {
				boolean isOverride = true;
				Category c = cs.get(i);
				if (isResume && resumeFromCategory != null && c.getId() < resumeFromCategory.getId()) {
					_logger.info("resume mode, skip: " + i);
					System.out.println("resume mode, skip: " + i);
					continue;
				} 
				
				ss.setCategory(c);
				ss.save();
				buildAllProductsForCategory(c, ss.getScraper_version().getScraper_version());
				//System.out.println("Total:" + buildAllProductsForCategory(c, 1));
				//System.out.println("Total in list:" + tmpList.size());
				//break;
			}
			
			ss.setStatus(ScrapingStatus.SUCCESS);
			ss.save();

		}catch (Exception e) {
			// TODO Auto-generated catch block
	    	_logger.error(e);
			e.printStackTrace();
			if (ss != null) {
				ss.setStatus(ScrapingStatus.ERROR);
				ss.save();						
			}
		}		
	}
	/**
	 * @param args
	 * 1. Init db. Only run when migrate to a new database
	 * 2. Run a brand new AWS scraping
	 * 3. Run from last error point
	 * 4. Scrape user manual (comming)
	 */
	public static void main(String[] args) {
		// Load config
		try{
			LoadConfig.loadConfig(configFilePath, logFilePath);
//			System.out.println("args: " + args.length);
//			System.out.println("args: " + args[0]);
			if (args.length == 0) {
				System.out.println("please append the run options[1-4] to the end of command line.");
				System.out.println("1. Init db. Only run when migrate to a new database");
				System.out.println("2. Run a brand new AWS scraping");
				System.out.println("3. Run from last error point");
				System.out.println("4. Scrape user manual (coming)");
				return;
			}
			
			switch(Integer.valueOf(args[0])) {
			case 1:
				InitDB.insertInitData();
				break;
			case 2:
				_logger.info("Run a brand new AWS scraping");
				start(false);
				break;
			case 3:
				_logger.info("resume current AWS scraping");
				start(true);
				break;
			case 4:
				_logger.info("case 4 is coming soon");
				break;
			case 5:
				_logger.info("case 5 is testing");
				InitDB.insertInitData();
				if (TestItemLookUp.test()) {
					_logger.info("Test passed!");
				}
				break;				
			case 99:
				
				_logger.info("debug mode");
				InitDB.insertInitData();
				
				
				//start(false);
				EntityManager entityManager = MyEntityManagerFactory.getInstance();
//				buildOnePageProductsForCategory(entityManager.find(Category.class, 4L), 1, null, null, 11);
				buildAProduct("B002DMWJNY", entityManager.find(Category.class, 234L), 1L);
				/*
				Category maxCategoryIdScraped = ScrapingStatus.getMaxCategoryIdAlreadyExist(1L);

				
				if (maxCategoryIdScraped == null) {
					_logger.info("haha: " + "0");
				} else {
					_logger.info("haha: " + maxCategoryIdScraped.getName());
				
				}
				EntityManager entityManager = MyEntityManagerFactory.getInstance();
				try{
					ScrapingStatus ss = ScrapingStatus.nextNewScrapingVersion(true);
					ss.setCategory(entityManager.find(Category.class, 6L));
					ss.save();
				}catch(Exception e) {
					
				}
				*/
				//System.out.println(ScrapingStatus.getMaxScrapingVersion());
//				ScrapingStatus ss = ScrapingStatus.nextNewScrapingVersion(true); 
				//images B004LDG9IO
				// no primary images B004N866SU
//				BasicApiRequest itml = new ItemLookupRequest("B004LDG9IO", 1, 1);
//				ItemLookupResponse ilrb = (ItemLookupResponse) itml.call();
//				System.out.println(ilrb.toString());
//				ilrb.setCategoryId(1L);
//				Product p = Product.createOrUpdateFromAwsApi(ilrb, 1);	

				
//				
//				buildAProduct("B0048JGC6U", entityManager.find(Category.class, 1L), 1L);
//				buildAProduct("B004LDG9IO", entityManager.find(Category.class, 2L), 1L);
//				ss.setStatus(ScrapingStatus.SUCCESS);
//				ss.save();
				
				
				//buildAllProductsForCategory(c, ss.getScraper_version().getScraper_version());
				
//				BasicApiRequest itms = new ItemSearchRequest("1288217011", BasicApiRequest.AWS_SEARCHINDEX_ELECTRONICS, 1, BasicApiRequest.AWS_SORT_US_ELECTRONIC_ASC, 44361L);
//				ItemSearchResponse itsr = (ItemSearchResponse)itms.call();
				
				break;
			default:
				System.out.println("invalid options. It must be 1 - 4");
				return;
			}
//		    Init db: create aws provider or samething.
//			InitDB.insertInitData();
//			buildCategories("493964", null);
//			BasicApiRequest itms = new ItemSearchRequest("172665", BasicApiRequest.AWS_SEARCHINDEX_ELECTRONICS, 1, null, null);
//			ItemSearchResponse itsr = (ItemSearchResponse)itms.call();
//			System.out.println(itsr.toString());
//			System.out.println(b.toString());
			
/*			
			List<Category> cs = Category.getLeafCatgories(1L);
			for(int i=0; i< cs.size(); i++) {
				Category c = cs.get(i);
				//if (c.getId() < maxCategoryIdScraped) continue;				
				System.out.println(c.getId());
				System.out.println("Total:" + buildAllProductsForCategory(c, 1));
				System.out.println("Total in list:" + tmpList.size());
				break;
			}
*/	
			
//			BasicApiRequest itml = new ItemLookupRequest("B004N866SU", 1, 1);
//			ItemLookupResponse ilrb = (ItemLookupResponse) itml.call();
//			System.out.println(ilrb.toString());
//			ilrb.setCategoryId(1L);
//			Product p = Product.createOrUpdateFromAwsApi(ilrb, 1);			
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
