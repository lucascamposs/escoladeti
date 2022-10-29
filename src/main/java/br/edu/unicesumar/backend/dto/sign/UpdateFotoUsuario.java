package br.edu.unicesumar.backend.dto.sign;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UpdateFotoUsuario {

    @NotNull(message = "Foto do Usuário não pode ser nulo")
    private String fotoUsuario;
}
