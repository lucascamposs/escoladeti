package br.edu.unicesumar.backend.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.unicesumar.backend.config.auth.jwt.Jwt;
import br.edu.unicesumar.backend.domain.Usuario;
import br.edu.unicesumar.backend.dto.sign.SignIn;
import br.edu.unicesumar.backend.dto.sign.SignUpAgencia;
import br.edu.unicesumar.backend.dto.sign.SignUpViajante;
import br.edu.unicesumar.backend.service.UsuarioService;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/signin")
    public ResponseEntity<Jwt> signIn(@Valid @RequestBody SignIn signIn) {
        return ResponseEntity.ok(usuarioService.signIn(signIn));
    }

    @PostMapping("/signup_viajante")
    public ResponseEntity<Usuario> signIn(@Valid @RequestBody SignUpViajante signUp) {
        return ResponseEntity.ok(usuarioService.signUpViajante(signUp));
    }
    
    @PostMapping("/signup_agencia")
    public ResponseEntity<Usuario> signIn(@Valid @RequestBody SignUpAgencia signUp) {
        return ResponseEntity.ok(usuarioService.signUpAgencia(signUp));
    }


}
