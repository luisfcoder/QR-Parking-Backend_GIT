package qrparking.models.pagamento.vo;

import java.util.Date;

public class FiltrosVO {
	
	public Date dataInicial;
	public Date dataFinal;
	
	public Date getDataInicial() {
		return dataInicial;
	}
	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}
	public Date getDataFinal() {
		return dataFinal;
	}
	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}
}
