package qrparking.models.parametro;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import qrparking.models.administrador.Administrador;

@Entity
@Table(name = "parametro")
public class Parametro {

	// ------------------------
	// PRIVATE FIELDS
	// ------------------------

	@Id
	@Column(length = 5)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(nullable = false)
	private BigDecimal valorMinuto;
	
	@Column(length = 2, nullable = false)
	private Integer tolerancia;
	
	@Column(length = 150, nullable = false)
	private String justificativa;
	
	@ManyToOne
	private Administrador administrador;
	
	@Column(nullable = false)
	private Date dtAlteracao;
	
	/*
	 * MÉTODOS PÚBLICOS
	 */
	
	public Parametro() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getValorMinuto() {
		return valorMinuto;
	}

	public void setValorMinuto(BigDecimal valorMinuto) {
		this.valorMinuto = valorMinuto;
	}

	public Integer getTolerancia() {
		return tolerancia;
	}

	public void setTolerancia(Integer tolerancia) {
		this.tolerancia = tolerancia;
	}

	public String getJustificativa() {
		return justificativa;
	}

	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}

	public Administrador getAdministrador() {
		return administrador;
	}

	public void setAdministrador(Administrador administrador) {
		this.administrador = administrador;
	}

	public Date getDtAlteracao() {
		return dtAlteracao;
	}

	public void setDtAlteracao(Date dtAlteracao) {
		this.dtAlteracao = dtAlteracao;
	}
	
}
