package com.airarena.product.resources;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.airarena.products.model.Product;
import com.airarena.products.model.ProductReview;

public class Review extends BaseResource {
	

	
	private int totalReviewsAmount = 0;
	private int fiveStarAmount = 0;
	private int fourStarAmount = 0;
	private int threeStarAmount = 0;
	private int twoStarAmount = 0;
	private int oneStarAmount = 0;
	private float averageRate;
	
	private List<ReviewDetail> rds = new ArrayList<ReviewDetail>();
	
	public boolean isValidate(){
		if (totalReviewsAmount != this.fiveStarAmount + this.fourStarAmount + this.threeStarAmount + this.twoStarAmount + this.oneStarAmount) {
			return false;
		}
		if ((this.totalReviewsAmount != 0 && this.averageRate == 0) || (this.totalReviewsAmount == 0 && this.averageRate != 0)) {
			return false;
		}
		if ((this.totalReviewsAmount == 0 && rds.size() > 0) || (this.totalReviewsAmount > 0 && rds.isEmpty())) {
			return false;
		}
		for( ReviewDetail rd : rds) {
			if (!rd.isValidate()) return false;
		}
		
		return true;
	}

	public ReviewDetail initReviewDetail(){
		return new ReviewDetail();
	}
	
	public int getTotalReviewsAmount() {
		return totalReviewsAmount;
	}


	public void setTotalReviewsAmount(int totalReviewsAmount) {
		this.totalReviewsAmount = totalReviewsAmount;
	}


	public int getFiveStarAmount() {
		return fiveStarAmount;
	}


	public void setFiveStarAmount(int fiveStarAmount) {
		this.fiveStarAmount = fiveStarAmount;
	}


	public int getFourStarAmount() {
		return fourStarAmount;
	}


	public void setFourStarAmount(int fourStarAmount) {
		this.fourStarAmount = fourStarAmount;
	}


	public int getThreeStarAmount() {
		return threeStarAmount;
	}


	public void setThreeStarAmount(int threeStarAmount) {
		this.threeStarAmount = threeStarAmount;
	}


	public int getTwoStarAmount() {
		return twoStarAmount;
	}


	public void setTwoStarAmount(int twoStarAmount) {
		this.twoStarAmount = twoStarAmount;
	}


	public int getOneStarAmount() {
		return oneStarAmount;
	}


	public void setOneStarAmount(int oneStarAmount) {
		this.oneStarAmount = oneStarAmount;
	}


	public float getAverageRate() {
		return averageRate;
	}


	public void setAverageRate(float averageRate) {
		this.averageRate = averageRate;
	}


	public List<ReviewDetail> getRds() {
		return rds;
	}


	public void setRds(List<ReviewDetail> rds) {
		this.rds = rds;
	}


	public class ReviewDetail {
		private String helpful;
		private float rate = -1;
		private String reviewHtml;
		private Date submitAt;
		private String title;
		private String address;
		private String author;
		
		public String getAuthor() {
			return author;
		}
		public void setAuthor(String author) {
			this.author = author;
		}
		public Date getSubmitAt() {
			return submitAt;
		}
		public void setSubmitAt(Date submitAt) {
			this.submitAt = submitAt;
		}
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

		public String getReviewHtml() {
			return reviewHtml;
		}
		public void setReviewHtml(String reviewHtml) {
			this.reviewHtml = reviewHtml;
		}
		
		
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public boolean isValidate(){
			if (rate == -1 || author == null || this.reviewHtml == null || this.submitAt == null) {
				return false;
			}
			return true;
		}
		
		public boolean isNull() {
			if (this.getHelpful() == null && this.getRate() == -1 && this.submitAt == null && this.title == null && this.address == null && this.author == null) {
				return true;
			}
			return false;
			
		}
		
		
		@Override
		public String toString() {
			return "ReviewDetail [helpful=" + helpful + ", rate=" + rate
					+ ", reviewHtml=" + reviewHtml + ", submitAt=" + submitAt
					+ ", title=" + title + ", address=" + address + ", author="
					+ author + "]";
		}
		
				
		public ProductReview toProductReview(Product p) {
			return new ProductReview(this.getHelpful(), this.getRate(), this.getReviewHtml(), this.getSubmitAt(),
					this.getTitle(), this.getAddress(), this.getAuthor(), p);
			
		}
		
	}


	@Override
	public String toString() {
        StringBuilder rdList = new StringBuilder();
		for (ReviewDetail rd : this.rds) {
			rdList.append(rd.toString());
			rdList.append("\n");			
		}
		return "Review [totalReviewsAmount=" + totalReviewsAmount
				+ ", fiveStarAmount=" + fiveStarAmount + ", fourStarAmount="
				+ fourStarAmount + ", threeStarAmount=" + threeStarAmount
				+ ", twoStarAmount=" + twoStarAmount + ", oneStarAmount="
				+ oneStarAmount + ", averageRate=" + averageRate 
				+ rdList.toString() +
				"]";
	}
	
	
	
}
