package com.airarena.aws.products.api.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.airarena.hibernate.util.SessionService;
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
	
	private HashMap<String, Image> images = new HashMap<String, Image>();
	private HashMap<Long, String> itemAttributes = new HashMap<Long, String>();
	
	
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
        
        
		return "ItemLookupResponse [sourceObjectId=" + sourceObjectId
				+ ", rawXmlContentUrl=" + rawXmlContentUrl
				+ ", rawHtmlContentUrl=" + rawHtmlContentUrl + ", descirption="
				+ descirption + ", reviewUrl=" + reviewUrl
				+ ", specificationUrl=" + specificationUrl + ", images="
//				+ itms.toString() 
				+ ", attributs=" +as.toString()  
				+ "]";
	}




}
