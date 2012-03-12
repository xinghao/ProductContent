package com.airarena.hibernate.util;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import com.airarena.products.model.BaseModel;
import com.airarena.products.model.Provider;

public class MyEntityManager {
	private String threadName = MyEntityManagerFactory.DEFAULT_INSTANCE_NAME;
	
	public MyEntityManager(String threadName) {
		this.threadName = threadName;
	}

	public MyEntityManager() {
		
	}
	
	public EntityManager getNativeEntityManager() {
		return MyEntityManagerFactory.getInstance(this.threadName);
	}
	
	public void newModel(BaseModel model) {

		EntityManager entityManager = MyEntityManagerFactory.getInstance(this.threadName);
		entityManager.getTransaction().begin();
		entityManager.persist( model );
		entityManager.getTransaction().commit();
	}
	
	public void query(BaseModel model, String queryString) {
		EntityManager entityManager = MyEntityManagerFactory.getInstance(this.threadName);
		entityManager.getTransaction().begin();
        List<Provider> result = entityManager.createQuery( "from Provider", Provider.class ).getResultList();
		for ( Provider p : result ) {
			System.out.println( "Provider (" + p.getName() + ") : " + p.getCreated_at() );
		}
        entityManager.getTransaction().commit();
	}

}
