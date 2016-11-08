package qrparking.models.administrador;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

/**
 * This class is used to access data for the User entity.
 * Repository annotation allows the component scanning support to find and 
 * configure the DAO wihtout any XML configuration and also provide the Spring 
 * exceptiom translation.
 * Since we've setup setPackagesToScan and transaction manager on
 * DatabaseConfig, any bean method annotated with Transactional will cause
 * Spring to magically call begin() and commit() at the start/end of the
 * method. If exception occurs it will also call rollback().
 */
@Repository
@Transactional
public class AdministradorDao {
  
  // ------------------------
  // PUBLIC METHODS
  // ------------------------
  
  public void create(Administrador administrador) {
    entityManager.persist(administrador);
    return;
  }
  
  @SuppressWarnings("unchecked")
  public List<Administrador> getAll() {
    return entityManager.createQuery("from Administrador").getResultList();
  }

  public Administrador getById(long id) {
    return entityManager.find(Administrador.class, id);
  }

  public void update(Administrador administrador) {
    entityManager.merge(administrador);
    return;
  }

  // ------------------------
  // PRIVATE FIELDS
  // ------------------------
  
  // An EntityManager will be automatically injected from entityManagerFactory
  // setup on DatabaseConfig class.
  @PersistenceContext
  private EntityManager entityManager;
  
} // class UserDao
