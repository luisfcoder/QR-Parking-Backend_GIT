package qrparking.models.relatorio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class RelatorioFinanceiroDao {

	@PersistenceContext
	private EntityManager entityManager;

	public RelatorioFinanceiro financeiroSalvar(RelatorioFinanceiro relatorioFinanceiro) {
		return entityManager.merge(relatorioFinanceiro);
	}

	@SuppressWarnings("unchecked")
	public List<RelatorioFinanceiro> financeiroBuscarTodos() {
		return entityManager.createQuery("from RelatorioFinanceiro").getResultList();
	}

	@SuppressWarnings("unchecked")
	public RelatorioFinanceiro buscarUltimoPagamentoPorIdTicket(Long ticketId) {
		List<RelatorioFinanceiro> resultado = entityManager
				.createQuery("select r from RelatorioFinanceiro r join r.ticket t where t.id = :ticketId order by r.dtPagamento desc")
				.setParameter("ticketId", ticketId).setMaxResults(1).getResultList();
		if (resultado.size() > 0) {
			return resultado.get(0);
		}

		return null;
	}

}
