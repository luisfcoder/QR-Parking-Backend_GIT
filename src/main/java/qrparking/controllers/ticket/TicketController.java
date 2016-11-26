package qrparking.controllers.ticket;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import qrparking.models.pagamento.vo.PagamentoVO;
import qrparking.models.ticket.Ticket;
import qrparking.service.Constantes;
import qrparking.service.ticket.TicketService;

@RestController
@CrossOrigin(origins = Constantes.HOME_IONIC)
@RequestMapping(value = "/ticket")
public class TicketController {

	@Autowired
	TicketService ticketService;

	@RequestMapping(value = "/buscarPorId/{ticketId}")
	@ResponseBody
	public Ticket getId(@PathVariable("ticketId") Long ticketId) {
		Ticket ticket = ticketService.buscarPorId(ticketId);
		return ticket;
	}
	
	@RequestMapping(value = "/gerar")
	@ResponseBody
	public Ticket gerar() {
		Ticket ticket = ticketService.gerarTicket();
		return ticket;
	}
	
	@RequestMapping(value = "/calcular/{ticketId}")
	@ResponseBody
	public Map<String, Number> calcular(@PathVariable("ticketId") Long ticketId) {
		return ticketService.calcular(ticketId);
	}
	
	@RequestMapping(value = "/validarSaida/{ticketId}")
	@ResponseBody
	public String validarSaida(@PathVariable("ticketId") Long ticketId) {
		return ticketService.valiarSaida(ticketId);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<HttpStatus> salvar(@RequestBody Ticket ticket) {
		ticketService.salvar(ticket);
		return ResponseEntity.ok(HttpStatus.OK);
	}
	
	@RequestMapping(value="/processarPagamento", method = RequestMethod.POST)
	public ResponseEntity<HttpStatus> processarPagamento(@RequestBody PagamentoVO dadosPagamento) {
		ticketService.pagar(dadosPagamento);
		return ResponseEntity.ok(HttpStatus.OK);
	}
}