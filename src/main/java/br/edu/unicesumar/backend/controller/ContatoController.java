package br.edu.unicesumar.backend.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import br.edu.unicesumar.backend.dto.sign.DtoContato;
import br.edu.unicesumar.backend.repository.ContatoRepository;
import br.edu.unicesumar.backend.service.ContatoService;


@CrossOrigin
@RestController
@RequestMapping("/api/contato")
public class ContatoController {
	@Autowired
	private ContatoService service;
	@Autowired
	private ContatoRepository repository;
	
	//GET
	@GetMapping("/contato/{id}")
	public ResponseEntity<Optional<Contato>> getContatoPorId(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(service.buscarContatoPorId(id));
    }
	
	//POST
	@PostMapping("/Adicionar_Contato/{id}")
	public ResponseEntity<Contato> addContato(@RequestBody DtoContato contatoDTO){
		Usuario userLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return ResponseEntity.ok(service.adicionarContato(contatoDTO, userLogado));
	}
	
	
	//PUT
	@PutMapping("/Alterar_Contato/{id}")
	public ResponseEntity<Contato> updateContato(@PathVariable(name = "id")Long id, @RequestBody Contato contato){
		
		contato.setContatoId(id);
		
		Contato contatoAtualizado =  service.atualizarContato(contato).orElse(null);
		if(contatoAtualizado != null) {
			return ResponseEntity.ok(contatoAtualizado);
		}else {
			return ResponseEntity.notFound().build();
		}
		//return service.atualizarContato(contato).map(c -> ResponseEntity.ok(c)).orElseGet(ResponseEntity.notFound().build());

		
	}
	//DELETE
	@DeleteMapping("/Deletar_Contato/{id}")
	public void deleteContato(@PathVariable(name = "id")Long id) {
		service.deleteContatoPorId(id);
	}
	

}
