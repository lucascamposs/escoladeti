package br.edu.unicesumar.backend.dto.sign;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class DtoEndereco {
	
	@NotNull
	private Long cep;
	
	@NotEmpty
	private String estado;
	
	@NotEmpty
	private String cidade;
	
	@NotEmpty
	private String bairro;
	
	@NotEmpty
	private String logradouro;
	
	@NotEmpty
	private String numero; //Numero pode ter letras "Casa 36A"
	
	private String complemento;

}
