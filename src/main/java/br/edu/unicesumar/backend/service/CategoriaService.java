package br.edu.unicesumar.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.unicesumar.backend.domain.AtividadeCategoria;
import br.edu.unicesumar.backend.domain.LugarCategoria;
import br.edu.unicesumar.backend.repository.AtividadeCategoriaRepository;
import br.edu.unicesumar.backend.repository.LugarCategoriaRepository;

@Service
public class CategoriaService {
    
	@Autowired
	private LugarCategoriaRepository lugarCategoriaRepository;
	
	@Autowired
	private AtividadeCategoriaRepository atividadeCategoriaRepository;
		
	public List<LugarCategoria> buscarTodosCategoriaLugar(){
		return lugarCategoriaRepository.findAll();
	}
	
	public List<AtividadeCategoria> buscarTodasAtividadeCategoria(){
		return atividadeCategoriaRepository.findAll();
	}	

	public AtividadeCategoria salvarCategoriaAtividade(AtividadeCategoria atividadeCategoria) {
		return atividadeCategoriaRepository.save(atividadeCategoria);
	}
	
	public LugarCategoria salvarCategoriaLugar (LugarCategoria lugarCategoria) {
		return lugarCategoriaRepository.save(lugarCategoria);
	}
	

	
	
	
	
	

}
