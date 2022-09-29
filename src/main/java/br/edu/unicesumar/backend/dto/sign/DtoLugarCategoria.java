package br.edu.unicesumar.backend.dto.sign;

import javax.validation.constraints.NotEmpty;

import lombok.Data;


@Data
public class DtoLugarCategoria {
	
	
	@NotEmpty(message = "Nome categoria não pode ser vazio")
	private String nome_categoria;
	
	@NotEmpty(message = "Icone categoria não pode ser vazio")
	private String icone_categoria;

}
