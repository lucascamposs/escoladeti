package br.edu.unicesumar.backend.dto.sign;

import java.time.LocalTime;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;

@Data
public class SignUpAtividadeHorario {
	
	@JsonProperty(access = Access.READ_ONLY)
	private Integer quantPessoasAtual;
	
	@NotNull(message = "Quantidade Pessoas Máximo não pode ser nulo")
	private Integer quantPessoasMax;
	
	@NotNull(message = "Horário Início não pode ser nulo")
	private LocalTime horario_inicio;
	
	@NotNull(message = "Horário Final não pode ser nulo")
	private LocalTime horario_final;
	
	@NotNull(message = "Preço não pode ser nulo")
	private Double preco;
}
