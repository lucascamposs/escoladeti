package br.edu.unicesumar.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.unicesumar.backend.domain.Comentario;
import br.edu.unicesumar.backend.domain.Usuario;
import br.edu.unicesumar.backend.dto.sign.SignUpComentario;
import br.edu.unicesumar.backend.service.ComentarioService;

@RestController
@RequestMapping("/api/comentario")
@CrossOrigin
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    @GetMapping("/atividade_comentarios/{atividadeId}")
    public ResponseEntity<List<Comentario>> getComentariosDaAtividade(@PathVariable(name = "atividadeId") Long atividadeId) {
        return ResponseEntity.ok(comentarioService.getComentariosAtividade(atividadeId));
    }

    @GetMapping("/lugar_comentarios/{lugarId}")
    public ResponseEntity<List<Comentario>> getComentariosDoLugar(@PathVariable(name = "lugarId") Long lugarId) {
        return ResponseEntity.ok(comentarioService.getComentariosLugar(lugarId));
    }

    @GetMapping("/roteiro_comentarios/{roteiroId}")
    public ResponseEntity<List<Comentario>> getComentariosDoRoteiro(@PathVariable(name = "roteiroId") Long roteiroId) {
        return ResponseEntity.ok(comentarioService.getComentariosRoteiro(roteiroId));
    }

    @PostMapping("/VIAJANTE/atividade_adicionar_comentario")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Comentario> addComentarioAtividade(@RequestBody SignUpComentario signUpComentario) {

        Usuario userLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return ResponseEntity.ok(comentarioService.addComentarioAtividade(signUpComentario, userLogado));
    }

    @PostMapping("/VIAJANTE/lugar_adicionar_comentario")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Comentario> addComentarioLugar(@RequestBody SignUpComentario signUpComentario) {

        Usuario userLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return ResponseEntity.ok(comentarioService.addComentarioLugar(signUpComentario, userLogado));
    }

    @PostMapping("/VIAJANTE/roteiro_adicionar_comentario")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Comentario> addComentarioRoteiro(@RequestBody SignUpComentario signUpComentario) {

        Usuario userLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return ResponseEntity.ok(comentarioService.addComentarioRoteiro(signUpComentario, userLogado));
    }
}
