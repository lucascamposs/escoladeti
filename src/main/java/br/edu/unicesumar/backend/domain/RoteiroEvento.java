package br.edu.unicesumar.backend.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "roteiro_evento")
public class RoteiroEvento {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "roteiro_evento_id")
	@JsonProperty(access = Access.READ_ONLY)
    private Long roteiroEventoId;
	
	@NotEmpty(message = "Nome Evento não pode ser vazio")
	private String nomeEvento;
	
	@NotEmpty(message = "Descricao Evento não pode ser vazio")
	private String descricaoEvento;
	
	@NotNull(message = "Ordem não pode ser nulo")
	private Integer ordem;
	
	@ManyToOne
	@JoinColumn(name = "lugar_id")
	@NotNull(message = "Lugar não pode ser nulo")
	private Lugar lugar;
	
	@ManyToOne
	@JoinColumn(name = "atividade_id")
	private Atividade atividade;
}
