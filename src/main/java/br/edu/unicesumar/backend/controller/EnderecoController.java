package br.edu.unicesumar.backend.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.edu.unicesumar.backend.domain.Contato;
import br.edu.unicesumar.backend.domain.Endereco;
import br.edu.unicesumar.backend.service.EnderecoService;

@RestController
public class EnderecoController {
	@Autowired
	private EnderecoService service;
	
	
	@GetMapping("/Buscar_Endereco/{id}")
	public ResponseEntity<Optional<Endereco>> buscarEnderecoPorId(@PathVariable( name ="id")  Long id) {
		return ResponseEntity.ok(service.buscarEnderecoPorId(id));
		
	}
	
	@PutMapping("/Atualizar_Endereco/{id}")
	public ResponseEntity<Endereco> updateEndereco(@PathVariable (name = "id" )Long id , @RequestBody Endereco endereco){
		endereco.setEnderecoId(id);
		Endereco enderecoAtualizado = service.atualizarEndereco(endereco).orElse(null);
		
		if(enderecoAtualizado!= null) {
			return ResponseEntity.ok(enderecoAtualizado);
			
		}else {
			return ResponseEntity.notFound().build();
		}

		

	}

}
