package br.edu.unicesumar.backend.dto.sign;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class SignIn {

    @NotEmpty(message = "Username não pode ser vazio")
    private String username;

    @Length(min = 6, max = 20, message = "Password deve ter entre 6 e 20 caracteres")
    private String password;

}
