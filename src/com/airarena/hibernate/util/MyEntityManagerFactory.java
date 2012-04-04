package com.airarena.hibernate.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.airarena.products.model.BaseModel;
import com.airarena.products.model.Provider;



public class MyEntityManagerFactory {
	private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory( MyEntityManagerFactory.JPA_UNIT );	
	private static final String JPA_UNIT = "com.airarena.products";
	private static HashMap<String, EntityManager> emList = new HashMap<String, EntityManager>();
	public static final String DEFAULT_INSTANCE_NAME = "main";
	
	
	public static EntityManager getInstance(String providerName) {
		if (emList.containsKey(providerName.toLowerCase())) {
			return emList.get(providerName.toLowerCase());
		}
		
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		emList.put(providerName.toLowerCase(), entityManager);
		return entityManager;
				
	}
	
	public static EntityManager getInstance() {
		String providerName = DEFAULT_INSTANCE_NAME;
		if (emList.containsKey(providerName.toLowerCase())) {
			return emList.get(providerName.toLowerCase());
		}
		
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		emList.put(providerName, entityManager);
		return entityManager;
				
	}


	
	
	public static void main(String[] args) {

	}
	
	
	public static void releaseSession() {
		for(String provider : emList.keySet()) {
			EntityManager entityManager = emList.get(provider);
			entityManager.close();
		}
		emList.clear();
		entityManagerFactory.close();
		entityManagerFactory = Persistence.createEntityManagerFactory( MyEntityManagerFactory.JPA_UNIT );		
	}
	


	
//	public EntityManagerFactory getEntityManagerFactory() {
//		if (entityManagerFactory == null) {
//			entityManagerFactory = Persistence.createEntityManagerFactory( SessionService.JPA_UNIT );
//		}
//		return entityManagerFactory;
//	}

//	public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
//		this.entityManagerFactory = entityManagerFactory;
//	}

//	public void testBasicUsage() {
//		entityManagerFactory = Persistence.createEntityManagerFactory( SessionService.JPA_UNIT );
//		// create a couple of events...
//		EntityManager entityManager = entityManagerFactory.createEntityManager();
//		entityManager.getTransaction().begin();
//		entityManager.persist( new Provider( "aws" ) );
////		entityManager.persist( new Event( "A follow up event", new Date() ) );
//		entityManager.getTransaction().commit();
//		entityManager.close();
//
//		// now lets pull events from the database and list them
//		entityManager = entityManagerFactory.createEntityManager();
//		entityManager.getTransaction().begin();
//        List<Provider> result = entityManager.createQuery( "from Provider", Provider.class ).getResultList();
//		for ( Provider p : result ) {
//			_logger.info( "Provider (" + p.getName() + ") : " + p.getCreated_at() );
//		}
//        entityManager.getTransaction().commit();
//        entityManager.close();
//        entityManagerFactory.close();
//	}	
}
