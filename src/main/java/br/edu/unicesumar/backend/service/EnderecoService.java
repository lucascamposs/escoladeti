package br.edu.unicesumar.backend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

import br.edu.unicesumar.backend.domain.Endereco;
import br.edu.unicesumar.backend.repository.EnderecoRepository;

@Service
public class EnderecoService {
	
	@Autowired
	private EnderecoRepository repository;
	
	//GET
	public Optional<Endereco> buscarEnderecoPorId(Long id ){
		return repository.findById(id);
	}
	//PUT
	public Optional<Endereco> atualizarEndereco(Endereco endereco) {
		if(repository.existsById(endereco.getEnderecoId())) {
			Endereco enderecoAtualizado = repository.save(endereco);
			return Optional.of(enderecoAtualizado);		
		}else {
			return Optional.empty();
		}
		
		
		
		
	}

}
