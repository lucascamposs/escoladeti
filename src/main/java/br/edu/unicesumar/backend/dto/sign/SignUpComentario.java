package br.edu.unicesumar.backend.dto.sign;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.edu.unicesumar.backend.domain.ComentarioFoto;
import lombok.Data;

@Data
public class SignUpComentario {

    @NotNull(message = "Avaliação não pode ser nulo")
    private Integer avaliacao;

    @NotEmpty(message = "Descrição não pode ser vazio")
    private String descricao;

    private List<ComentarioFoto> comentarioFotos = new ArrayList<>();

    private Long lugarId;

    private Long atividadeId;

    private Long roteiroId;
}
