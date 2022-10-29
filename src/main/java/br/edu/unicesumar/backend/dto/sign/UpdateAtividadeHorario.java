package br.edu.unicesumar.backend.dto.sign;

import java.time.LocalTime;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class UpdateAtividadeHorario {

    @NotNull(message = "Atividade Horario Id não pode ser nulo")
    private Long atividadeHorarioId;

    @NotNull(message = "Ativo não pode ser nulo")
    private Boolean ativo;

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
