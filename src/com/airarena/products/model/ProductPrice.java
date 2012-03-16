package com.airarena.products.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.CascadeType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table( name = "product_prices" )
public class ProductPrice extends BaseModel {
	private Long id;
	
	private ProductCondition productCondition;
	private long price;
	private String currency_code;
	private String formated_price;
	private int available = 0;
	private Product product;
	

//    private int version;    
//    private Date created_at;
//    private Date updated_at;
    
    
    
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")	
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}


	@ManyToOne
    @JoinColumn(name="product_id")
    @org.hibernate.annotations.Index(name = "myProductPriceProductIndex")
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	
	@ManyToOne
    @JoinColumn(name="condition_id")	
	public ProductCondition getProductCondition() {
		return productCondition;
	}
	public void setProductCondition(ProductCondition productCondition) {
		this.productCondition = productCondition;
	}

	
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	public int getAvailable() {
		return available;
	}
	public void setAvailable(int available) {
		this.available = available;
	}
	
	
	
	public String getCurrency_code() {
		return currency_code;
	}
	public void setCurrency_code(String currency_code) {
		this.currency_code = currency_code;
	}
	public String getFormated_price() {
		return formated_price;
	}
	public void setFormated_price(String formated_price) {
		this.formated_price = formated_price;
	}

	
//	@Version
//	public int getVersion() {
//		return version;
//	}
//	public void setVersion(int version) {
//		this.version = version;
//	}
//
//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name = "created_at")
//	public Date getCreated_at() {
//		return created_at;
//	}
//
//	public void setCreated_at(Date created_at) {
//		this.created_at = created_at;
//	}
//
//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name = "updated_at")
//	public Date getUpdated_at() {
//		return updated_at;
//	}
//
//	public void setUpdated_at(Date updated_at) {
//		this.updated_at = updated_at;
//	}
	
	
	
	
	
	public ProductPrice() {
		// TODO Auto-generated constructor stub
	}
	
	
	public ProductPrice(ProductCondition pc, long price, String currency_code,
			String formated_price, int available, Product p) {
		
		super();
		this.productCondition = pc;
		this.price = price;
		this.currency_code = currency_code;
		this.formated_price = formated_price;
		this.available = available;
		this.product = p;
//		this.updated_at = this.created_at = new Date();	

	}
	
	
	
	
	
	
}
