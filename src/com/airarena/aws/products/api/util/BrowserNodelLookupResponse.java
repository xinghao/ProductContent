package com.airarena.aws.products.api.util;

import java.util.ArrayList;

import com.airarena.products.model.Category;

public class BrowserNodelLookupResponse extends BasicApiRespose {
	
	private String sourceObjectId;
	private String name;
	private String parentSourceObjectId;
	private ArrayList<String> childrenSourceObjectId = new ArrayList<String>();
	
	
	public String getSourceObjectId() {
		return sourceObjectId;
	}

	public void setSourceObjectId(String sourceObjectId) {
		this.sourceObjectId = sourceObjectId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	public String getParentSourceObjectId() {
		return parentSourceObjectId;
	}

	public void setParentSourceObjectId(String parentSourceObjectId) {
		this.parentSourceObjectId = parentSourceObjectId;
	}

	public ArrayList<String> getChildrenSourceObjectId() {
		return childrenSourceObjectId;
	}

	public void setChildrenSourceObjectId(ArrayList<String> childrenSourceObjectId) {
		this.childrenSourceObjectId = childrenSourceObjectId;
	}

	
	public String toString() {
		StringBuilder childrens = new StringBuilder();
		for(int i=0; i< this.childrenSourceObjectId.size(); ++i) {
			if(childrens.toString().equals("")) {
				childrens.append(this.childrenSourceObjectId.get(i));
			}else {
				childrens.append(", " + this.childrenSourceObjectId.get(i));
			}
		}
			
		return "[SoureObjectId:" + this.sourceObjectId + "], " +
			"[Name:" + this.name + "], " +
			"[ParentSourceObjectId:" + (this.parentSourceObjectId==null?"null":this.parentSourceObjectId) + "], " +
			"[ItemsList:" + this.childrenSourceObjectId.size() + "], " +
			"[ItemsList:" + childrens.toString() + "]";
		     		
	}

	@Override
	public boolean isValidate() {
		boolean success = true;
		System.out.println("Validate Browser Node Lookup response =====================");

		if (!BasicApiRespose.validateString(this.getSourceObjectId())) {
			System.out.println("source object test failed!");
			success = false;			
		}
		
		if (!BasicApiRespose.validateString(this.getName())) {
			System.out.println("Name test failed!");
			success = false;			
		}

		if (!BasicApiRespose.validateString(this.getParentSourceObjectId())) {
			System.out.println("ParentsourceObjectId test failed!");
			success = false;			
		}
		
		if (!BasicApiRespose.validateList(this.getChildrenSourceObjectId())) {
			System.out.println("Children list failed!");
			success = false;			
		}
	
		

		if (!success) {
			System.out.println("Browser Node Lookup test failed!!!!!!!!!!!!!!!!!");
		} else {
			System.out.println("Browser Node Lookup test passed!!!!!!!!!!!!!!!!!");
		}
		
		return success;
		
	}
	
}
