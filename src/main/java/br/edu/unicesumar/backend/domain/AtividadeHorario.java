package br.edu.unicesumar.backend.domain;

import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "atividade_horario_dia")
public class AtividadeHorario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "atividade_horario_id")
    private Long atividadeHorarioId;

    @NotNull(message = "Ativo não pode ser nulo")
    private Boolean ativo;

    @JsonProperty(access = Access.READ_ONLY)
    private Integer quantPessoasAtual;

    @NotNull(message = "Quantidade Pessoas Máximo não pode ser nulo")
    private Integer quantPessoasMax;

    @NotNull(message = "Horário Início não pode ser nulo")
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime horario_inicio;

    @NotNull(message = "Horário Final não pode ser nulo")
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime horario_final;

    @NotNull(message = "Preço não pode ser nulo")
    private Double preco;
}
