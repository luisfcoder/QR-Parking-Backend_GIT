package qrparking.service.relatorio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import qrparking.models.pagamento.vo.PagamentoVO;
import qrparking.models.relatorio.RelatorioFinanceiro;
import qrparking.models.relatorio.RelatorioFinanceiroDao;

@Service
public class RelatorioService {

	@Autowired
	private RelatorioFinanceiroDao relatorioDao;

	public List<RelatorioFinanceiro> buscarTodos() {
		List<RelatorioFinanceiro> relatorio = new ArrayList<RelatorioFinanceiro>();
		relatorio = relatorioDao.financeiroBuscarTodos();
		return relatorio;
	}

	public void financeiroSalvar(PagamentoVO dadosPagamento) {
		RelatorioFinanceiro relatorioFinanceiro = new RelatorioFinanceiro();
		relatorioFinanceiro.setTicket(dadosPagamento.getTicket());
		relatorioFinanceiro.setValorPago(dadosPagamento.getValor());
		relatorioFinanceiro.setDtPagamento(new Date());
		relatorioDao.financeiroSalvar(relatorioFinanceiro);
	}

	public RelatorioFinanceiro buscarPorIdTicket(Long ticketId) {
		return relatorioDao.buscarUltimoPagamentoPorIdTicket(ticketId);
	}
}
