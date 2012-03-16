package com.airarena.products.model;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.airarena.aws.products.api.util.AwsApiException;
import com.airarena.hibernate.util.MyEntityManager;
import com.airarena.hibernate.util.MyEntityManagerFactory;
import com.airarena.scraper.ScrapingException;

@Entity
@Table( name = "scraping_status" )
@NamedQuery(name="scrapingStatus.maxVersion", query="select max(scraper_version) from ScrapingStatus")

public class ScrapingStatus extends BaseModel {
    public static final String SUCCESS = "success";
    public static final String ERROR = "error";
    public static final String RUNNING = "running";
    public static final String TEST_ERROR = "testing failed";
	
	private Long id;
    private ScraperVersion scraper_version;
    private String status;
    private String scraper_type = "aws";
    private Category category;
    
	public ScrapingStatus() {
		// TODO Auto-generated constructor stub
	}
	
	
	public ScrapingStatus(long scraper_version, String status) {
		super();
		this.scraper_version = new ScraperVersion(scraper_version);
		this.status = status;
		this.category = null;
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
	
	@Embedded
	public ScraperVersion getScraper_version() {
		return scraper_version;
	}
	public void setScraper_version(ScraperVersion scraper_version) {
		this.scraper_version = scraper_version;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	public String getScraper_type() {
		return scraper_type;
	}


	public void setScraper_type(String scraper_type) {
		this.scraper_type = scraper_type;
	}

	@ManyToOne
    @JoinColumn(name="category_id")	
	public Category getCategory() {
		return category;
	}


	public void setCategory(Category category) {
		this.category = category;
	}


	public static ScrapingStatus getMaxScrapingVersion() {		
		try {
	     	EntityManager entityManager = MyEntityManagerFactory.getInstance();
			Query q = entityManager.createNamedQuery("scrapingStatus.maxVersion");
			ScraperVersion sv = (ScraperVersion)q.getSingleResult();
			if (sv == null) return null;
			return entityManager.find(ScrapingStatus.class, sv.getScraper_version());
		} catch (NoResultException e) {
			return null;
		} catch (NumberFormatException e1) {
			return null;			
		}					
	}
	
	public static ScrapingStatus nextNewScrapingVersion(boolean ignoreError) throws ScrapingException {
		MyEntityManager s = new MyEntityManager();
		ScrapingStatus ss = getMaxScrapingVersion();
		if (ss == null) {
			return (ScrapingStatus)s.newModel(new ScrapingStatus(1L, RUNNING));
		} else if (ignoreError || ss.getStatus().equalsIgnoreCase(SUCCESS)) {
			return (ScrapingStatus)s.newModel(new ScrapingStatus(ss.getScraper_version().getScraper_version() + 1, RUNNING));
		} else {			
			throw new ScrapingException("The preview scraping is wrong or still running....");
		}
				
	}
	
	public ScrapingStatus save() {
		MyEntityManager s = new MyEntityManager();
		return (ScrapingStatus)s.saveModel(this);
	}
	

	public static Category getMaxCategoryIdAlreadyExist(long version) {
		try {
			EntityManager entityManager = MyEntityManagerFactory.getInstance();						
			return entityManager.createQuery("select max(category) from " + ScrapingStatus.class.getName() + " where  scraper_version= :scraperVersion", Category.class ).setParameter("scraperVersion", new ScraperVersion(version)).getSingleResult();				        
	        
	        
		}catch(NoResultException e) {
			return null;
		}		
	}
    
}
