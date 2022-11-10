package br.edu.unicesumar.backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.unicesumar.backend.domain.Contato;
import br.edu.unicesumar.backend.domain.Usuario;
import br.edu.unicesumar.backend.dto.sign.SignUpContato;
import br.edu.unicesumar.backend.dto.sign.UpdateContato;
import br.edu.unicesumar.backend.service.ContatoService;

@RestController
@RequestMapping("/api/contato")
@CrossOrigin
public class ContatoController {
    @Autowired
    private ContatoService contatoService;

    @GetMapping("/contatos_usuario_logado")
    public ResponseEntity<List<Contato>> getContatoPorId() {
        Usuario userLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(contatoService.buscarContatosDoUsuarioLogado(userLogado));
    }

    @PostMapping("/AGENCIA/adicionar_contato")
    @PreAuthorize("hasRole('COMPANY')")
    public ResponseEntity<List<Contato>> addContato(@RequestBody SignUpContato signUpContato) {
        Usuario userLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(contatoService.adicionarContato(signUpContato, userLogado));
    }

    @PutMapping("/alterar_contato")
    public ResponseEntity<Optional<List<Contato>>> updateContato(@RequestBody List<UpdateContato> listUpdateContato) {
        Usuario userLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(contatoService.updateContatos(listUpdateContato, userLogado));
    }

    @DeleteMapping("/AGENCIA/deletar_contato/{contatoId}")
    @PreAuthorize("hasRole('COMPANY')")
    public void deleteContato(@PathVariable(name = "contatoId") Long contatoId) {
        Usuario userLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        contatoService.deleteContatoPorId(contatoId, userLogado);
    }

}
