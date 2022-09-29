package br.edu.unicesumar.backend.dto.sign;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.edu.unicesumar.backend.domain.Endereco;
import br.edu.unicesumar.backend.domain.Agencia;
import br.edu.unicesumar.backend.domain.Contato;
import lombok.Data;

@Data
public class SignUpAgencia {
	
	@NotEmpty(message = "Username não pode ser vazio")
	private String username;
	
	@Length(min = 6, max = 20, message = "Password deve ter entre 6 e 20 caracteres")
	private String password;
	
    @Email(message = "Email deve ser válido")
    @NotEmpty(message = "Email não pode ser vazio")
	private String email;
	
    @NotNull(message = "Foto do Usuário não pode ser nulo")
    private String fotoUsuario;
    
    @NotNull(message = "Endereço não pode ser nulo")
    private Endereco endereco;
    
    @NotNull(message = "Agência não pode ser nulo")
    private Agencia agencia;
    
    @NotNull(message = "Contatos não pode ser nulo")
    private List<Contato> contatos = new ArrayList<>();
}
