package br.edu.unicesumar.backend.dto.sign;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class SignUpLugar {
	
	@NotEmpty(message = "Nome do Lugar não pode ser vazio")
	private String nomeLugar;
	
	@NotEmpty(message = "Descrição não pode ser vazia")
	private String descricao;
	
	@NotNull(message = "Lugar Indicado não pode ser nulo")
    private Boolean lugarIndicado;
	
	@NotEmpty(message = "Coordenada não pode ser vazia")
	private String coordenada;
	
	@NotNull(message = "Categoria ID não pode ser nulo")
	private Long lugarCategoriaId;
}
