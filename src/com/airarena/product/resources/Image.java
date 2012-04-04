package com.airarena.product.resources;

import com.airarena.products.model.Product;
import com.airarena.products.model.ProductImage;

public class Image extends BaseResource {
	public static final String CATEGORY_THUMBNAIL = "thumbnail";
	public static final String CATEGORY_TINY = "tiny";
	public static final String CATEGORY_SAMLL = "small";
	public static final String CATEGORY_MEDIUM = "medium";
	public static final String CATEGORY_LARGE = "large";
	public static final String CATEGORY_SWATCH = "SwatchImage";
	
	
	public Image() {
		
	}
	
	public Image(String url, int height, int weight, String imageCategory) {
		super();
		this.url = url;
		this.height = height;
		this.weight = weight;
		this.imageCategory = imageCategory;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public String getImageCategory() {
		return imageCategory;
	}
	public void setImageCategory(String imageCategory) {
		this.imageCategory = imageCategory;
	}
	private String url;
	private int height;
	private int weight;
	private String imageCategory;

	@Override
	public String toString() {
		return "Image [url=" + url + ", height=" + height + ", weight="
				+ weight + ", imageCategory=" + imageCategory + "]";
	}
	
	
	public ProductImage toProductImage(Product p) {
		return new ProductImage(p, this, 1);
	}
	
	
}
