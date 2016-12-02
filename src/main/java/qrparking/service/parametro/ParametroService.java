package qrparking.service.parametro;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import qrparking.models.parametro.Parametro;
import qrparking.models.parametro.ParametroDao;

@Service
public class ParametroService {

	@Autowired
	private ParametroDao parametroDao;

	public Parametro buscarPorId(@PathVariable("parametroId") Long parametroId) {
		Parametro parametro = parametroDao.getById(parametroId);
		return parametro;
	}

	public void salvar(Parametro parametro) {
		Parametro novoParametro = new Parametro();
		novoParametro.setJustificativa(parametro.getJustificativa());
		novoParametro.setTolerancia(parametro.getTolerancia());
		novoParametro.setValorMinuto(parametro.getValorMinuto());
		novoParametro.setDtAlteracao(new Date());
		novoParametro.setAdministrador(parametro.getAdministrador());
		parametroDao.salvar(novoParametro);
	}

	public Parametro buscarAtual() {
		Parametro parametro = parametroDao.buscarAtual();
		return parametro;
	}
}
