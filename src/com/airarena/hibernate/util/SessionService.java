package com.airarena.hibernate.util;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.airarena.products.model.BaseModel;
import com.airarena.products.model.Provider;



public class SessionService {
	private EntityManagerFactory entityManagerFactory;	
	private static final String JPA_UNIT = "com.airarena.products";
	private static SessionService _instance;
	
	private SessionService() {
		//entityManagerFactory = Persistence.createEntityManagerFactory( SessionService.JPA_UNIT );				 		
	}
	
	public static SessionService getInstance() {
		return new SessionService();
		
//		if (SessionService._instance== null) {
//			SessionService._instance = new SessionService();
//		} 
//		return SessionService._instance;
		
	}
	
	public static void main(String[] args) {
		SessionService n = new SessionService();
		n.testBasicUsage();
	}
	
	public void newModel(BaseModel model) {
		entityManagerFactory = Persistence.createEntityManagerFactory( SessionService.JPA_UNIT );
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist( model );
		entityManager.getTransaction().commit();
		entityManager.close();
		entityManagerFactory.close();
	}
	
	public void query(BaseModel model, String queryString) {
		entityManagerFactory = Persistence.createEntityManagerFactory( SessionService.JPA_UNIT );
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
        List<Provider> result = entityManager.createQuery( "from Provider", Provider.class ).getResultList();
		for ( Provider p : result ) {
			System.out.println( "Provider (" + p.getName() + ") : " + p.getCreated_at() );
		}
        entityManager.getTransaction().commit();
        entityManager.close();		
        entityManagerFactory.close();
	}
	
	public void releaseSession() {
		entityManagerFactory.close();
	}
	


	
	public EntityManagerFactory getEntityManagerFactory() {
		if (entityManagerFactory == null) {
			entityManagerFactory = Persistence.createEntityManagerFactory( SessionService.JPA_UNIT );
		}
		return entityManagerFactory;
	}

//	public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
//		this.entityManagerFactory = entityManagerFactory;
//	}

	public void testBasicUsage() {
		entityManagerFactory = Persistence.createEntityManagerFactory( SessionService.JPA_UNIT );
		// create a couple of events...
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist( new Provider( "aws" ) );
//		entityManager.persist( new Event( "A follow up event", new Date() ) );
		entityManager.getTransaction().commit();
		entityManager.close();

		// now lets pull events from the database and list them
		entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
        List<Provider> result = entityManager.createQuery( "from Provider", Provider.class ).getResultList();
		for ( Provider p : result ) {
			System.out.println( "Provider (" + p.getName() + ") : " + p.getCreated_at() );
		}
        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();
	}	
}
