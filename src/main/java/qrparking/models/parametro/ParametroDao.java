package qrparking.models.parametro;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

/**
 * This class is used to access data for the User entity. Repository annotation
 * allows the component scanning support to find and configure the DAO wihtout
 * any XML configuration and also provide the Spring exceptiom translation.
 * Since we've setup setPackagesToScan and transaction manager on
 * DatabaseConfig, any bean method annotated with Transactional will cause
 * Spring to magically call begin() and commit() at the start/end of the method.
 * If exception occurs it will also call rollback().
 */
@Repository
@Transactional
public class ParametroDao {

	// ------------------------
	// PUBLIC METHODS
	// ------------------------

	public Parametro getById(long id) {
		return entityManager.find(Parametro.class, id);
	}

	public void salvar(Parametro parametro) {
		entityManager.persist(parametro);
		return;
	}

	// ------------------------
	// PRIVATE FIELDS
	// ------------------------

	@PersistenceContext
	private EntityManager entityManager;

	public Parametro buscarAtual() {
		return (Parametro) entityManager.createQuery("select p from Parametro p order by p.id desc")
				.setMaxResults(1)
				.getSingleResult();
	}

}
