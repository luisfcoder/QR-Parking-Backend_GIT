package qrparking.models.ticket;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class TicketDao {

	public Ticket salvar(Ticket ticket) {
		return entityManager.merge(ticket);
	}

	public Ticket getById(long id) {
		return entityManager.find(Ticket.class, id);
	}

	@PersistenceContext
	private EntityManager entityManager;

}
