package br.edu.unicesumar.backend.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "atividade_dia")
public class AtividadeDia {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "atividade_dia_id")
	@JsonProperty(access = Access.READ_ONLY)
    private Long atividadeDiaId;
	
	@NotEmpty(message = "Dia Disponível não pode ser vazio")
	private LocalDate diaDisponivel;
	
	@NotNull(message = "Ativo não pode ser nulo")
    private Boolean ativo;
	
	@ManyToOne
	@JoinColumn(name = "atividade_id")
	@NotNull(message = "Atividade não pode ser nulo")
	private Atividade atividade; 
	
	@OneToMany
	@JoinColumn(name = "atividade_dia_id")
	@NotNull(message = "Horários da Atividade não pode ser nulo")
	@Builder.Default
	private List<AtividadeHorario> atividadeHorarios = new ArrayList<>();
}
