package qrparking.service.ticket;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import qrparking.models.pagamento.vo.ComprovanteVO;
import qrparking.models.pagamento.vo.PagamentoVO;
import qrparking.models.parametro.Parametro;
import qrparking.models.relatorio.RelatorioFinanceiro;
import qrparking.models.ticket.Ticket;
import qrparking.models.ticket.TicketDao;
import qrparking.service.parametro.ParametroService;
import qrparking.service.relatorio.RelatorioService;
import qrparking.service.util.EnviarEmail;

@Service
public class TicketService {

	private static final String TICKET_UTILIZADO = "Este ticket já foi utilizado.";
	private static final String TICKET_INVALIDO = "Ticket inválido, procure a administração.";
	private static final String TRANSACAO_NAO_AUTORIZADA = "Transação não autorizada.";
	private static final String TICKET_NAO_PAGO = "Ticket não pago, saída não permitida. Valor devido: ";
	private static final String TOLERANCIA_VENCIDA = "Tolerância de saída vencida, pague o ticket novamente. Valor devido: ";
	private static final String SAIDA_LIBERADA = "Saída liberada.";

	@Autowired
	private TicketDao ticketDao;

	@Autowired
	private RelatorioService relatorioService;

	@Autowired
	private ParametroService parametroService;

	@Autowired
	private EnviarEmail emailService;

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

	public Map<Object, Object> calcular(Long ticketId) {
		Ticket ticket = this.buscarPorId(ticketId);
		validarExistenciaTicket(ticket);
		validarSeJaSaiu(ticket);
		Parametro parametro = parametroService.buscarAtual();
		RelatorioFinanceiro relatorioFinanceiro = relatorioService.buscarPorIdTicket(ticketId);

		long permanencia = getPermanencia(ticket, relatorioFinanceiro);
		String valor = null;

		Map<Object, Object> calculo = new HashMap<>();

		if (!isTicketNaTolerancia(parametro, permanencia)) {
			valor = getValorCalculado(parametro, permanencia);
		}

		calculo.put("valor", valor);
		calculo.put("permanencia", permanencia);
		return calculo;
	}

	private void validarExistenciaTicket(Ticket ticket) {
		if (ticket == null) {
			throw new IllegalArgumentException(TICKET_INVALIDO);
		}
	}

	private boolean isTicketNaTolerancia(Parametro parametro, long permanencia) {
		return permanencia <= parametro.getTolerancia();
	}

	private String getValorCalculado(Parametro parametro, long permanencia) {
		DecimalFormat df = new DecimalFormat("0.##");
		return df.format(parametro.getValorMinuto().doubleValue() * permanencia);
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

	public Map<String, String> valiarSaida(Long ticketId) {
		Ticket ticket = this.buscarPorId(ticketId);
		validarExistenciaTicket(ticket);
		validarSeJaSaiu(ticket);
		RelatorioFinanceiro relatorioFinanceiro = relatorioService.buscarPorIdTicket(ticketId);
		Parametro parametro = parametroService.buscarAtual();
		long permanencia = getPermanencia(ticket, relatorioFinanceiro);
		String valorDevido = getValorCalculado(parametro, permanencia);
		Map<String, String> retorno = new HashMap<String, String>();

		if (isTicketNaTolerancia(parametro, permanencia)) {
			retorno.put("valor", SAIDA_LIBERADA);
			ticket.setDtSaida(new Date());
			ticketDao.salvar(ticket);
			return retorno;
		}

		if (isTicketPago(relatorioFinanceiro) && !isTicketNaTolerancia(parametro, permanencia)) {
			throw new IllegalArgumentException(TOLERANCIA_VENCIDA + valorDevido);
		}

		throw new IllegalArgumentException(TICKET_NAO_PAGO + valorDevido);
	}

	private void validarSeJaSaiu(Ticket ticket) {
		if (ticket.getDtSaida() != null) {
			throw new IllegalArgumentException(TICKET_UTILIZADO);
		}
	}

	public RelatorioFinanceiro pagar(PagamentoVO dadosPagamento) {
		// Simulando uma negação de transação
		if (dadosPagamento.cartao.equalsIgnoreCase("0")) {
			throw new IllegalArgumentException(TRANSACAO_NAO_AUTORIZADA);
		}
		;

		return relatorioService.financeiroSalvar(dadosPagamento);
	}

	public void enviarComprovante(ComprovanteVO comprovanteVO) {
		try {
			RelatorioFinanceiro relatorioFinanceiro = relatorioService.buscarPorId(comprovanteVO.getIdPagamento());
			StringBuilder sb = new StringBuilder();
			sb.append("==== Dados de pagamento\n").append("\nValor :" + relatorioFinanceiro.getValorPago())
					.append("\nData de pagamento: " + relatorioFinanceiro.getDtPagamento());
			System.out.println("=====> Conteuddo email \n" + sb.toString());
			emailService.enviarEmail(comprovanteVO.getEmail(), sb.toString());
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}
}
