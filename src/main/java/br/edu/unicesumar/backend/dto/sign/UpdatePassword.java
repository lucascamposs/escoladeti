package br.edu.unicesumar.backend.dto.sign;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class UpdatePassword {
	
	@Length(min = 6, max = 20, message = "Password deve ter entre 6 e 20 caracteres")
    private String password;
	
	@Length(min = 6, max = 20, message = "Password deve ter entre 6 e 20 caracteres")
    private String confirmPassword;
	

}
