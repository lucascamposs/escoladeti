package br.edu.unicesumar.backend.dto.sign;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UpdateEndereco {

    @NotNull(message = "Endereço Id não pode ser nulo")
    private Long enderecoId;

    @NotEmpty(message = "CEP não pode ser vazio")
    private String cep;

    @NotEmpty(message = "Estado não pode ser vazio")
    private String estado;

    @NotEmpty(message = "Cidade não pode ser vazio")
    private String cidade;

    @NotEmpty(message = "Bairro não pode ser vazio")
    private String bairro;

    @NotEmpty(message = "Logradouro não pode ser vazio")
    private String logradouro;

    @NotEmpty(message = "Numero não pode ser vazio")
    private String numero;

    private String complemento;

}
