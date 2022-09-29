package br.edu.unicesumar.backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import br.edu.unicesumar.backend.domain.Atividade;
import br.edu.unicesumar.backend.domain.Usuario;
import br.edu.unicesumar.backend.dto.sign.SignUpAtividade;
import br.edu.unicesumar.backend.service.AtividadeService;

@RestController
@RequestMapping("/api/atividade")
public class AtividadeController {
	
	@Autowired
	private AtividadeService atividadeService;
	
	@GetMapping("/atividades_por_categoria/{id}")
	public ResponseEntity<List<Atividade>> getAtividadesPorCategoria(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(atividadeService.getAtividadesPorCategoria(id));
    }
	
	
	@GetMapping("/atividades_agencia/{id}")
    public ResponseEntity<List<Atividade>> getCompanyAtividades(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(atividadeService.getCompanyAtividades(id));
    }
	
	@GetMapping("/atividade_especifica/{id}")
	public ResponseEntity<Atividade> getAtividadePorId(@PathVariable(name = "id") Long id){
		Optional<Atividade> atividadeOptional = atividadeService.getAtividadeById(id);
		if (atividadeOptional.isPresent()) {
			return ResponseEntity.ok(atividadeOptional.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping("/adicionar_atividade")
	@PreAuthorize("hasRole('COMPANY')")
	public ResponseEntity<String> addAtividade(@RequestBody SignUpAtividade signUpAtividade){
		Usuario userLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		atividadeService.addAtividade(signUpAtividade, userLogado);
		return new ResponseEntity<>("Atividade Adicionada com Sucesso", HttpStatus.OK);
	}	
	
	@PutMapping("/alterar_atividade/{id}")
	@PreAuthorize("hasRole('COMPANY')")
	public ResponseEntity<Atividade> updateAtividade(@PathVariable(name = "id") Long id, @RequestBody Atividade atividade) {

        if (!id.equals(atividade.getAtividadeId())) {
            return ResponseEntity.badRequest().build();
        }

        Atividade atividadeAtualizado = atividadeService.updateAtividade(atividade).orElse(null);
 
        if (atividadeAtualizado != null) {
            return ResponseEntity.ok(atividadeAtualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
	
	@DeleteMapping("/deletar_atividade/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public void deleteAtividade(@PathVariable(name = "id") Long id){
		atividadeService.deleteAtividadeById(id);
	}
}
