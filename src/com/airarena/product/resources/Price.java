package com.airarena.product.resources;

import com.airarena.products.model.Product;
import com.airarena.products.model.ProductCondition;
import com.airarena.products.model.ProductPrice;


public class Price extends BaseResource {
	private String categoryName;
	private long amount;
	private String currencyCode;
	private String formatedPrice;
	private int available;
			
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getFormatedPrice() {
		return formatedPrice;
	}
	public void setFormatedPrice(String formatedPrice) {
		this.formatedPrice = formatedPrice;
	}
	public int getAvailable() {
		return available;
	}
	public void setAvailable(int available) {
		this.available = available;
	}
	
	
	public Price(String categoryName, long amount, String currencyCode,
			String formated_price, int available) {
		super();
		this.categoryName = categoryName;
		this.amount = amount;
		this.currencyCode = currencyCode;
		this.formatedPrice = formated_price;
		this.available = available;
	}
	
	public Price() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ProductPrice toProductPrice(Product product){
		return new ProductPrice(ProductCondition.getProductConditionByName(this.categoryName),
								this.amount,
								this.currencyCode,
								this.formatedPrice,
								this.available,
								product);
	}
	
	@Override
	public String toString() {
		return "Price [categoryName=" + categoryName + ", amount=" + amount
				+ ", currencyCode=" + currencyCode + ", formatedPrice="
				+ formatedPrice + ", available=" + available + "]";
	}
	
	
	
	
	
	

	

}
