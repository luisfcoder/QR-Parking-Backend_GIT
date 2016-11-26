package qrparking.service.ticket;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import qrparking.models.pagamento.vo.PagamentoVO;
import qrparking.models.parametro.Parametro;
import qrparking.models.ticket.Ticket;
import qrparking.models.ticket.TicketDao;
import qrparking.service.parametro.ParametroService;
import qrparking.service.relatorio.RelatorioService;

@Service
public class TicketService {

	@Autowired
	private TicketDao ticketDao;

	@Autowired
	private RelatorioService relatorioService;

	@Autowired
	private ParametroService parametroService;

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

	public Map<String, Number> calcular(Long ticketId) {
		Ticket ticket = this.buscarPorId(ticketId);
		Parametro parametro = parametroService.buscarPorId(1L);

		long permanenciaMinutos = Minutes.minutesBetween(new DateTime(ticket.getDtEntrada()), new DateTime())
				.getMinutes();
		double valor = 0L;

		Map<String, Number> calculo = new HashMap<String, Number>();

		if (permanenciaMinutos > parametro.getTolerancia()) {
			valor = parametro.getValorMinuto().doubleValue() * permanenciaMinutos;
		}

		calculo.put("valor", valor);
		calculo.put("permanencia", permanenciaMinutos);
		return calculo;
	}

	public void pagar(PagamentoVO dadosPagamento) {
		// Simulando uma negação de transação
		if (dadosPagamento.cartao.equalsIgnoreCase("0")) {
			throw new IllegalArgumentException("Transação não autorizada pelo cartão!");
		}
		;

		relatorioService.financeiroSalvar(dadosPagamento);
	}
}
