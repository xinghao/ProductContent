package com.airarena.products.model;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

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

import com.airarena.hibernate.util.MyEntityManagerFactory;

@Entity
@Table( name = "product_attribute_meta_keys" )
public class ProductAttributeMetaKey extends BaseModel {
	
    private Long id;

    private String name;
    private String description;    
    private Long provider_id = 1L; //default it will be aws category.
    private int is_valid = 1;
//    private Date created_at;
//    private Date updated_at;	
    
    private static HashMap<String, ProductAttributeMetaKey> keys = null;

	public ProductAttributeMetaKey() {
		// this form used by Hibernate
	}
	
	public ProductAttributeMetaKey(String name, String description) {
		this.name = name;
		this.description = description;
//		this.updated_at = this.created_at = new Date();		
	}	

	public static HashMap<String, ProductAttributeMetaKey> loadKeys() {
		ProductAttributeMetaKey.keys = new HashMap<String, ProductAttributeMetaKey>();
		
		EntityManager em = MyEntityManagerFactory.getInstance();
		List<ProductAttributeMetaKey> metaKeys = em.createQuery("from " + ProductAttributeMetaKey.class.getName(), ProductAttributeMetaKey.class).getResultList();
		for (ProductAttributeMetaKey metaKey : metaKeys) {
			keys.put(metaKey.getName().toLowerCase(), metaKey);
		}
		return keys;
	}
	
	public static ProductAttributeMetaKey getProductAttributeByName(String name) {
        if (keys == null) {
        	loadKeys();
        }
        return keys.get(name.toLowerCase());
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

	@org.hibernate.annotations.Index(name = "myProductAttributeNameIndex")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getProvider_id() {
		return provider_id;
	}

	public void setProvider_id(Long provider_id) {
		this.provider_id = provider_id;
	}
	
	
	public int getIs_valid() {
		return is_valid;
	}

	public void setIs_valid(int is_valid) {
		this.is_valid = is_valid;
	}

	/*
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
