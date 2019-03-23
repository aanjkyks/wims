package bootcamp.wims.dbControl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public enum PersistenceManager {

	INSTANCE;

	private EntityManagerFactory emFactory;
	private EntityManager manager;

	private PersistenceManager() {

		emFactory = Persistence.createEntityManagerFactory("database_test");

	}

	public EntityManager getEntityManager() {
		if(manager == null) manager = emFactory.createEntityManager();
		return manager;

	}

	public void close() {

//		emFactory.close();

	}

}
