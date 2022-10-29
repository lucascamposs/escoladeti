package br.edu.unicesumar.backend.dto.sign;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.edu.unicesumar.backend.domain.LugarFoto;
import lombok.Data;

@Data
public class SignUpLugar {

    @NotEmpty(message = "Nome do Lugar não pode ser vazio")
    private String nomeLugar;

    @NotEmpty(message = "Descrição não pode ser vazia")
    private String descricao;

    @NotEmpty(message = "Coordenada não pode ser vazia")
    private String coordenada;

    @NotNull(message = "Categoria ID não pode ser nulo")
    private Long lugarCategoriaId;

    @NotNull(message = "Fotos do Lugar não pode ser nulo")
    private List<LugarFoto> lugarFotos = new ArrayList<>();
}
