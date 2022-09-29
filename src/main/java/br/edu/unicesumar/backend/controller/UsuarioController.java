package br.edu.unicesumar.backend.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.unicesumar.backend.domain.Agencia;
import br.edu.unicesumar.backend.domain.Contato;
import br.edu.unicesumar.backend.domain.Usuario;
import br.edu.unicesumar.backend.domain.Viajante;
import br.edu.unicesumar.backend.dto.sign.UpdatePassword;
import br.edu.unicesumar.backend.service.UsuarioService;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;

    @GetMapping("/me")
    public ResponseEntity<Usuario> getMe() {
    	Usuario userLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(userLogado);
    }
    
    @PutMapping("/alterar_senha")
    public void updatePassword(UpdatePassword updatePassword){
    	Usuario userLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	usuarioService.updatePassword(updatePassword, userLogado);
    }
    
    
    @PutMapping("/alterar_dados_agencia/{id}")
    public ResponseEntity<Agencia> updateUsuarioAgencia(@PathVariable(name = "id")Long id , @RequestBody Agencia agencia){
    	agencia.setAgenciaId(id);

    	Agencia usuarioAgenciaAtualizado = usuarioService.atualizarUsuarioAgencia(agencia).orElse(null);
    	if(usuarioAgenciaAtualizado != null) {
    		return ResponseEntity.ok(usuarioAgenciaAtualizado);
    	} else {
    		return ResponseEntity.notFound().build();
    	}
    }
    
    @PutMapping("/alterar_dados_viajante/{id}")
    public ResponseEntity<Viajante>updateUsuairoViajante (@PathVariable(name = "id")Long id, @RequestBody Viajante viajante){
    	viajante.setViajanteId(id);
    	
    	Viajante usuarioViajanteAtualizado = usuarioService.atualizarUsuarioViajante(viajante).orElse(null);
    	if (usuarioViajanteAtualizado != null) {
    		return ResponseEntity.ok(usuarioViajanteAtualizado);
    	}else {
    		return ResponseEntity.notFound().build();
    	}
    }
	
    

}
