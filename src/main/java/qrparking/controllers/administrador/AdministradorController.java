package qrparking.controllers.administrador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import qrparking.models.administrador.Administrador;
import qrparking.service.Constantes;
import qrparking.service.administrador.AdministradorService;

@RestController
@RequestMapping(value = "/administrador")
public class AdministradorController {

	@Autowired
	AdministradorService administradorService;

	@RequestMapping(value = "/buscarPorId/{administradorId}")
	@ResponseBody
	public Administrador getId(@PathVariable("administradorId") Long administradorId) {
		Administrador administrador = administradorService.buscarPorId(administradorId);
		return administrador;
	}

	@RequestMapping(value = "/buscarTodos")
	@ResponseBody
	public List<Administrador> getAll() {
		return administradorService.buscarTodos();
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<HttpStatus> salvar(@RequestBody Administrador administrador) {
		administradorService.salvar(administrador);
		return ResponseEntity.ok(HttpStatus.OK);
	}
}
