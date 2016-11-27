package qrparking.models.relatorio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import qrparking.models.pagamento.vo.FiltrosVO;
import qrparking.models.parametro.Parametro;

@Repository
@Transactional
public class RelatorioDao {

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
	public List<RelatorioFinanceiro> financeiroBuscarPorPeriodo(FiltrosVO parametro) {
		String where = definirWhereFinanceiro(parametro);
		Query relatorio = entityManager.createQuery("select r from RelatorioFinanceiro r " + where);
		definirParametros(parametro, relatorio);
		
		return relatorio.getResultList();
	}

	private String definirWhereFinanceiro(FiltrosVO parametro) {
		String where = "";
		if (parametro.getDataInicial() != null && parametro.getDataFinal() != null) {
			where = "where r.dtPagamento BETWEEN :dataInicio AND :dataFim";
		}
		return where;
	}

	private void definirParametros(FiltrosVO parametro, Query relatorio) {
		if (parametro.getDataInicial() != null && parametro.getDataFinal() != null) {
			relatorio
			.setParameter("dataInicio", parametro.getDataInicial())
			.setParameter("dataFim", parametro.getDataFinal());
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Parametro> parametroBuscarPorPeriodo(FiltrosVO parametro) {
		String where = definirWhereParametro(parametro);
		Query relatorio = entityManager.createQuery("select p from Parametro p " + where);
		definirParametros(parametro, relatorio);
		return relatorio.getResultList();
	}
	
	private String definirWhereParametro(FiltrosVO filtros) {
		String where = "";
		if (filtros.getDataInicial() != null && filtros.getDataFinal() != null) {
			where = "where p.dtAlteracao BETWEEN :dataInicio AND :dataFim";
		}
		return where;
	}

}
