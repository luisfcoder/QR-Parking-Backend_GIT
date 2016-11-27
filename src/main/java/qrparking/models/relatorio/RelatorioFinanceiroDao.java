package qrparking.models.relatorio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import qrparking.models.pagamento.vo.FinanceiroFiltrosParametroVO;

@Repository
@Transactional
public class RelatorioFinanceiroDao {

	@PersistenceContext
	private EntityManager entityManager;

	public RelatorioFinanceiro financeiroSalvar(RelatorioFinanceiro relatorioFinanceiro) {
		return entityManager.merge(relatorioFinanceiro);
	}

	@SuppressWarnings("unchecked")
	public RelatorioFinanceiro buscarUltimoPagamentoPorIdTicket(Long ticketId) {
		List<RelatorioFinanceiro> resultado = entityManager
				.createQuery(
						"select r from RelatorioFinanceiro r join r.ticket t where t.id = :ticketId order by r.dtPagamento desc")
				.setParameter("ticketId", ticketId).setMaxResults(1).getResultList();
		if (resultado.size() > 0) {
			return resultado.get(0);
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public List<RelatorioFinanceiro> financeiroBuscarPorPeriodo(FinanceiroFiltrosParametroVO parametro) {
		String where = definirWhere(parametro);
		Query relatorio = entityManager.createQuery("select r from RelatorioFinanceiro r " + where);
		definirParametros(parametro, relatorio);
		
		return relatorio.getResultList();
	}

	private String definirWhere(FinanceiroFiltrosParametroVO parametro) {
		String where = "";
		if (parametro.getDataInicial() != null && parametro.getDataFinal() != null) {
			where = "where r.dtPagamento BETWEEN :dataInicio AND :dataFim";
		}
		return where;
	}

	private void definirParametros(FinanceiroFiltrosParametroVO parametro, Query relatorio) {
		if (parametro.getDataInicial() != null && parametro.getDataFinal() != null) {
			relatorio
			.setParameter("dataInicio", parametro.getDataInicial())
			.setParameter("dataFim", parametro.getDataFinal());
		}
	}

}
