package com.airarena.aws.products.api.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.airarena.product.resources.*;
import com.airarena.products.model.Product;

public class ItemLookupResponse extends BasicApiRespose {

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
	private Long categoryId;
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


	public Long getCategoryId() {
		return categoryId;
	}


	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}


	public String getRawXmlContentUrl() {
		return rawXmlContentUrl;
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




}
