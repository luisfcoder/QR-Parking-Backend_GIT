package qrparking.service.administrador;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

	private static final String DADOS_INCORRETOS = "CPF ou senha inválidos";
	private static final String CPF_CADASTRADO = "CPF já cadastrado";
	@Autowired
	private AdministradorDao administradorDao;

	public void salvar(@RequestBody Administrador administrador) {

		if (administradorDao.getCpfExistente(administrador.getCpf(), administrador.getId())) {
			throw new IllegalArgumentException(CPF_CADASTRADO);
		}

		if (administrador.getId() == 0) {
			administrador.setDtCadastro(new Date());
			String senhaCripto = criptografarSenha(administrador);
			administrador.setSenha(senhaCripto);
		} else {
			if(administrador.getSenha() != null){
				String senhaCripto = criptografarSenha(administrador);
				administrador.setSenha(senhaCripto);
			}else{
				Administrador administradorCadastrado = administradorDao.getById(administrador.getId());
				administrador.setSenha(administradorCadastrado.getSenha());
			}
		}

		administradorDao.update(administrador);

	}

	private String criptografarSenha(Administrador administrador) {
		String senhaOriginal = administrador.getSenha();

		MessageDigest algorithm;
		byte[] messageDigest = null;
		try {
			algorithm = MessageDigest.getInstance("MD5");
			try {
				messageDigest = algorithm.digest(senhaOriginal.getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		StringBuilder hexString = new StringBuilder();
		for (byte b : messageDigest) {
			hexString.append(String.format("%02X", 0xFF & b));
		}

		String senhaCripto = hexString.toString();
		return senhaCripto;
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

	public Administrador buscarPorCredenciais(Administrador administrador) {
		String senhaCriptografada = criptografarSenha(administrador);
		administrador.setSenha(senhaCriptografada);
		Administrador administradorAutenticado = administradorDao.getAdministradorPorCredenciais(administrador);
		if (administradorAutenticado == null) {
			throw new IllegalArgumentException(DADOS_INCORRETOS);
		}
		return administradorAutenticado;
	}

}
