package com.airarena.aws.products.api.util;

import java.util.ArrayList;

public class ItemSearchResponse extends BasicApiRespose {

	private int totalPage;
	private int totalItems;
	private int currentPage;
	private Long pageMaxNewPrice;
	
	
	private ArrayList<String> itemsIdList = new ArrayList<String>();
	
	
	public Long getPageMaxNewPrice() {
		return pageMaxNewPrice;
	}
	public void setPageMaxNewPrice(Long pageLowerNewPrice) {
		this.pageMaxNewPrice = pageLowerNewPrice;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
	public int getTotalItems() {
		return totalItems;
	}
	public void setTotalItems(int totalItems) {
		this.totalItems = totalItems;
	}
	public ArrayList<String> getItemsIdList() {
		return itemsIdList;
	}
	public void setItemsIdList(ArrayList<String> itemsIdList) {
		this.itemsIdList = itemsIdList;
	}
	
	
	
	public String toString() {
		StringBuilder itms = new StringBuilder();
		for(int i=0; i< this.itemsIdList.size(); ++i) {
			if(itms.toString().equals("")) {
				itms.append(this.itemsIdList.get(i));
			}else {
				itms.append(", " + this.itemsIdList.get(i));
			}
		}
			
		return "[TotalPage:" + this.totalPage + "], " +
			"[CurrentPage:" + this.currentPage + "], " +			
			"[TotalItems:" + this.totalItems + "], " +			
			"[ResultsAmount:" + this.itemsIdList.size() + "], " +
			"[LowestNewPrice:" + this.pageMaxNewPrice + "], " +
			"[ChildList:" + itemsIdList.toString() + "]";
		     		
	}
	
	
	@Override
	public boolean isValidate() {

		boolean success = true;
		System.out.println("Validate Search response =====================");


		if (this.getTotalPage() <= 0 ) {
			System.out.println("totalPage test failed!");
			success = false;			
		}

		if (this.getTotalItems() <= 0 ) {
			System.out.println("totalItems test failed!");
			success = false;			
		}
		if (this.getCurrentPage() <= 0 ) {
			System.out.println("currentPage test failed!");
			success = false;			
		}
		if (this.getPageMaxNewPrice() <= 0 ) {
			System.out.println("pageMaxNewPrice test failed!");
			success = false;			
		}
		
		if (!BasicApiRespose.validateList(this.getItemsIdList())) {
			System.out.println("ItemsIdList test failed!");
			success = false;									
		}
		
		if (!success) {
			System.out.println("IemSearchResponse test failed!!!!!!!!!!!!!!!!!");
		} else {
			System.out.println("IemSearchResponse test passed!!!!!!!!!!!!!!!!!");
		}
		
		return success;
		

		
	}	
	
}
