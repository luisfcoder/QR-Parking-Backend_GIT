package qrparking.controllers.relatorios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import qrparking.models.relatorio.RelatorioFinanceiro;
import qrparking.service.Constantes;
import qrparking.service.relatorio.RelatorioService;

@RestController
@CrossOrigin(origins = Constantes.HOME_IONIC)
@RequestMapping(value = "/relatorio")
public class RelatorioFinanceiroController {
	
	@Autowired
	RelatorioService relatorioService;
	
	@RequestMapping(value = "financeiro/buscarTodos")
	@ResponseBody
	public List<RelatorioFinanceiro> financeiroBuscarTodos() {
		return relatorioService.buscarTodos();
	}

}
