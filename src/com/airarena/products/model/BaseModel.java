package com.airarena.products.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

//drop table product_attribute_meta_keys;
//drop table product_attributes;
//drop table product_images;
//drop table product_prices;
//drop table product_conditions;
//drop table product_reviews;
//drop table product_specifications;
//drop table category_product;
//drop table scraping_status;
//drop table products;
//drop table providers;
//drop table categories;

@MappedSuperclass
public class BaseModel implements Serializable {

    private int version;    
    private Date created_at;
    private Date updated_at;

	@Version
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at")
	public Date getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}

	@PrePersist
    protected void onCreate() {
		this.updated_at = this.created_at = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
    	this.updated_at = new Date();
    }
	
}
