package br.edu.unicesumar.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.unicesumar.backend.domain.AtividadeDia;
import br.edu.unicesumar.backend.domain.Usuario;
import br.edu.unicesumar.backend.dto.sign.SignUpAtividadeDia;
import br.edu.unicesumar.backend.dto.sign.UpdateAtividadeDia;
import br.edu.unicesumar.backend.service.AtividadeService;

@RestController
@RequestMapping("/api/atividade_dia")
public class AtividadeDiaController {

    @Autowired
    private AtividadeService atividadeService;

    @GetMapping("/atividades_dia_por_atividade/{atividadeId}")
    public ResponseEntity<List<AtividadeDia>> getAtividadesDiaPorAtividadeId(
            @PathVariable(name = "atividadeId") Long id) {
        return ResponseEntity.ok(atividadeService.getAtividadeDiasByAtividadeId(id));
    }

    @PostMapping("/AGENCIA/adicionar_atividade_dia")
    @PreAuthorize("hasRole('COMPANY')")
    public ResponseEntity<AtividadeDia> addAtividade(@RequestBody SignUpAtividadeDia signUpAtividadeDia) {

        Usuario userLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return ResponseEntity.ok(atividadeService.addAtividadeDia(signUpAtividadeDia, userLogado));
    }

    @PutMapping("/AGENCIA/alterar_atividade_dia")
    @PreAuthorize("hasRole('COMPANY')")
    public ResponseEntity<AtividadeDia> updateAtividadeDia(@RequestBody UpdateAtividadeDia updateAtividadeDia) {

        Usuario userLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        AtividadeDia atividadeDiaAtualizado = atividadeService.updateAtividadeDia(updateAtividadeDia, userLogado)
                .orElse(null);

        if (atividadeDiaAtualizado != null) {
            return ResponseEntity.ok(atividadeDiaAtualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/ADMIN/deletar_atividade_dia/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAtividade(@PathVariable(name = "id") Long id) {
        atividadeService.deleteAtividadeDiaById(id);
    }

}
