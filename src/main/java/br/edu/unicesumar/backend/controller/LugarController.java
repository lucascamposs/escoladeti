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

import br.edu.unicesumar.backend.domain.Lugar;
import br.edu.unicesumar.backend.domain.Usuario;
import br.edu.unicesumar.backend.dto.sign.SignUpLugar;
import br.edu.unicesumar.backend.service.LugarService;

@RestController
@RequestMapping("/api/lugar")
public class LugarController {

	@Autowired
	private LugarService lugarService;
		
	@GetMapping("/lugares_oficiais_por_categoria/{id}")
	public ResponseEntity<List<Lugar>> getLugaresOficiaisPorCategoria(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(lugarService.getLugaresOficiaisPorCategoria(id));
    }
	
	@GetMapping("/lugares_indicados_por_categoria/{id}")
	public ResponseEntity<List<Lugar>> getLugaresIndicadosPorCategoria(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(lugarService.getLugaresIndicadosPorCategoria(id));
    }
	
	@GetMapping("/lugar_especifico/{id}")
	public ResponseEntity<Lugar> getLugarPorId(@PathVariable(name = "id") Long id){
		Optional<Lugar> lugarOptional = lugarService.getLugarPorId(id);
		if (lugarOptional.isPresent()) {
			return ResponseEntity.ok(lugarOptional.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping("/adicionar_lugar")
	public ResponseEntity<Lugar> addLugar(@RequestBody SignUpLugar signUpLugar){
		Usuario userLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return ResponseEntity.ok(lugarService.addLugar(signUpLugar, userLogado));
	}	
	
	@PutMapping("/alterar_lugar/{id}")
	@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Lugar> updateLugar(@PathVariable(name = "id") Long id, @RequestBody Lugar lugar) {

        if (!id.equals(lugar.getLugarId())) {
            return ResponseEntity.badRequest().build();
        }

        Lugar lugarAtualizado = lugarService.updateLugar(lugar).orElse(null);
 
        if (lugarAtualizado != null) {
            return ResponseEntity.ok(lugarAtualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
	
	@DeleteMapping("/deletar_lugar/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public void deleteLugar(@PathVariable(name = "id") Long id){
		lugarService.deleteLugarById(id);
	}
	
}
