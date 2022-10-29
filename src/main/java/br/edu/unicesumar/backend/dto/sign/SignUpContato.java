package br.edu.unicesumar.backend.dto.sign;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;

import br.edu.unicesumar.backend.domain.enums.TipoContato;
import lombok.Data;

@Data
public class SignUpContato {

    @Enumerated(EnumType.STRING)
    private TipoContato tipoContato;

    @NotEmpty(message = "Numero não pode ser vazio")
    private String numero;

}
