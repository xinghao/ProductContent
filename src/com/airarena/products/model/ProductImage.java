package com.airarena.products.model;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NoResultException;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Type;

import com.airarena.aws.products.api.util.ItemLookupResponse;
import com.airarena.product.resources.Image;

@Entity
@Table( name = "product_images" )
public class ProductImage extends BaseModel {
	
    private Long id;
	
	private Long product_id;
	private String url;
	private String image_category;
	private int weight;
	private int height;
    private int is_valid = 1;
//    private int version = 1;    
//    private Date created_at;
//    private Date updated_at;
	
    public ProductImage() {
    	
    }
    
    public ProductImage(Long productId, Image image, int is_valid, int version) {
    	this.product_id = productId;
    	this.url = image.getUrl();
    	this.image_category = image.getImageCategory();
    	this.weight = image.getWeight();
    	this.height = image.getHeight();
    	this.is_valid = is_valid;
//    	this.version = version;
//		this.updated_at = this.created_at = new Date();		    	
    }
    
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")	    
    public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	
	@org.hibernate.annotations.Index(name = "myProductImageProductIdIndex")	
	public Long getProduct_id() {
		return product_id;
	}
	public void setProduct_id(Long product_id) {
		this.product_id = product_id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getImage_category() {
		return image_category;
	}
	public void setImage_category(String image_category) {
		this.image_category = image_category;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getIs_valid() {
		return is_valid;
	}
	public void setIs_valid(int is_valid) {
		this.is_valid = is_valid;
	}
	
	/*
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
*/
	
    
}
