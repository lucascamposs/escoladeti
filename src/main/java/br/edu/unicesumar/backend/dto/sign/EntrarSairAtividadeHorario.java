package br.edu.unicesumar.backend.dto.sign;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class EntrarSairAtividadeHorario {

    @NotNull(message = "Atividade Horario Id não pode ser nulo")
    private Long atividadeHorarioId;
}
