package qrparking.controllers.relatorios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import qrparking.models.pagamento.vo.FinanceiroFiltrosParametroVO;
import qrparking.models.relatorio.RelatorioFinanceiro;
import qrparking.service.Constantes;
import qrparking.service.relatorio.RelatorioService;

@RestController
@CrossOrigin(origins = Constantes.HOME_IONIC)
@RequestMapping(value = "/relatorio")
public class RelatorioFinanceiroController {
	
	@Autowired
	RelatorioService relatorioService;
	
	@RequestMapping(value = "financeiro/buscarRelatorioFinanceiroPorPeriodo", method = RequestMethod.POST)
	public List<RelatorioFinanceiro> financeiroBuscarPorPeriodo(@RequestBody FinanceiroFiltrosParametroVO parametro) {
		return relatorioService.financeiroBuscarPorPeriodo(parametro);
	}

}
