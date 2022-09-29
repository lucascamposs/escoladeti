package br.edu.unicesumar.backend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.edu.unicesumar.backend.domain.Contato;
import br.edu.unicesumar.backend.domain.Usuario;
import br.edu.unicesumar.backend.dto.sign.DtoContato;
import br.edu.unicesumar.backend.repository.ContatoRepository;

@Service
public class ContatoService {
	
	@Autowired
	private ContatoRepository repository;
	
	//GET
	public Optional<Contato> buscarContatoPorId(Long id) {
		return repository.findById(id);
	}
	// DELETE
	public void deleteContatoPorId(Long id) {
		if(repository.existsById(id)) {
			repository.deleteById(id);	
		}else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID de contato não encontrado!");
		}
	}
	//PUT
	
	public Optional<Contato> atualizarContato(Contato contatoExistente ) {
		if(repository.existsById(contatoExistente.getContatoId())) {
			Contato contatoAtualizado = repository.save(contatoExistente);
			return Optional.of(contatoAtualizado);
		}
		else {
			return Optional.empty();
		}
		
	}
	
	//POST
	public Contato adicionarContato(DtoContato contatoDTO,  Usuario usuario) {
		Contato contato = Contato.builder()
				.tipoContato(contatoDTO.getTipoContato())
				.numero(contatoDTO.getNumero()).build();
		
		return repository.save(contato);
		

	}
	
	
}
