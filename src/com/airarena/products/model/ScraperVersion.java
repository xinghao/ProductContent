package com.airarena.products.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class ScraperVersion implements Serializable {
	
	private long scraper_version;

	public long getScraper_version() {
		return scraper_version;
	}

	public void setScraper_version(long scraper_version) {
		this.scraper_version = scraper_version;
	}

	
	public ScraperVersion() {
		// TODO Auto-generated constructor stub
	}

	public ScraperVersion(long scraper_version) {
		super();
		if (scraper_version > 0) {
			this.scraper_version = scraper_version;
		} else {
			this.scraper_version = -1;
		}
	}
	
	
	
}
