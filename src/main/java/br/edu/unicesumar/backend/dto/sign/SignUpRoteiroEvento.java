package br.edu.unicesumar.backend.dto.sign;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class SignUpRoteiroEvento {
	
	@NotEmpty(message = "Nome Evento não pode ser vazio")
	private String nomeEvento;
	
	@NotEmpty(message = "Descricao Evento não pode ser vazio")
	private String descricaoEvento;
	
	@NotNull(message = "Ordem não pode ser nulo")
	private Integer ordem;
	
	@NotNull(message = "Lugar ID não pode ser nulo")
	private Long lugarId;
	
	private Long atividadeId;
}
