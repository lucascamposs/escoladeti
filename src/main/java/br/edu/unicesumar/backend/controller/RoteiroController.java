package br.edu.unicesumar.backend.controller;

import java.util.List;
import java.util.Optional;

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

import br.edu.unicesumar.backend.domain.Roteiro;
import br.edu.unicesumar.backend.domain.Usuario;
import br.edu.unicesumar.backend.dto.sign.SignUpRoteiro;
import br.edu.unicesumar.backend.dto.sign.UpdateRoteiro;
import br.edu.unicesumar.backend.service.RoteiroService;

@RestController
@RequestMapping("/api/roteiro")
public class RoteiroController {

    @Autowired
    private RoteiroService roteiroService;

    @PostMapping("/AGENCIA/adicionar_roteiro")
    @PreAuthorize("hasRole('COMPANY')")
    public ResponseEntity<Roteiro> addRoteiro(@RequestBody SignUpRoteiro signUpRoteiro) {

        Usuario userLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return ResponseEntity.ok(roteiroService.addRoteiro(signUpRoteiro, userLogado));
    }

    @GetMapping("/roteiros_por_agencia/{agenciaId}")
    public ResponseEntity<List<Roteiro>> getRoteirosPorAgencia(@PathVariable(name = "agenciaId") Long agenciaId) {
        return ResponseEntity.ok(roteiroService.getRoteirosByAgenciaId(agenciaId));
    }

    @GetMapping("/roteiros_por_lugar/{lugarId}")
    public ResponseEntity<List<Roteiro>> getRoteirosPorLugar(@PathVariable(name = "lugarId") Long lugarId) {
        return ResponseEntity.ok(roteiroService.getRoteirosByLugarId(lugarId));
    }

    @GetMapping("/roteiro_especifico/{roteiroId}")
    public ResponseEntity<Roteiro> getRoteiroPorId(@PathVariable(name = "roteiroId") Long roteiroId) {
        Optional<Roteiro> roteiroOptional = roteiroService.getRoteiroById(roteiroId);
        if (roteiroOptional.isPresent()) {
            return ResponseEntity.ok(roteiroOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/AGENCIA/alterar_roteiro")
    @PreAuthorize("hasRole('COMPANY')")
    public ResponseEntity<Roteiro> updateRoteiro(@RequestBody UpdateRoteiro updateRoteiro) {

        Usuario userLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Roteiro roteiroAtualizado = this.roteiroService.updateRoteiro(updateRoteiro, userLogado).orElse(null);

        if (roteiroAtualizado != null) {
            return ResponseEntity.ok(roteiroAtualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/ADMIN/deletar_roteiro/{roteiroId}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteRoteiro(@PathVariable(name = "roteiroId") Long roteiroId) {
        roteiroService.deleteRoteiroById(roteiroId);
    }

}
