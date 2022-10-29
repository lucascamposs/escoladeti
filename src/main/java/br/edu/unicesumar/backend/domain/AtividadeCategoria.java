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
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "atividade_categoria")
public class AtividadeCategoria {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "atividade_categoria_id")
    private Long atividadeCategoriaId;
	
	@NotEmpty(message = "Nome Categoria não pode ser vazio")
	private String nome_categoria;
	
	@NotEmpty(message = "Icone Categoria não pode ser vazio")
	@Column(columnDefinition="TEXT")
	private String icone_categoria;
	
}
