package br.edu.unicesumar.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.unicesumar.backend.domain.Endereco;
import br.edu.unicesumar.backend.domain.Usuario;
import br.edu.unicesumar.backend.dto.sign.UpdateEndereco;
import br.edu.unicesumar.backend.service.EnderecoService;

@RestController
@RequestMapping("/api/endereco")
@CrossOrigin
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @GetMapping("/endereco_usuario_logado")
    public ResponseEntity<Endereco> buscaEnderecoPeloUsuarioLogado() {
        Usuario userLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(enderecoService.buscarEnderecoPeloUsuarioLogado(userLogado));
    }

    @PutMapping("/alterar_endereco")
    public ResponseEntity<Endereco> updateContato(@RequestBody UpdateEndereco updateEndereco) {
        Usuario userLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(enderecoService.updateEndereco(updateEndereco, userLogado));
    }
}
