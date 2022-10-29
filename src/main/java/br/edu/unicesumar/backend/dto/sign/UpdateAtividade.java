package br.edu.unicesumar.backend.dto.sign;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.edu.unicesumar.backend.domain.AtividadeFoto;
import lombok.Data;

@Data
public class UpdateAtividade {
    
    @NotNull(message = "Atividade Id não pode ser nulo")
    private Long atividadeId;
    
    @NotEmpty(message = "Nome da Atividade não pode ser vazia")
    private String nomeAtividade;
    
    @NotEmpty(message = "Descrição da Atividade não pode ser vazia")
    private String descricaoAtividade;
    
    @NotNull(message = "Ativo não pode ser nulo")
    private Boolean ativo;
    
    @NotNull(message = "Categoria ID da Atividade não pode ser nulo")
    private Long atividadeCategoriaId;
    
    private Long lugarId;
    
    @NotNull(message = "Fotos da Atividade não pode ser nulo")
    private List<AtividadeFoto> atividadeFotos = new ArrayList<>();
}
