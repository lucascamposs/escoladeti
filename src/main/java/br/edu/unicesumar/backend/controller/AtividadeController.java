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

import br.edu.unicesumar.backend.domain.Atividade;
import br.edu.unicesumar.backend.domain.Usuario;
import br.edu.unicesumar.backend.dto.sign.SignUpAtividade;
import br.edu.unicesumar.backend.dto.sign.UpdateAtividade;
import br.edu.unicesumar.backend.service.AtividadeService;

@RestController
@RequestMapping("/api/atividade")
@CrossOrigin
public class AtividadeController {

    @Autowired
    private AtividadeService atividadeService;

    @GetMapping("/atividades_por_categoria/{categoriaId}")
    public ResponseEntity<List<Atividade>> getAtividadesPorCategoria(@PathVariable(name = "categoriaId") Long categoriaId) {
        return ResponseEntity.ok(atividadeService.getAtividadesPorCategoria(categoriaId));
    }

    @GetMapping("/atividades_agencia/{agenciaId}")
    public ResponseEntity<List<Atividade>> getCompanyAtividades(@PathVariable(name = "agenciaId") Long agenciaId) {
        return ResponseEntity.ok(atividadeService.getCompanyAtividades(agenciaId));
    }

    @GetMapping("/atividade_especifica/{atividadeId}")
    public ResponseEntity<Atividade> getAtividadePorId(@PathVariable(name = "atividadeId") Long atividadeId) {
        Optional<Atividade> atividadeOptional = atividadeService.getAtividadeById(atividadeId);
        if (atividadeOptional.isPresent()) {
            return ResponseEntity.ok(atividadeOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/AGENCIA/adicionar_atividade")
    @PreAuthorize("hasRole('COMPANY')")
    public ResponseEntity<Atividade> addAtividade(@RequestBody SignUpAtividade signUpAtividade) {

        Usuario userLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return ResponseEntity.ok(atividadeService.addAtividade(signUpAtividade, userLogado));
    }

    @PutMapping("/AGENCIA/alterar_atividade")
    @PreAuthorize("hasRole('COMPANY')")
    public ResponseEntity<Atividade> updateAtividade(@RequestBody UpdateAtividade updateAtividade) {

        Usuario userLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Atividade atividadeAtualizado = atividadeService.updateAtividade(updateAtividade, userLogado).orElse(null);

        if (atividadeAtualizado != null) {
            return ResponseEntity.ok(atividadeAtualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/ADMIN/deletar_atividade/{atividadeId}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAtividade(@PathVariable(name = "atividadeId") Long atividadeId) {
        atividadeService.deleteAtividadeById(atividadeId);
    }
}
