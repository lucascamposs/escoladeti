package br.edu.unicesumar.backend.dto.sign;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class SignUpComentarioResposta {

    @NotEmpty(message = "Descrição não pode ser vazio")
    private String descricao;

    @NotNull(message = "Comentario Id não pode ser nulo")
    private Long comentarioId;
}
