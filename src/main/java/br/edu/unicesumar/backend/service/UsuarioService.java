package br.edu.unicesumar.backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

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
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

import br.edu.unicesumar.backend.config.auth.Roles;
import br.edu.unicesumar.backend.config.auth.jwt.Jwt;
import br.edu.unicesumar.backend.config.auth.jwt.JwtTool;
import br.edu.unicesumar.backend.domain.Contato;
import br.edu.unicesumar.backend.domain.Endereco;
import br.edu.unicesumar.backend.domain.Usuario;
import br.edu.unicesumar.backend.domain.enums.TipoUsuario;
import br.edu.unicesumar.backend.dto.sign.SignIn;
import br.edu.unicesumar.backend.dto.sign.SignUpAgencia;
import br.edu.unicesumar.backend.dto.sign.SignUpViajante;
import br.edu.unicesumar.backend.dto.sign.UpdateAgencia;
import br.edu.unicesumar.backend.dto.sign.UpdateFotoUsuario;
import br.edu.unicesumar.backend.dto.sign.UpdatePassword;
import br.edu.unicesumar.backend.dto.sign.UpdateViajante;
import br.edu.unicesumar.backend.repository.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService {

    @Lazy
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTool jwtTokenTool;

    @Autowired
    private UsuarioRepository usuarioRepository;

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
            //throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username já está sendo usado!");
            throw new HttpStatusCodeException(HttpStatus.BAD_REQUEST, "Username já está sendo usado!") { 
                
            };
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

    public void updatePassword(UpdatePassword updatePassword, Usuario usuario) {

        if (!updatePassword.getPassword().equals(updatePassword.getConfirmPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Senha confirmada está diferente da Senha");
        }

        Optional<Usuario> usuarioDoBanco = usuarioRepository.findById(usuario.getUsuarioId());

        usuarioDoBanco.get().setPassword(passwordEncoder.encode(updatePassword.getPassword()));
        usuarioRepository.save(usuarioDoBanco.get());

    }

    @PostConstruct
    public void registerAdminUser() {

        if (!usuarioRepository.existsByUsername(this.adminUsername)) {

            Endereco endereco = new Endereco();
            endereco.popularDadosAdmin();
            ;

            Contato contatoAux = new Contato();
            contatoAux.popularDadosAdmin();
            ;

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

    public Usuario atualizarFotoUsuario(UpdateFotoUsuario updateFotoUsuario, Usuario usuario) {

        Optional<Usuario> usuarioDoBanco = usuarioRepository.findById(usuario.getUsuarioId());

        usuarioDoBanco.get().setFotoUsuario(updateFotoUsuario.getFotoUsuario());

        return usuarioRepository.save(usuarioDoBanco.get());
    }

    public Usuario atualizarUsuarioAgencia(UpdateAgencia updateAgencia, Usuario usuario) {

        if (usuario.getAgencia() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário Logado não é Agência");
        }

        Optional<Usuario> usuarioDoBanco = usuarioRepository.findById(usuario.getUsuarioId());

        usuarioDoBanco.get().getAgencia().setNomeFantasia(updateAgencia.getNomeFantasia());
        usuarioDoBanco.get().getAgencia().setRazaoSocial(updateAgencia.getRazaoSocial());
        usuarioDoBanco.get().getAgencia().setCnpj(updateAgencia.getCnpj());

        if (usuarioDoBanco.get().getAgencia().getInscricaoEstatual() != null) {
            usuarioDoBanco.get().getAgencia().setInscricaoEstatual(updateAgencia.getInscricaoEstatual());
        }

        return usuarioRepository.save(usuarioDoBanco.get());
    }

    public Usuario atualizarUsuarioViajante(UpdateViajante updateViajante, Usuario usuario) {

        if (usuario.getViajante() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário Logado não é Viajante");
        }

        Optional<Usuario> usuarioDoBanco = usuarioRepository.findById(usuario.getUsuarioId());

        usuarioDoBanco.get().getViajante().setNome(updateViajante.getNome());
        usuarioDoBanco.get().getViajante().setSobrenome(updateViajante.getSobrenome());
        usuarioDoBanco.get().getViajante().setCpf(updateViajante.getCpf());
        usuarioDoBanco.get().getViajante().setRg(updateViajante.getRg());
        usuarioDoBanco.get().getViajante().setDataNascimento(updateViajante.getDataNascimento());
        usuarioDoBanco.get().getViajante().setSexo(updateViajante.getSexo());

        return usuarioRepository.save(usuarioDoBanco.get());

    }

}