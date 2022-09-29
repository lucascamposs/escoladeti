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

import br.edu.unicesumar.backend.domain.Roteiro;
import br.edu.unicesumar.backend.domain.Usuario;
import br.edu.unicesumar.backend.dto.sign.SignUpRoteiro;
import br.edu.unicesumar.backend.service.RoteiroService;

@RestController
@RequestMapping("/api/roteiro")
public class RoteiroController {
	
	@Autowired
	private RoteiroService roteiroService;
	
	@PostMapping("/adicionar_roteiro")
	@PreAuthorize("hasRole('COMPANY')")
	public ResponseEntity<String> addRoteiro(@RequestBody SignUpRoteiro signUpRoteiro){
		Usuario userLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		roteiroService.addRoteiro(signUpRoteiro, userLogado);
		return new ResponseEntity<>("Roteiro adicionado com sucesso", HttpStatus.CREATED);
	}
	
	@GetMapping("/roteiros_por_agencia/{id}")
	public ResponseEntity<List<Roteiro>> getRoteirosPorAgencia(@PathVariable(name = "id") Long id){
		return ResponseEntity.ok(roteiroService.getRoteirosByAgenciaId(id));
	}
	
	@GetMapping("/roteiros_por_lugar/{id}")
	public ResponseEntity<List<Roteiro>> getRoteirosPorLugar(@PathVariable(name = "id") Long id){
		return ResponseEntity.ok(roteiroService.getRoteirosByLugarId(id));
	}
	
	@GetMapping("/roteiro_especifico/{id}")
	public ResponseEntity<Roteiro> getRoteiroPorId(@PathVariable(name = "id") Long id){
		Optional<Roteiro> roteiroOptional = roteiroService.getRoteiroById(id);
		if (roteiroOptional.isPresent()) {
			return ResponseEntity.ok(roteiroOptional.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PutMapping("/alterar_roteiro/{id}")
	@PreAuthorize("hasRole('COMPANY')")
    public ResponseEntity<Roteiro> updateRoteiro(@PathVariable(name = "id") Long id, @RequestBody Roteiro roteiro) {

        if (!id.equals(roteiro.getRoteiroId())) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Roteiro> roteiroAtualizadoOptional = this.roteiroService.updateRoteiro(roteiro);
 
        if (roteiroAtualizadoOptional.isPresent()) {
            return ResponseEntity.ok(roteiroAtualizadoOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
	
	@DeleteMapping("/deletar_roteiro/{id}")
	public void deleteRoteiro(@PathVariable(name = "id") Long id){
		roteiroService.deleteRoteiroById(id);
	}
	
}
