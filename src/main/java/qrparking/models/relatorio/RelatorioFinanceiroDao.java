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

}
