package br.edu.unicesumar.backend.dto.sign;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.edu.unicesumar.backend.domain.enums.TipoContato;
import lombok.Data;

@Data
public class UpdateContato {

    @NotNull(message = "Contato Id não pode ser nulo")
    private Long contatoId;

    @Enumerated(EnumType.STRING)
    private TipoContato tipoContato;

    @NotEmpty(message = "Numero não pode ser vazio")
    private String numero;
}
