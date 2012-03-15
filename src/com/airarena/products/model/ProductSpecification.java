package com.airarena.products.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table( name = "product_specifications" )
public class ProductSpecification extends BaseModel {
	
    private Long id;
	
	private String name;
	private String value;
    private int is_valid = 1;
	private Product product;	
    
    
	public ProductSpecification() {
		// TODO Auto-generated constructor stub
	}
	
	
	public ProductSpecification(String name, String value,
			int is_valid, Product product) {
		super();
		this.name = name;
		this.value = value;
		this.is_valid = is_valid;
		this.product = product;
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



	

	@Type(type="text")
	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
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


	@ManyToOne
    @JoinColumn(name="product_id")		
    @org.hibernate.annotations.Index(name = "myProductSpecificationProductIdIndex")    
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	

}
