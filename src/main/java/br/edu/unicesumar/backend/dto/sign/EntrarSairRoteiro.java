package br.edu.unicesumar.backend.dto.sign;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class EntrarSairRoteiro {

    @NotNull(message = "Roteiro Id não pode ser nulo")
    private Long roteiroId;
}
