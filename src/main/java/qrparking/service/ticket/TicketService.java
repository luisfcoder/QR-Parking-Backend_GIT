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
import qrparking.models.relatorio.RelatorioFinanceiro;
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
		Parametro parametro = parametroService.buscarAtual();
		RelatorioFinanceiro relatorioFinanceiro = relatorioService.buscarPorIdTicket(ticketId);

		long permanencia = getPermanencia(ticket, relatorioFinanceiro);
		double valor = 0L;

		Map<String, Number> calculo = new HashMap<String, Number>();

		if (!isTicketNaTolerancia(parametro, permanencia)) {
			valor = getValorCalculado(parametro, permanencia);
		}

		calculo.put("valor", valor);
		calculo.put("permanencia", permanencia);
		return calculo;
	}

	private boolean isTicketNaTolerancia(Parametro parametro, long permanencia) {
		return permanencia <= parametro.getTolerancia();
	}

	private double getValorCalculado(Parametro parametro, long permanencia) {
		return parametro.getValorMinuto().doubleValue() * permanencia;
	}

	private long getPermanencia(Ticket ticket, RelatorioFinanceiro relatorioFinanceiro) {
		long permanencia;
		if (!isTicketPago(relatorioFinanceiro)) {
			permanencia = Minutes.minutesBetween(new DateTime(ticket.getDtEntrada()), new DateTime()).getMinutes();
		} else {
			permanencia = Minutes.minutesBetween(new DateTime(relatorioFinanceiro.getDtPagamento()), new DateTime())
					.getMinutes();
		}
		return permanencia;
	}

	private Boolean isTicketPago(RelatorioFinanceiro relatorioFinanceiro) {
		if (relatorioFinanceiro != null) {
			return true;
		}
		return false;
	}

	public String valiarSaida(Long ticketId) {
		Ticket ticket = this.buscarPorId(ticketId);
		RelatorioFinanceiro relatorioFinanceiro = relatorioService.buscarPorIdTicket(ticketId);
		Parametro parametro = parametroService.buscarAtual();
		long permanencia = getPermanencia(ticket, relatorioFinanceiro);
		double valorDevido = getValorCalculado(parametro, permanencia);
		
		if (isTicketNaTolerancia(parametro, permanencia)) {
			return "Saída liberada.";
		}

		if (isTicketPago(relatorioFinanceiro) && !isTicketNaTolerancia(parametro, permanencia)) {
				throw new IllegalArgumentException("Tolerância de saída vencida, pague o ticket novamente. Valor devido: " + valorDevido);
		}
		
		throw new IllegalArgumentException("Ticket não pago, saída não permitida. Valor devido: " + valorDevido);
	}

	public void pagar(PagamentoVO dadosPagamento) {
		// Simulando uma negação de transação
		if (dadosPagamento.cartao.equalsIgnoreCase("0")) {
			throw new IllegalArgumentException("Transação não autorizada.");
		}
		;

		relatorioService.financeiroSalvar(dadosPagamento);
	}
}
