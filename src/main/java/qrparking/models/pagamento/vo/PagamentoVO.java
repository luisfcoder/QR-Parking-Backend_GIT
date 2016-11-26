package qrparking.models.pagamento.vo;

import java.math.BigDecimal;

import qrparking.models.ticket.Ticket;

public class PagamentoVO {
	
	public String cartao;
	public BigDecimal valor;
	public Ticket ticket;
	
	public String getCartao() {
		return cartao;
	}
	public void setCartao(String cartao) {
		this.cartao = cartao;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public Ticket getTicket() {
		return ticket;
	}
	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}
}
