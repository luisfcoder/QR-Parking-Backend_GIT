package qrparking.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import qrparking.models.Administrador;
import qrparking.models.AdministradorDao;
import qrparking.service.Constantes;


@RestController
@CrossOrigin(origins = Constantes.HOME_IONIC)
@RequestMapping(value="/administrador")
public class AdministradorController {

  // ------------------------
  // PUBLIC METHODS
  // ------------------------
 
  @RequestMapping(value="/getId/{administradorId}")
  @ResponseBody
  public Administrador getId(@PathVariable("administradorId") Long administradorId) {
      Administrador administrador = administradorDao.getById(administradorId);
	  return administrador;
  }
  
  @RequestMapping(value="/getAll")
  @ResponseBody
  public List<Administrador> getAll() {
	  List<Administrador> administrador = new ArrayList<Administrador>();
	  try {
      administrador = administradorDao.getAll();
    }
    catch (Exception ex) {
      ex.toString();
    }
    return administrador;
  }
  
  @RequestMapping(method = RequestMethod.POST)
  public void updateName(@RequestBody Administrador administrador) {
	  	if(administrador.getId() != 0){
	  		administradorDao.update(administrador);
	  	}else{
	  		administrador.setDtCadastro(new Date());
	  		administradorDao.create(administrador);
	  	}
  } 

  // ------------------------
  // PRIVATE FIELDS
  // ------------------------
  
  @Autowired
  private AdministradorDao administradorDao;
}
