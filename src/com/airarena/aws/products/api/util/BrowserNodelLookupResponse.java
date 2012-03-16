package com.airarena.aws.products.api.util;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.airarena.products.aws.main.ProductContent;
import com.airarena.products.model.Category;

public class BrowserNodelLookupResponse extends BasicApiRespose {
	private static final Logger _logger = Logger.getLogger(BrowserNodelLookupResponse.class);
	
	private String sourceObjectId;
	private String name;
	private String parentSourceObjectId;
	private ArrayList<String> childrenSourceObjectId = new ArrayList<String>();
	private Long parent_id;
	
	
	
	public Long getParent_id() {
		return parent_id;
	}

	public void setParent_id(Long parent_id) {
		this.parent_id = parent_id;
	}

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
		_logger.info("Validate Browser Node Lookup response =====================");

		if (!BasicApiRespose.validateString(this.getSourceObjectId())) {
			_logger.info("source object test failed!");
			success = false;			
		}
		
		if (!BasicApiRespose.validateString(this.getName())) {
			_logger.info("Name test failed!");
			success = false;			
		}

		if (!BasicApiRespose.validateString(this.getParentSourceObjectId())) {
			_logger.info("ParentsourceObjectId test failed!");
			success = false;			
		}
		
		if (!BasicApiRespose.validateList(this.getChildrenSourceObjectId())) {
			_logger.info("Children list failed!");
			success = false;			
		}
	
		

		if (!success) {
			_logger.info("Browser Node Lookup test failed!!!!!!!!!!!!!!!!!");
		} else {
			_logger.info("Browser Node Lookup test passed!!!!!!!!!!!!!!!!!");
		}
		
		return success;
		
	}
	
}
