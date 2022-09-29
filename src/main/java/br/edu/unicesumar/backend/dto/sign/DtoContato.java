package br.edu.unicesumar.backend.dto.sign;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import br.edu.unicesumar.backend.domain.enums.TipoContato;
import lombok.Data;
@Data
public class DtoContato {
	
	@Enumerated(EnumType.STRING)
    private TipoContato tipoContato;
	
	@NotNull(message = "Numero não pode ser nulo")
	private Long numero;

}
