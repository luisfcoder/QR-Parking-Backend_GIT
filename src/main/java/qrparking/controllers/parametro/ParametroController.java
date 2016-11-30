package qrparking.controllers.parametro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import qrparking.models.parametro.Parametro;
import qrparking.service.Constantes;
import qrparking.service.parametro.ParametroService;

@RestController
@RequestMapping(value = "/parametro")
public class ParametroController {

	@Autowired
	ParametroService parametroService;

	@RequestMapping(value = "/buscarAtual")
	@ResponseBody
	public Parametro buscarAtual() {
		return parametroService.buscarAtual();
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<HttpStatus> salvar(@RequestBody Parametro parametro) {
		parametroService.salvar(parametro);
		return ResponseEntity.ok(HttpStatus.OK);
	}

}
