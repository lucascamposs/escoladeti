package br.edu.unicesumar.backend.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "atividade_categoria")
public class AtividadeCategoria {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "atividade_categoria_id")
	@JsonProperty(access = Access.READ_ONLY)
    private Long atividadeCategoriaId;
	
	@NotEmpty(message = "Nome Categoria não pode ser vazio")
	private String nome_categoria;
	
	@NotEmpty(message = "Icone Categoria não pode ser vazio")
	private String icone_categoria;
	
}
