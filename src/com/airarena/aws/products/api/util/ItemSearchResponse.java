package com.airarena.aws.products.api.util;

import java.util.ArrayList;

public class ItemSearchResponse extends BasicApiRespose {

	private int totalPage;
	private int totalItems;
	private int currentPage;
	private ArrayList<String> itemsIdList = new ArrayList<String>();
	
	
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
			"[ChildList:" + itemsIdList.toString() + "]";
		     		
	}	
	
}
