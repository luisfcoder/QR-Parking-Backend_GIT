package qrparking.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import qrparking.models.Administrador;
import qrparking.models.AdministradorDao;
import qrparking.service.Constantes;

/**
 * Class AdministradorController
 */
@RestController
@CrossOrigin(origins = Constantes.HOME_IONIC)
public class AdministradorController {

  // ------------------------
  // PUBLIC METHODS
  // ------------------------

  /**
   * Create a new administrador with an auto-generated id and email and name as passed 
   * values.
   */
  @RequestMapping(value="/create")
  @ResponseBody
  public String create(String name, String email) {
    try {
      Administrador administrador = new Administrador(name, email);
      administradorDao.create(administrador);
    }
    catch (Exception ex) {
      return "Error creating the administrador: " + ex.toString();
    }
    return "Administrador succesfully created!";
  }
  
  /**
   * Delete the administrador with the passed id.
   */
  @RequestMapping(value="/delete")
  @ResponseBody
  public String delete(long id) {
    try {
      Administrador administrador = new Administrador(id);
      administradorDao.delete(administrador);
    }
    catch (Exception ex) {
      return "Error deleting the administrador: " + ex.toString();
    }
    return "Administrador succesfully deleted!";
  }
  
  /**
   * Retrieve the id for the administrador with the passed email address.
   */
  @RequestMapping(value="/get-by-email")
  @ResponseBody
  public String getByEmail(String email) {
    String administradorId;
    try {
      Administrador administrador = administradorDao.getByEmail(email);
      administradorId = String.valueOf(administrador.getId());
    }
    catch (Exception ex) {
      return "Administrador not found: " + ex.toString();
    }
    return "The administrador id is: " + administradorId;
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
  
  /**
   * Update the email and the name for the administrador indentified by the passed id.
   */
  @RequestMapping(value="/getAll", method = RequestMethod.POST)
  public void updateName(@RequestBody Administrador administrador) {
    	administradorDao.update(administrador);
  } 

  // ------------------------
  // PRIVATE FIELDS
  // ------------------------
  
  // Wire the AdministradorDao used inside this controller.
  @Autowired
  private AdministradorDao administradorDao;
  
} // class AdministradorController
