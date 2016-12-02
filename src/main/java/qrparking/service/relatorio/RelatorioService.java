package qrparking.service.relatorio;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import qrparking.models.pagamento.vo.FiltrosVO;
import qrparking.models.pagamento.vo.PagamentoVO;
import qrparking.models.parametro.Parametro;
import qrparking.models.relatorio.RelatorioFinanceiro;
import qrparking.models.relatorio.RelatorioDao;

@Service
public class RelatorioService {

	private static final String DATA_FINAL_MENOR_INICIAL = "A data final não pode ser menor do que a inicial";
	@Autowired
	private RelatorioDao relatorioDao;

	public RelatorioFinanceiro financeiroSalvar(PagamentoVO dadosPagamento) {
		RelatorioFinanceiro relatorioFinanceiro = new RelatorioFinanceiro();
		relatorioFinanceiro.setTicket(dadosPagamento.getTicket());
		relatorioFinanceiro.setValorPago(dadosPagamento.getValor());
		relatorioFinanceiro.setDtPagamento(new Date());
		return relatorioDao.financeiroSalvar(relatorioFinanceiro);
	}

	public RelatorioFinanceiro buscarPorIdTicket(Long ticketId) {
		return relatorioDao.buscarUltimoPagamentoPorIdTicket(ticketId);
	}

	public List<RelatorioFinanceiro> financeiroBuscarPorPeriodo(FiltrosVO parametro) {
		if(parametro.getDataInicial() != null && parametro.getDataFinal() != null 
				&& parametro.getDataFinal().getTime() < parametro.getDataInicial().getTime()){
			throw new IllegalArgumentException(DATA_FINAL_MENOR_INICIAL);
		}
		parametro.setDataFinal(new DateTime(parametro.dataFinal).plusDays(1).toDate());
		return relatorioDao.financeiroBuscarPorPeriodo(parametro);
	}
	
	public List<Parametro> parametroBuscarPorPeriodo(FiltrosVO parametro) {
		if(parametro.getDataInicial() != null && parametro.getDataFinal() != null 
				&& parametro.getDataFinal().getTime() < parametro.getDataInicial().getTime()){
			throw new IllegalArgumentException(DATA_FINAL_MENOR_INICIAL);
		}
		parametro.setDataFinal(new DateTime(parametro.dataFinal).plusDays(1).toDate());
		return relatorioDao.parametroBuscarPorPeriodo(parametro);
	}

	public RelatorioFinanceiro buscarPorId(Long idPagamento) {
		return relatorioDao.financeiroGetById(idPagamento);
	}
}
