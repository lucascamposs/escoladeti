package br.edu.unicesumar.backend.dto.sign;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class SignUpAtividadeDia {

    @NotNull(message = "Dia Disponível não pode ser nulo")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate diaDisponivel;

    @NotNull(message = "Atividade não pode ser nulo")
    private Long atividadeId;

    @NotNull(message = "Horários da Atividade não pode ser nulo")
    private List<SignUpAtividadeHorario> atividadeHorarios = new ArrayList<>();
}