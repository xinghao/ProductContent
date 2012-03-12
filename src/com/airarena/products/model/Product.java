package com.airarena.products.model;


import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Type;

import com.airarena.aws.products.api.util.ItemLookupResponse;
import com.airarena.hibernate.util.MyEntityManagerFactory;
import com.airarena.product.resources.Image;

@Entity
@Table( name = "products" )
public class Product extends BaseModel {
	
    private Long id;
    
	private String source_object_id;	
	private Long category_id;
	private String rawXmlContentUrl;
	private String rawHtmlContentUrl;
	private String descirption;
	private String reviewUrl;
	private String specificationUrl;
	private Long salesRank;
    private int is_valid = 1;
    private int version = 1;    
    private Date created_at;
    private Date updated_at;	

	
	public Product() {
	}

	
	
	public Product(String source_bject_id, Long category_id, String rawXmlContentUrl,
			String rawHtmlContentUrl, String descirption, String reviewUrl,
			String specificationUrl, Long salesRank, int is_valid, int version) {
		super();
		this.source_object_id = source_bject_id;
		this.category_id = category_id;
		this.rawXmlContentUrl = rawXmlContentUrl;
		this.rawHtmlContentUrl = rawHtmlContentUrl;
		this.descirption = descirption;
		this.reviewUrl = reviewUrl;
		this.specificationUrl = specificationUrl;
		this.salesRank = salesRank;
		this.is_valid = is_valid;
		this.version = version;
		this.updated_at = this.created_at = new Date();
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



	public String getSource_object_id() {
		return source_object_id;
	}



	public void setSource_object_id(String source_bject_id) {
		this.source_object_id = source_bject_id;
	}


	@org.hibernate.annotations.Index(name = "myProductCategoryIndex")
	public Long getCategory_id() {
		return category_id;
	}



	public void setCategory_id(Long category_id) {
		this.category_id = category_id;
	}


	@Type(type="text")
	public String getRawXmlContentUrl() {
		return rawXmlContentUrl;
	}



	public void setRawXmlContentUrl(String rawXmlContentUrl) {
		this.rawXmlContentUrl = rawXmlContentUrl;
	}


	@Type(type="text")
	public String getRawHtmlContentUrl() {
		return rawHtmlContentUrl;
	}



	public void setRawHtmlContentUrl(String rawHtmlContentUrl) {
		this.rawHtmlContentUrl = rawHtmlContentUrl;
	}


	@Type(type="text")
	public String getDescirption() {
		return descirption;
	}



	public void setDescirption(String descirption) {
		this.descirption = descirption;
	}


	@Type(type="text")
	public String getReviewUrl() {
		return reviewUrl;
	}


	public void setReviewUrl(String reviewUrl) {
		this.reviewUrl = reviewUrl;
	}


	@Type(type="text")
	public String getSpecificationUrl() {
		return specificationUrl;
	}



	public void setSpecificationUrl(String specificationUrl) {
		this.specificationUrl = specificationUrl;
	}



	public int getIs_valid() {
		return is_valid;
	}



	public void setIs_valid(int is_valid) {
		this.is_valid = is_valid;
	}



	public int getVersion() {
		return version;
	}



	public void setVersion(int version) {
		this.version = version;
	}
	
	

	public Long getSalesRank() {
		return salesRank;
	}



	public void setSalesRank(Long salesRank) {
		this.salesRank = salesRank;
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

	public static Product findBySourceOjbectId(String sourceObjectId) {
		try {
			EntityManager entityManager = MyEntityManagerFactory.getInstance();
			return entityManager.createQuery("from " + Product.class.getName() + " where source_object_id = :sourceObjectId", Product.class).setParameter("sourceObjectId", sourceObjectId).getSingleResult();
		} catch (NoResultException e) {
			return null;			
		}

	}

	public static Product createOrUpdateFromAwsApi(ItemLookupResponse ilr, int version) {

		EntityManager entityManager = MyEntityManagerFactory.getInstance();
		
		Product p = findBySourceOjbectId(ilr.getSourceObjectId());
		
		entityManager.getTransaction().begin();
		
		if (p != null) {
			
			// delete related images and attributes
			String hqlDelete1 = "delete " + ProductImage.class.getName() + " where product_id = :productId";
			int deletedEntities1 = entityManager.createQuery( hqlDelete1 )
			                            .setParameter( "productId", p.getId() )
			                            .executeUpdate();
			
			String hqlDelete2 = "delete " + ProductAttribute.class.getName() + " where product_id = :productId";
			int deletedEntities2 = entityManager.createQuery( hqlDelete2 )
			                            .setParameter( "productId", p.getId() )
			                            .executeUpdate();	
			
			p.category_id = ilr.getCategoryId();
			p.rawXmlContentUrl = ilr.getRawXmlContentUrl();
			p.rawHtmlContentUrl = ilr.getRawHtmlContentUrl();
			p.descirption = ilr.getDescirption();
			p.reviewUrl = ilr.getReviewUrl();
			p.specificationUrl = ilr.getSpecificationUrl();
			p.salesRank = ilr.getSalesRank();
			p.is_valid = 1;
			p.version = version;
			p.updated_at = new Date();					
		} else {								
		
			p = new Product(ilr.getSourceObjectId(), ilr.getCategoryId(), ilr.getRawXmlContentUrl(),
					ilr.getRawHtmlContentUrl(), ilr.getDescirption(), ilr.getReviewUrl(),
					ilr.getSpecificationUrl(), ilr.getSalesRank(), 1, version);
					
		}
		
		// save or updated product
		entityManager.persist(p);
		
		Iterator iterator = ilr.getImages().keySet().iterator();
	       
        while(iterator. hasNext()){   
        	String key = (String)iterator.next();
            Image image = (Image)ilr.getImages().get(key);
            entityManager.persist(new ProductImage(p.getId(), image, 1, 1));
        }
        
		iterator = ilr.getItemAttributes().keySet().iterator();
	       
        while(iterator. hasNext()){   
        	Long key = (Long)iterator.next();
            String value = (String)ilr.getItemAttributes().get(key);
            entityManager.persist(new ProductAttribute(p.getId(), key, value, 1, 1));
        }
        

        
        entityManager.getTransaction().commit();


		return p;
		
	}
	
	public static Long getMaxCategoryIdAlreadyExist(int version) {
		try {
			EntityManager entityManager = MyEntityManagerFactory.getInstance();
			entityManager.getTransaction().begin();
			
			
			Long maxId  = (Long) entityManager.createQuery("select max(category_id) from " + Product.class.getName() + " where version = :version", Long.class ).setParameter("version", version).getSingleResult();

			
	        entityManager.getTransaction().commit();
	        
	        return maxId;
	        
		}catch(NoResultException e) {
			return -1L;
		}		
	}
	
	public static void deleteAllProductsForCategory(int version, Long categoryId) {

		try {
	
	
			EntityManager entityManager = MyEntityManagerFactory.getInstance();
			entityManager.getTransaction().begin();
			
			
			List<Product> ps = entityManager.createQuery("from " + Product.class.getName()
					+ " where version = :version and category_id = :categoryId", Product.class ).setParameter("version", version).setParameter("categoryId", categoryId).getResultList();
			
			StringBuilder sb = new StringBuilder();
			sb.append(ps.get(0).getId());
			for(int i=1; i < ps.size(); ++i) {
				sb.append("," + ps.get(i).getId()); 
			}
			System.out.println(sb.toString());
			
			Query Imageuery = entityManager.createQuery("delete from ProductImage pi "
	                + "where pi.version = :version and pi.product_id in (" + sb.toString() + ")");
			Imageuery.setParameter("version", version);
			int deleted = Imageuery.executeUpdate();
			
			
	
			Query Atrributequery = entityManager.createQuery("delete from ProductAttribute pa "
					+ "where pa.version = :version and pa.product_id in (" + sb.toString() + ")");
			Atrributequery.setParameter("version", version);
			deleted = Atrributequery.executeUpdate();
			
			Query pquery = entityManager.createQuery("delete from Product p "
	                + "where p.version = :version and p.category_id = :categoryId");
			pquery.setParameter("version", version);
			pquery.setParameter("categoryId", categoryId);
			deleted = pquery.executeUpdate();
	
			
	        entityManager.getTransaction().commit();
		}catch(NoResultException e) {
			return;
		}

		
	}
}
