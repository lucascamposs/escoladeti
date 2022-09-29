package br.edu.unicesumar.backend.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


import org.hibernate.validator.constraints.br.CNPJ;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

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
	@JsonProperty(access = Access.READ_ONLY)
    private Long agenciaId;
	
	@NotEmpty(message = "Nome Fantasia não pode ser vazio")
	private String nomeFantasia;
	
	@NotEmpty(message = "Razão Social não pode ser vazio")
	private String razaoSocial;
	
	@NotNull(message = "CNPJ não pode ser nulo")
	@CNPJ(message = "Cnpj deve ter 14 dígitos")
	private String cnpj;
	
	//@NotEmpty(message = "Inscrição Estadual não pode ser vazio")
	private String inscricaoEstatual;


}	
