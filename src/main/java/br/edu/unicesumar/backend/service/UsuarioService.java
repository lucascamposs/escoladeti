package br.edu.unicesumar.backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.apache.catalina.startup.ClassLoaderFactory.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.edu.unicesumar.backend.config.auth.Roles;
import br.edu.unicesumar.backend.config.auth.jwt.Jwt;
import br.edu.unicesumar.backend.config.auth.jwt.JwtTool;
import br.edu.unicesumar.backend.domain.Endereco;
import br.edu.unicesumar.backend.domain.Agencia;
import br.edu.unicesumar.backend.domain.Contato;
import br.edu.unicesumar.backend.domain.Usuario;
import br.edu.unicesumar.backend.domain.Viajante;
import br.edu.unicesumar.backend.domain.enums.TipoUsuario;
import br.edu.unicesumar.backend.dto.sign.SignIn;
import br.edu.unicesumar.backend.dto.sign.SignUpAgencia;
import br.edu.unicesumar.backend.dto.sign.SignUpViajante;
import br.edu.unicesumar.backend.dto.sign.UpdatePassword;
import br.edu.unicesumar.backend.repository.AgenciaRepository;
import br.edu.unicesumar.backend.repository.UsuarioRepository;
import br.edu.unicesumar.backend.repository.ViajanteRepository;

@Service
public class UsuarioService implements UserDetailsService {
	
   
  	@Lazy
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTool jwtTokenTool;
//---------------------------------------------------------------------------------
// REPOSITORYS
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private AgenciaRepository agenciaRepository;
    
    @Autowired
    private ViajanteRepository viajanteRepository;
    
  //---------------------------------------------------------------------------------    
    
    @Value("${escoladeti.auth.admin.username}")
    private String adminUsername;

    @Value("${escoladeti.auth.admin.password}")
    private String adminPassword;

    @Override
    public Usuario loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findUsuariosByUsername(username);
    }

    public Jwt signIn(SignIn signIn) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(signIn.getUsername(), signIn.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Usuario userDetails = (Usuario) authentication.getPrincipal();

        return jwtTokenTool.generateToken(userDetails);

    }
    
    

    public Usuario signUpViajante(SignUpViajante signUp) {
    	
        if (usuarioRepository.existsByUsername(signUp.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username já está sendo usado!");
        }

        if (usuarioRepository.existsByEmail(signUp.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email já está sendo usado!");
        }
        
        if (signUp.getContato().size() > 1) {
        	throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Viajante só pode ter um contato");
		}
        	
    	Usuario usuario = Usuario.builder()
    			.username(signUp.getUsername())
    			.password(passwordEncoder.encode(signUp.getPassword()))
    			.email(signUp.getEmail())
    			.tipoUsuario(TipoUsuario.VIAJANTE)
    			.fotoUsuario(signUp.getFotoUsuario())
    			.ativo(true)
    			.viajante(signUp.getViajante())
    			.contato(signUp.getContato())
    			.endereco(signUp.getEndereco()).build();
    	
    	usuario.getRoles().add(Roles.ROLE_USER);
    	
    	
    	return usuarioRepository.save(usuario);        
    }
    
    public Usuario signUpAgencia(SignUpAgencia signUp) {
        if (usuarioRepository.existsByUsername(signUp.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username já está sendo usado!");
        }

        if (usuarioRepository.existsByEmail(signUp.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email já está sendo usado!");
        }
        
    	Usuario usuario = Usuario.builder()
    			.username(signUp.getUsername())
    			.password(passwordEncoder.encode(signUp.getPassword()))
    			.email(signUp.getEmail())
    			.tipoUsuario(TipoUsuario.AGENCIA)
    			.fotoUsuario(signUp.getFotoUsuario())
    			.ativo(true)
    			.agencia(signUp.getAgencia())
    			.contato(signUp.getContatos())
    			.endereco(signUp.getEndereco()).build();
    	
    	usuario.getRoles().add(Roles.ROLE_COMPANY);
    	return usuarioRepository.save(usuario);
        
    }
    
    public void updatePassword(UpdatePassword updatePassword, Usuario userLogado) {
    	
    	if (updatePassword.getPassword() == updatePassword.getConfirmPassword()) {
    		userLogado.setPassword(passwordEncoder.encode(updatePassword.getPassword()));
    		usuarioRepository.save(userLogado);
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Senha confirmada está diferente da Senha");
		}    	
    	
    }

    @PostConstruct
    public void registerAdminUser() {

        if (!usuarioRepository.existsByUsername(this.adminUsername)) {
           
        	Endereco endereco = new Endereco();
            endereco.popularDadosTeste();
            
            Contato contatoAux = new Contato();
            contatoAux.popularDadosTeste();
            
            List<Contato> contato = new ArrayList<>();
            contato.add(contatoAux);
            
			Usuario admin = Usuario.builder()
                    .username(this.adminUsername)
                    .email("admin@admin.com")
                    .ativo(true)
                    .tipoUsuario(TipoUsuario.ADMIN)
                    .endereco(endereco)
                    .contato(contato)
                    .fotoUsuario("Foto")
                    .password(passwordEncoder.encode(this.adminPassword)).build();

            admin.getRoles().add(Roles.ROLE_ADMIN);

            usuarioRepository.save(admin);
            
        }

    }
    public Optional<Agencia> atualizarUsuarioAgencia(Agencia agenciaExistente){
    	if(agenciaRepository.existsById(agenciaExistente.getAgenciaId())) {
    		Agencia agenciaAtualizada = agenciaRepository.save(agenciaExistente);
    		return Optional.of(agenciaAtualizada);
    	}
    	else {
    		return Optional.empty();
    	}
    }
    
    public Optional<Viajante> atualizarUsuarioViajante(Viajante viajanteExistente){
    	if(viajanteRepository.existsById(viajanteExistente.getViajanteId())) {
    		Viajante viajanteAtualizado = viajanteRepository.save(viajanteExistente);
    		return Optional.of(viajanteAtualizado);
    	}
    	else {
    		return Optional.empty();
    	}
    	
    }
    

   
	

	

}