package qrparking.models.relatorio;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import qrparking.models.ticket.Ticket;

@Entity
@Table(name = "relatorioFinanceiro")
public class RelatorioFinanceiro {

	@Id
	@Column(length = 5)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@ManyToOne
	private Ticket ticket;

	@Column(nullable = false)
	private BigDecimal valorPago;
	
	@Column(nullable = false)
	private Date dtPagamento;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public BigDecimal getValorPago() {
		return valorPago;
	}

	public void setValorPago(BigDecimal valorPago) {
		this.valorPago = valorPago;
	}

	public Date getDtPagamento() {
		return dtPagamento;
	}

	public void setDtPagamento(Date dtPagamento) {
		this.dtPagamento = dtPagamento;
	}
	
}
