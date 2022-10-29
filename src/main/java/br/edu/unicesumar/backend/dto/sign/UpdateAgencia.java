package br.edu.unicesumar.backend.dto.sign;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.br.CNPJ;

import lombok.Data;

@Data
public class UpdateAgencia {

    @NotEmpty(message = "Nome Fantasia não pode ser vazio")
    private String nomeFantasia;

    @NotEmpty(message = "Razão Social não pode ser vazio")
    private String razaoSocial;

    @NotEmpty(message = "CNPJ não pode ser vazio")
    @CNPJ(message = "Cnpj deve ter 14 dígitos e ser válido")
    private String cnpj;

    private String inscricaoEstatual;
}
