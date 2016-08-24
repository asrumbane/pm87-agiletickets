package br.com.caelum.agiletickets.domain.precos;

import java.math.BigDecimal;

import br.com.caelum.agiletickets.models.Sessao;
import br.com.caelum.agiletickets.models.TipoDeEspetaculo;

public class CalculadoraDePrecos {

	public static BigDecimal calcula(Sessao sessao, Integer quantidade) {
		BigDecimal preco;
		int lugares = (sessao.getTotalIngressos() - sessao.getIngressosReservados());
		double totalIngressos = 	sessao.getTotalIngressos().doubleValue();
		BigDecimal dezPorCento = sessao.getPreco().add(sessao.getPreco().multiply(BigDecimal.valueOf(0.10)));
		BigDecimal vintePorCento = sessao.getPreco().add(sessao.getPreco().multiply(BigDecimal.valueOf(0.20)));
		boolean cinquentaPorCento = (sessao.getTotalIngressos() - sessao.getIngressosReservados()) / sessao.getTotalIngressos().doubleValue() <= 0.50;
		
		if(sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.CINEMA) || sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.SHOW)) {
			//quando estiver acabando os ingressos... 
			if(lugares/totalIngressos  <= 0.05) { 
				preco = dezPorCento;
			} else {
							preco = sessao.getPreco();
			}
		} 
		
		else if(sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.BALLET)) {
			if(cinquentaPorCento) { 
				preco = vintePorCento;
			} else {
				preco = sessao.getPreco();
			}
			if(sessao.getDuracaoEmMinutos() > 60){
				preco = preco.add(sessao.getPreco().multiply(BigDecimal.valueOf(0.10)));
			}
		} 
		
		else if(sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.ORQUESTRA)) {
			if((sessao.getTotalIngressos() - sessao.getIngressosReservados()) / sessao.getTotalIngressos().doubleValue() <= 0.50) { 
				preco = vintePorCento;
			} else {
				preco = sessao.getPreco();
			}
			if(sessao.getDuracaoEmMinutos() > 60){
				preco = dezPorCento;
			}
		}  else {
			//nao aplica aumento para teatro (quem vai é pobretão)
			preco = sessao.getPreco();
		} 

		return preco.multiply(BigDecimal.valueOf(quantidade));
	}
}

