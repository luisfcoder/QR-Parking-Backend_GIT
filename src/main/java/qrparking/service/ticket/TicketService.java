package qrparking.service.ticket;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import qrparking.models.ticket.Ticket;
import qrparking.models.ticket.TicketDao;

@Service
public class TicketService {

	@Autowired
	private TicketDao ticketDao;

	public Ticket buscarPorId(@PathVariable("ticketId") Long ticketId) {
		Ticket ticket = ticketDao.getById(ticketId);
		return ticket;
	}

	public void salvar(Ticket ticket) {
		ticketDao.salvar(ticket);
	}

	public Ticket gerarTicket() {
		Ticket ticket = new Ticket();
		ticket.setDtEntrada(new Date());

		return ticketDao.salvar(ticket);
	}
}
