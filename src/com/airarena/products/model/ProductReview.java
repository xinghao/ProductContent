package com.airarena.products.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table( name = "product_reviews" )
public class ProductReview extends BaseModel {
	
    private Long id;
    
	private String helpful;
	private float rate = -1;
	private String reviewHtml;
	private Date submitAt;
	private String title;
	private String address;
	private String author;
	private Product product;
	
	public ProductReview() {
		// TODO Auto-generated constructor stub
	}
	
	public ProductReview(String helpful, float rate, String reviewHtml,
			Date submitAt, String title, String address, String author, Product product) {
		super();
		this.helpful = helpful;
		this.rate = rate;
		this.reviewHtml = reviewHtml;
		this.submitAt = submitAt;
		this.title = title;
		this.address = address;
		this.author = author;
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

	@ManyToOne
    @JoinColumn(name="product_id")	
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Type(type="text")
	public String getHelpful() {
		return helpful;
	}

	public void setHelpful(String helpful) {
		this.helpful = helpful;
	}

	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}

	@Type(type="text")
	public String getReviewHtml() {
		return reviewHtml;
	}

	public void setReviewHtml(String reviewHtml) {
		this.reviewHtml = reviewHtml;
	}

	public Date getSubmitAt() {
		return submitAt;
	}

	public void setSubmitAt(Date submitAt) {
		this.submitAt = submitAt;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	
	
	
}
