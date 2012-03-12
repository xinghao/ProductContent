package com.airarena.products.model;

import java.util.Date;
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

import com.airarena.hibernate.util.MyEntityManagerFactory;

@Entity
@Table( name = "categories" )
public class Category extends BaseModel{
    private Long id;

    private String name;
    private String permalink;
    private String source_object_id;
    private Long provider_id = 1L; //default it will be aws category.
    private Long parent_id;
    private int subcategories_count;
    private int version = 1;
    
    private Date created_at;
    private Date updated_at;

	public Category() {
		// this form used by Hibernate
	}

	public Category(String name, String source_object_id, Long provider_id, Long parent_id, int subcategories_count, int version) {
		// for application use, to create new events
		this.name = name;
		this.permalink = name;
		this.source_object_id = source_object_id;
		
		if (provider_id != null)
			this.provider_id = provider_id;
		
		
		this.parent_id = parent_id;
		this.subcategories_count = subcategories_count;
		if (version > 1)
			this.version = version;
		this.updated_at = this.created_at = new Date();
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
	
	public static List<Category> getLeafCatgories(Long categoryId) {
		try {
			EntityManager entityManager = MyEntityManagerFactory.getInstance();
			entityManager.getTransaction().begin();
			
			List<Category> results = entityManager.createQuery( "from " + Category.class.getName() + " where subcategories_count = 0 order by id", Category.class ).getResultList();
			
	        entityManager.getTransaction().commit();
	        
	        return results;
		}catch(NoResultException e) {
			return null;
		}
        
	}	
}
