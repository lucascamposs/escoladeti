package br.edu.unicesumar.backend.dto.sign;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class UpdateViajante {

    @NotEmpty(message = "Nome não pode ser vazio")
    private String nome;

    @NotEmpty(message = "Sobrenome não pode ser vazio")
    private String sobrenome;

    @NotNull(message = "CPF não pode ser nulo")
    @CPF(message = "CPF deve ser válido")
    private String cpf;

    @NotNull(message = "RG não pode ser nulo")
    private String rg;

    @NotNull(message = "Data de Nascimento não pode ser nulo")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

    @NotNull(message = "Sexo não pode ser nulo")
    private Character sexo;
}
