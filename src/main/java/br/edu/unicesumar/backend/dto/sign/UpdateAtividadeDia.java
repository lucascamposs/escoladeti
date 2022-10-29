package br.edu.unicesumar.backend.dto.sign;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class UpdateAtividadeDia {

    @NotNull(message = "Atividade Dia Id não pode ser nulo")
    private Long atividadeDiaId;

    @NotNull(message = "Dia Disponível não pode ser nulo")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate diaDisponivel;

    @NotNull(message = "Ativo não pode ser nulo")
    private Boolean ativo;

    @NotNull(message = "Atividade não pode ser nulo")
    private Long atividadeId;

    @NotNull(message = "Horários da Atividade não pode ser nulo")
    private List<UpdateAtividadeHorario> atividadeHorarios = new ArrayList<>();
}
