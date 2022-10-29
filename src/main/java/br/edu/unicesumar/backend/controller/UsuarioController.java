package br.edu.unicesumar.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.unicesumar.backend.domain.Usuario;
import br.edu.unicesumar.backend.dto.sign.UpdateAgencia;
import br.edu.unicesumar.backend.dto.sign.UpdateFotoUsuario;
import br.edu.unicesumar.backend.dto.sign.UpdatePassword;
import br.edu.unicesumar.backend.dto.sign.UpdateViajante;
import br.edu.unicesumar.backend.service.UsuarioService;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/me")
    public ResponseEntity<Usuario> getMe() {
        Usuario userLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(userLogado);
    }

    @PutMapping("/alterar_senha")
    public void updatePassword(@RequestBody UpdatePassword updatePassword) {
        Usuario userLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        usuarioService.updatePassword(updatePassword, userLogado);
    }

    @PutMapping("/alterar_foto_usuario")
    public ResponseEntity<Usuario> updateFotoUsuario(@RequestBody UpdateFotoUsuario updateFotoUsuario) {
        Usuario userLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(usuarioService.atualizarFotoUsuario(updateFotoUsuario, userLogado));
    }

    @PutMapping("/alterar_dados_agencia")
    @PreAuthorize("hasRole('COMPANY')")
    public ResponseEntity<Usuario> updateUsuarioAgencia(@RequestBody UpdateAgencia updateAgencia) {
        Usuario userLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(usuarioService.atualizarUsuarioAgencia(updateAgencia, userLogado));
    }

    @PutMapping("/alterar_dados_viajante")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Usuario> updateUsuairoViajante(@RequestBody UpdateViajante updateViajante) {
        Usuario userLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(usuarioService.atualizarUsuarioViajante(updateViajante, userLogado));
    }

}
