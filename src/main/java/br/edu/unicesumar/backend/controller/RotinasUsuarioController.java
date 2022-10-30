package br.edu.unicesumar.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.unicesumar.backend.domain.Usuario;
import br.edu.unicesumar.backend.dto.sign.EntrarSairAtividadeHorario;
import br.edu.unicesumar.backend.dto.sign.EntrarSairRoteiro;
import br.edu.unicesumar.backend.service.RotinasUsuarioService;
import br.edu.unicesumar.backend.service.UsuarioService;

@RestController
@RequestMapping("/api/rotinas_usuario")
public class RotinasUsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RotinasUsuarioService rotinasUsuarioService;

    @PostMapping("/VIAJANTE/entrar_em_roteiro")
    @PreAuthorize("hasRole('USER')")
    public void entrarEmRoteiro(EntrarSairRoteiro entrarSairRoteiro) {

        Usuario userLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        rotinasUsuarioService.entrarEmRoteiro(entrarSairRoteiro, userLogado);

    }

    @PostMapping("/VIAJANTE/sair_de_roteiro")
    @PreAuthorize("hasRole('USER')")
    public void sairDeRoteiro(EntrarSairRoteiro entrarSairRoteiro) {

        Usuario userLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        rotinasUsuarioService.sairDeRoteiro(entrarSairRoteiro, userLogado);

    }

    @PostMapping("/VIAJANTE/entrar_em_atividade_horario")
    @PreAuthorize("hasRole('USER')")
    public void entrarEmAtividadeHorario(EntrarSairAtividadeHorario entrarSairAtividadeHorario) {

        Usuario userLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        rotinasUsuarioService.entrarEmAtividadeHorario(entrarSairAtividadeHorario, userLogado);

    }

    @PostMapping("/VIAJANTE/sair_de_atividade_horario")
    @PreAuthorize("hasRole('USER')")
    public void sairDeAtividadeHorario(EntrarSairAtividadeHorario entrarSairAtividadeHorario) {

        Usuario userLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        rotinasUsuarioService.sairDeAtividadeHorario(entrarSairAtividadeHorario, userLogado);

    }

}
