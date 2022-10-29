package br.edu.unicesumar.backend.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.br.CNPJ;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "agencia")
public class Agencia {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "agencia_id")
    private Long agenciaId;
	
	@NotEmpty(message = "Nome Fantasia não pode ser vazio")
	private String nomeFantasia;
	
	@NotEmpty(message = "Razão Social não pode ser vazio")
	private String razaoSocial;
	
	@NotEmpty(message = "CNPJ não pode ser vazio")
	@CNPJ(message = "Cnpj deve ter 14 dígitos e ser válido")
	private String cnpj;
	
	private String inscricaoEstatual;


}	
