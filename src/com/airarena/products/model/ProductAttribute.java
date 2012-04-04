package com.airarena.products.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NoResultException;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Type;

import com.airarena.aws.products.api.util.ItemLookupResponse;

@Entity
@Table( name = "product_attributes" )
public class ProductAttribute extends BaseModel {

	
    private Long id;
    		
	private Product product;
	private Long product_attribute_meta_key_id;
	private String value;
    private int is_valid = 1;
//    private int version = 1;    
//    private Date created_at;
//    private Date updated_at;
	
    
	public ProductAttribute() {
		// TODO Auto-generated constructor stub
	}
	
	



	public ProductAttribute(Product p,
			Long product_attribute_meta_key_id, String value, int is_valid) {
		super();
		this.product = p;
		this.product_attribute_meta_key_id = product_attribute_meta_key_id;
		this.value = value;
		this.is_valid = is_valid;
//		this.version = version;
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


//	@org.hibernate.annotations.Index(name = "myProductAttributeProductIdIndex")
//	public Long getProduct_id() {
//		return product_id;
//	}
//
//
//
//	public void setProduct_id(Long product_id) {
//		this.product_id = product_id;
//	}

	@ManyToOne
    @JoinColumn(name="product_id")
    @org.hibernate.annotations.Index(name = "myProductAttributeProductIdIndex")
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}

	public Long getProduct_attribute_meta_key_id() {
		return product_attribute_meta_key_id;
	}



	public void setProduct_attribute_meta_key_id(Long product_attribute_meta_key_id) {
		this.product_attribute_meta_key_id = product_attribute_meta_key_id;
	}


	@Type(type="text")
	public String getValue() {
		return value;
	}



	public void setValue(String value) {
		this.value = value;
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
