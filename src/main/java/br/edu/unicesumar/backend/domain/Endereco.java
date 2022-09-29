package br.edu.unicesumar.backend.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "endereco")
public class Endereco {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "endereco_id")
	@JsonProperty(access = Access.READ_ONLY)
    private Long enderecoId;
	
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
	
	public void popularDadosTeste() {
		this.cep = 87013230L;
		this.estado = "PR";
		this.cidade = "Maringá";
		this.bairro = "Zona 1";
		this.logradouro = "Av xv de Novembro";
		this.numero = "1046";
		this.complemento = "Apto 123";
	}
	
	
}
