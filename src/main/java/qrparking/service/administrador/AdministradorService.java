package qrparking.service.administrador;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import qrparking.models.administrador.Administrador;
import qrparking.models.administrador.AdministradorDao;

@Service
public class AdministradorService {

	@Autowired
	private AdministradorDao administradorDao;

	public void salvar(@RequestBody Administrador administrador) {
		if (administrador.getId() == 0) {
			administrador.setDtCadastro(new Date());
		}
		administradorDao.update(administrador);
	}

	public List<Administrador> buscarTodos() {
		List<Administrador> administrador = new ArrayList<Administrador>();
		administrador = administradorDao.getAll();
		return administrador;
	}
	
	public Administrador buscarPorId(@PathVariable("administradorId") Long administradorId) {
		Administrador administrador = administradorDao.getById(administradorId);
		return administrador;
	}

}
