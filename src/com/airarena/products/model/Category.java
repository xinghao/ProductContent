package com.airarena.products.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.NoResultException;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.airarena.aws.products.api.util.BasicApiRequest;
import com.airarena.aws.products.api.util.BrowserNodelLookupResponse;
import com.airarena.aws.products.api.util.ItemLookupResponse;
import com.airarena.hibernate.util.MyEntityManagerFactory;

@Entity
@Table( name = "categories" )
@NamedQuery(name="category.findBySourceObjectId", query="select n from Category n where source_object_id = :sourceObjectId")
public class Category extends BaseModel{
    private Long id;

    private String name;
    private String permalink;
    private String source_object_id;
    private Long provider_id = 1L; //default it will be aws category.
    private Long parent_id;
    private int subcategories_count;
    private ScraperVersion scraper_version;
    private int is_valid = 1;
    
    private Set<Product> products;
//    private int version = 1;
//    
//    private Date created_at;
//    private Date updated_at;

	public Category() {
		// this form used by Hibernate
	}

	public Category(String name, String source_object_id, Long provider_id, Long parent_id, int subcategories_count, int is_valid, long version) {
		// for application use, to create new events
		this.name = name;
		this.permalink = name;
		this.source_object_id = source_object_id;
		
		if (provider_id != null)
			this.provider_id = provider_id;
		
		
		this.parent_id = parent_id;
		this.subcategories_count = subcategories_count;
		this.scraper_version = new ScraperVersion(version);
		this.is_valid = is_valid;
//		if (version > 1)
//			this.version = version;
//		this.updated_at = this.created_at = new Date();
	}
	
	public static Category findBySourceOjbectId(String sourceObjectId) {
		try {
			EntityManager entityManager = MyEntityManagerFactory.getInstance();
			 Query q = entityManager.createNamedQuery("category.findBySourceObjectId");
			 return (Category) q.setParameter("sourceObjectId", sourceObjectId).getSingleResult();
		} catch (NoResultException e) {
			return null;			
		}

	}

	
	public static Category createOrUpdateFromAwsApi(BrowserNodelLookupResponse rnlr, Long parent_id, Long provider_id, long version, boolean overrider) {

		EntityManager entityManager = MyEntityManagerFactory.getInstance();
		
		Category c = findBySourceOjbectId(rnlr.getSourceObjectId());
		
		entityManager.getTransaction().begin();
		
		if (c == null) {
			c = new Category();
		} 
		c.setIs_valid(1);
		c.setName(rnlr.getName());
		c.setParent_id(parent_id);
		c.setProvider_id(provider_id);
		c.setScraper_version(new ScraperVersion(version));
		c.setSource_object_id(rnlr.getSourceObjectId());
		c.setSubcategories_count(rnlr.getChildrenSourceObjectId().size());
		c.setPermalink(BasicApiRequest.getPermalink(c.getName()));
		entityManager.persist(c);
		
		entityManager.getTransaction().commit();
		return c;
	}
		

	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
    public Long getId() {
		return id;
    }

    private void setId(Long id) {
		this.id = id;
    }
    
	public int getIs_valid() {
		return is_valid;
	}

	public void setIs_valid(int is_valid) {
		this.is_valid = is_valid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPermalink() {
		return permalink;
	}

	public void setPermalink(String permalink) {
		this.permalink = permalink;
	}

	@org.hibernate.annotations.Index(name = "myCategorySourceObjectIdIndex")
	public String getSource_object_id() {
		return source_object_id;
	}

	public void setSource_object_id(String source_object_id) {
		this.source_object_id = source_object_id;
	}

	public Long getProvider_id() {
		return provider_id;
	}

	public void setProvider_id(Long provider_id) {
		this.provider_id = provider_id;
	}

	public Long getParent_id() {
		return parent_id;
	}

	public void setParent_id(Long parent_id) {
		this.parent_id = parent_id;
	}


	
	public int getSubcategories_count() {
		return subcategories_count;
	}

	public void setSubcategories_count(int subcategories_count) {
		this.subcategories_count = subcategories_count;
	}
	
	

//	public int getVersion() {
//		return version;
//	}
//
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
	
	@ManyToMany(mappedBy="categories", cascade=CascadeType.ALL, fetch=FetchType.LAZY)	
	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	@Embedded
	public ScraperVersion getScraper_version() {
		return scraper_version;
	}

	public void setScraper_version(ScraperVersion scraper_version) {
		this.scraper_version = scraper_version;
	}

	public static List<Category> getLeafCatgories(long version) {
		try {
			EntityManager entityManager = MyEntityManagerFactory.getInstance();
			entityManager.getTransaction().begin();
			
			List<Category> results = entityManager.createQuery( "from " + Category.class.getName() + " where subcategories_count = 0 and scraper_version = :scraperVersion order by id", Category.class ).setParameter("scraperVersion", new ScraperVersion(version)).getResultList();
			
	        entityManager.getTransaction().commit();
	        
	        return results;
		}catch(NoResultException e) {
			return null;
		}
        
	}	
}
