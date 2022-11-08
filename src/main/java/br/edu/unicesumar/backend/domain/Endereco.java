package br.edu.unicesumar.backend.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

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
    private Long enderecoId;
	
	@NotEmpty(message = "CEP não pode ser vazio")
	private String cep;
	
	@NotEmpty(message = "Estado não pode ser vazio")
	private String estado;
	
	@NotEmpty(message = "Cidade não pode ser vazio")
	private String cidade;
	
	@NotEmpty(message = "Bairro não pode ser vazio")
	private String bairro;
	
	@NotEmpty(message = "Logradouro não pode ser vazio")
	private String logradouro;
	
	@NotEmpty(message = "Numero não pode ser vazio")
	private String numero;
	
	private String complemento;
	
	public void popularDadosAdmin() {
		this.cep = "87000123";
		this.estado = "PR";
		this.cidade = "Maringá";
		this.bairro = "Zona 1";
		this.logradouro = "Av Brasil";
		this.numero = "123";
		this.complemento = "Complemento 123";
	}
	
	
}
