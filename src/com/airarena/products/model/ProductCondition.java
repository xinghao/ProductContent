package com.airarena.products.model;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.persistence.NamedQuery;

import org.hibernate.annotations.GenericGenerator;

import com.airarena.hibernate.util.MyEntityManagerFactory;


@Entity
@Table( name = "product_conditions" )
@NamedQuery(name="productCondition.all", query="select n from ProductCondition n")
public class ProductCondition extends BaseModel {

	private Long id;
	private String name;
	
//    private int version;
//    
//    private Date created_at;
//    private Date updated_at;
    
    
    public static final String NEW = "New";
    public static final String USED = "Used";
    public static final String COLLECTIBLE = "Collectible";
    public static final String REFURBISHED = "Refurbished";
    
    
    private static HashMap<String, ProductCondition> keys = null;
    
	public ProductCondition() {
		// TODO Auto-generated constructor stub
	}
	
	
	public ProductCondition(String name) {
		super();
		this.name = name;
//		this.updated_at = this.created_at = new Date();		
	}

	public static HashMap<String, ProductCondition> loadKeys() {
		keys = new HashMap<String, ProductCondition>();
		
		EntityManager em = MyEntityManagerFactory.getInstance();
		 Query q = em.createNamedQuery("productCondition.all");
		List<ProductCondition> conditionKeys = q.getResultList();
		for (ProductCondition key : conditionKeys) {
			keys.put(key.getName().toLowerCase(), key);
		}
		
		return keys;
		
	}
	
	public static ProductCondition getProductConditionByName(String name) {
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
	
	@org.hibernate.annotations.Index(name = "myProductConditionNameIndex")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
/*	
    @Version
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
*/

	
}
