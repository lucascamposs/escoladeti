package br.edu.unicesumar.backend.dto.sign;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class SignUpAtividadeDia {

	@NotEmpty(message = "Dia Disponível não pode ser vazio")
	private LocalDate diaDisponivel;
	
	@NotNull(message = "Atividade não pode ser nulo")
	private Long atividadeId;
	
	@NotNull(message = "Horários da Atividade não pode ser nulo")
	private List<SignUpAtividadeHorario> atividadeHorarios = new ArrayList<>();
}