package com.airarena.aws.products.api.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.airarena.product.resources.*;
import com.airarena.products.model.Category;
import com.airarena.products.model.Product;

public class ItemLookupResponse extends BasicApiRespose {
	private static final Logger _logger = Logger.getLogger(ItemLookupResponse.class);
	
	private String sourceObjectId;
//	private String name;
//	private String Manufacturer;
//	private String productGroup;
//	private String productTypeName;
	private String rawXmlContentUrl;
	private String rawHtmlContentUrl;
	private String descirption;
	private String reviewUrl;
	private String specificationUrl;
	private Category category;	
	private Long salesRank = 0L;	
//	private Long priceAmount = 0L;
//	private String currencyCode = "USD";
	
	private HashMap<String, Image> images = new HashMap<String, Image>();
	private HashMap<Long, String> itemAttributes = new HashMap<Long, String>();
	private HashMap<String, Price> priceList= new HashMap<String, Price>();
	private HashMap<String, String> technicalDetailList= new HashMap<String, String>();
	private Review review;
	
	public String getSourceObjectId() {
		return sourceObjectId;
	}
	
	public void setSourceObjectId(String sourceObjectId) {
		this.sourceObjectId = sourceObjectId;
	}

	
	

//	public String getName() {
//		return name;
//	}
//
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//
//	public String getManufacturer() {
//		return Manufacturer;
//	}
//
//
//	public void setManufacturer(String manufacturer) {
//		Manufacturer = manufacturer;
//	}
//
//
//	public String getProductGroup() {
//		return productGroup;
//	}
//
//
//	public void setProductGroup(String productGroup) {
//		this.productGroup = productGroup;
//	}


//	public String getProductTypeName() {
//		return productTypeName;
//	}
//
//
//	public void setProductTypeName(String productTypeName) {
//		this.productTypeName = productTypeName;
//	}


	public Review getReview() {
		return review;
	}


	public void setReview(Review review) {
		this.review = review;
	}


	public HashMap<String, String> getTechnicalDetailList() {
		return technicalDetailList;
	}


	public void setTechnicalDetailList(HashMap<String, String> technicalDetailList) {
		this.technicalDetailList = technicalDetailList;
	}


//	public Long getCategoryId() {
//		return categoryId;
//	}
//
//
//	public void setCategoryId(Long categoryId) {
//		this.categoryId = categoryId;
//	}


	public String getRawXmlContentUrl() {
		return rawXmlContentUrl;
	}


	public Category getCategory() {
		return category;
	}


	public void setCategory(Category category) {
		this.category = category;
	}


	public void setRawXmlContentUrl(String rawXmlContentUrl) {
		this.rawXmlContentUrl = rawXmlContentUrl;
	}


	public String getRawHtmlContentUrl() {
		return rawHtmlContentUrl;
	}


	public void setRawHtmlContentUrl(String rawHtmlContentUrl) {
		this.rawHtmlContentUrl = rawHtmlContentUrl;
	}


	public String getDescirption() {
		return descirption;
	}


	public void setDescirption(String descirption) {
		this.descirption = descirption;
	}


	public String getReviewUrl() {
		return reviewUrl;
	}


	public void setReviewUrl(String reviewUrl) {
		this.reviewUrl = reviewUrl;
	}


	public String getSpecificationUrl() {
		return specificationUrl;
	}


	public void setSpecificationUrl(String specificationUrl) {
		this.specificationUrl = specificationUrl;
	}


	public HashMap<String, Image> getImages() {
		return images;
	}


	public void setImages(HashMap<String, Image> images) {
		this.images = images;
	}


	public HashMap<Long, String> getItemAttributes() {
		return itemAttributes;
	}


	public void setItemAttributes(HashMap<Long, String> itemAttributes) {
		this.itemAttributes = itemAttributes;
	}


	public Long getSalesRank() {
		return salesRank;
	}


	public void setSalesRank(Long salesRank) {
		this.salesRank = salesRank;
	}


//	public Long getPriceAmount() {
//		return priceAmount;
//	}
//
//
//	public void setPriceAmount(Long priceAmount) {
//		this.priceAmount = priceAmount;
//	}
//
//
//	public String getCurrencyCode() {
//		return currencyCode;
//	}
//
//
//	public void setCurrencyCode(String currencyCode) {
//		this.currencyCode = currencyCode;
//	}

	

	public HashMap<String, Price> getPriceList() {
		return priceList;
	}


	public void setPriceList(HashMap<String, Price> priceList) {
		this.priceList = priceList;
	}


	@Override
	public String toString() {
		StringBuilder itms = new StringBuilder();
		
		Set s = images.entrySet();
        Iterator i = s.iterator();
        while (i.hasNext()) {
        	itms.append(i.next());
        }
        
        StringBuilder as = new StringBuilder();
        
		Set ass = this.itemAttributes.entrySet();
        Iterator j = ass.iterator();
        while (j.hasNext()) {
        	as.append(j.next());
        }
        
        StringBuilder pls = new StringBuilder();
        
		Set plss = this.priceList.entrySet();
        Iterator k = plss.iterator();
        while (k.hasNext()) {
        	pls.append(k.next());
        }

        StringBuilder tdList = new StringBuilder();
        
		Set tdListSet = this.technicalDetailList.entrySet();
        Iterator o = tdListSet.iterator();
        while (o.hasNext()) {
        	tdList.append(o.next());
        }
        
        String reviewStr = "";
        if (this.review != null) reviewStr = this.review.toString();
        
		return "ItemLookupResponse [sourceObjectId=" + sourceObjectId
				+ ", rawXmlContentUrl=" + rawXmlContentUrl
				+ ", rawHtmlContentUrl=" + rawHtmlContentUrl + ", technical_details="
				+ tdList.toString() + ", review="
				+ reviewStr + ", descirption="
				+ descirption + ", SalesRank=" 
				+ this.salesRank + ", priceList="
				+ pls.toString() + ", reviewUrl=" + reviewUrl				
				+ ", specificationUrl=" + specificationUrl
				+ ",  images="
//				+ itms.toString() 
				+ ", attributs=" +as.toString()  
				+ "]";
	}


	@Override
	public boolean isValidate() {
		boolean success = true;
		_logger.info("Validate Lookup response =====================");

		if (!BasicApiRespose.validateString(this.getSourceObjectId())) {
			_logger.info("source object test failed!");
			success = false;			
		}
		
		if (!BasicApiRespose.validateString(this.getRawXmlContentUrl())) {
			_logger.info("rawXmlContentUrl test failed!");
			success = false;			
		}

		if (!BasicApiRespose.validateString(this.getReviewUrl())) {
			_logger.info("reviewUrl test failed!");
			success = false;			
		}
		
		if (!BasicApiRespose.validateString(this.getSpecificationUrl())) {
			_logger.info("specificationUrl test failed!");
			success = false;			
		}

		if (!BasicApiRespose.validateString(this.getRawHtmlContentUrl())) {
			_logger.info("rawHtmlContentUrl test failed!");
			success = false;			
		}

		if (!BasicApiRespose.validateString(this.getDescirption())) {
			_logger.info("descirption test failed!");
			success = false;			
		}
		
		if (!BasicApiRespose.validateString(this.getPermalink())) {
			_logger.info("Permalink test failed!");
			success = false;			
		}
		
		
//		if (this.getCategory(). <= 0) {
//			_logger.info("categoryId test failed!");
//			success = false;			
//		}
		
		if (this.getSalesRank() <= 0 ) {
			_logger.info("salesRank test failed!");
			success = false;						
		}
		
		if (!BasicApiRespose.validateMap(this.getImages())) {
			_logger.info("images test failed!");
			success = false;									
		}

		if (!BasicApiRespose.validateMap(this.getItemAttributes())) {
			_logger.info("itemAttributes test failed!");
			success = false;									
		}

		if (!BasicApiRespose.validateMap(this.getPriceList())) {
			_logger.info("priceList test failed!");
			success = false;									
		}

		if (!BasicApiRespose.validateMap(this.getTechnicalDetailList())) {
			_logger.info("technicalDetailList test failed!");
			success = false;									
		}
		
		if (!this.getReview().isValidate()) {
			_logger.info("review test failed!");
			_logger.info(this.getReview().toString());
			success = false;												
		}

		
		if (!success) {
			_logger.info("IemLookupResponse test failed!!!!!!!!!!!!!!!!!");
		} else {
			_logger.info("IemLookupResponse test passed!!!!!!!!!!!!!!!!!");
		}
		
		return success;
	}


//	public boolean isValidate() {
//		
//		
//		
//	private Long priceAmount = 0L;
//	private String currencyCode = "USD";
	

	//		
//
//		BasicApiRequest itml = new ItemLookupRequest(sourceObjectId, 1, 1);
//		ItemLookupResponse ilrb = (ItemLookupResponse) itml.call();
//		_logger.info(ilrb);
//		
//		if (ilrb.getTechnicalDetailList().isEmpty()) {
//			_logger.info("Technical Detail test failed!");
//			success = false;
//		}
//		//_logger.info(ilrb.getReview());
//		if (ilrb.getReview() == null || !ilrb.getReview().isValidate()) {
//			_logger.info("Review test failed!");
//			success = false;				
//		}
//		
//		if (ilrb.getImages() == null || ilrb.getImages().isEmpty()) {
//			_logger.info("Image test failed!");
//			success = false;								
//		}
//			
//
//		return success;
//
//	}

	public String getPermalink() {
		String returnStr;
		String url = this.getRawHtmlContentUrl();
		Pattern p = Pattern.compile("com\\/(.*)\\/dp",  Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(url);

		if (m.find()) {
		returnStr = m.group(1).trim();
		
		return returnStr;
		}
		else return null;
		
	}
	

}
