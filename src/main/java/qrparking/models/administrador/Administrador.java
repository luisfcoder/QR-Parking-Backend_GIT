package qrparking.models.administrador;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Represents an User for this web application.
 */
@Entity
@Table(name = "administrador")
public class Administrador {

	// ------------------------
	// PRIVATE FIELDS
	// ------------------------

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(length = 100, nullable = false)
	private String nome;
	
	@Column(length = 65, nullable = false)
	private String senha;
	
	@Column(length = 11, nullable = false)
	private String cpf;

	@Column(length = 100, nullable = false)
	private String email;
	
	@Column(nullable = false)
	private Date dtCadastro;
	
	private Date dtInativacao;
	
	
	/*
	 * MÉTODOS PÚBLICOS
	 */
	
	public Administrador() {
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getSenha() {
		return senha;
	}


	public void setSenha(String senha) {
		this.senha = senha;
	}


	public String getCpf() {
		return cpf;
	}


	public void setCpf(String cpf) {
		this.cpf = cpf;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public Date getDtCadastro() {
		return dtCadastro;
	}


	public void setDtCadastro(Date dtCadastro) {
		this.dtCadastro = dtCadastro;
	}


	public Date getDtInativacao() {
		return dtInativacao;
	}


	public void setDtInativacao(Date dtInativacao) {
		this.dtInativacao = dtInativacao;
	}
	
}
