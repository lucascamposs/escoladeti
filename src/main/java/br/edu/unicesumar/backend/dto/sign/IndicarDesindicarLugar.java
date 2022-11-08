package br.edu.unicesumar.backend.dto.sign;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class IndicarDesindicarLugar {
    
    @NotNull(message = "Lugar Id não pode ser nulo")
    private Long lugarId;
}
