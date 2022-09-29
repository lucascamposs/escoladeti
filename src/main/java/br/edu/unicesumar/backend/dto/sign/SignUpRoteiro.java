package br.edu.unicesumar.backend.dto.sign;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.edu.unicesumar.backend.domain.RoteiroEvento;
import lombok.Data;

@Data
public class SignUpRoteiro {
	
	@NotEmpty(message = "Nome Roteiro não pode ser vazio")
	private String nomeRoteiro;
	
	@NotEmpty(message = "Descrição Roteiro não pode ser vazio")
	private String descricaoRoteiro;
	
	@NotNull(message = "Data Inicio não pode ser nulo")
	private LocalDate dataInicio;
	
	@NotNull(message = "Data Final não pode ser nulo")
	private LocalDate dataFinal;
	
	@NotNull(message = "Preço não pode ser nulo")
	private Double preco;
	
	@NotNull(message = "Quantidade Pessoas Máximo não pode ser nulo")
	private Integer quantidadePessoasMax;
	
	@NotNull(message = "Eventos de Roteiro não pode ser nulo")
	private List<SignUpRoteiroEvento> eventosRoteiro = new ArrayList<>();
}
