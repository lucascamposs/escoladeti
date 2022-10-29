package br.edu.unicesumar.backend.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "viajante")
public class Viajante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "viajante_id")
    private Long viajanteId;

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
