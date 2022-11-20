package br.edu.unicesumar.backend.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "atividade")
public class Atividade {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "atividade_id")
    private Long atividadeId;

	@NotEmpty(message = "Nome da Atividade não pode ser vazia")
	private String nomeAtividade;
	
	@NotEmpty(message = "Descrição da Atividade não pode ser vazia")
	private String descricaoAtividade;
	
	@NotNull(message = "Ativo não pode ser nulo")
    private Boolean ativo;
	
	@ManyToOne
	@JoinColumn(name = "atividade_categoria_id")
	private AtividadeCategoria atividadeCategoria;

	@ManyToOne
	@JoinColumn(name = "agencia_id")
	@JsonProperty(access = Access.READ_ONLY)
	private Agencia agencia;
	
	@ManyToOne
	@JoinColumn(name = "lugar_id")
	private Lugar lugar;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name= "atividade_id")
	@NotNull(message = "Fotos da Atividade não pode ser nulo")
	@Builder.Default
	private List<AtividadeFoto> atividadeFotos = new ArrayList<>();
	
	@OneToMany(mappedBy = "atividade", orphanRemoval = true)
    private List<Comentario> comentariosAtividade;
}
