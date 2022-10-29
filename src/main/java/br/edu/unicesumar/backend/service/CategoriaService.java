package br.edu.unicesumar.backend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
		
	// LUGAR BUSCAR
	public Page<LugarCategoria> BuscarTodosCategoriaLugar(Pageable pageable){
		return lugarCategoriaRepository.findAll(pageable);
	}
	
	//Necessário?
	public Optional<LugarCategoria> BuscarCategoriaLugarPorId(Long id) {
		return lugarCategoriaRepository.findById(id);
	}
	
	
	// ATIVIDADE BUSCAR
	public Page<AtividadeCategoria> BuscarTodasAtividadeCategoria(Pageable pageable){
		return atividadeCategoriaRepository.findAll(pageable);
	}

	//Necessário?
	public Optional<AtividadeCategoria> BuscarCategoriaAtividadePorId(Long id){
		return atividadeCategoriaRepository.findById(id);
	}
	
	
	
	//REGISTRO DE CATEGORIAS
	public AtividadeCategoria SalvarCategoriaAtividade(AtividadeCategoria atividadeCategoria) {
		return atividadeCategoriaRepository.save(atividadeCategoria);
	}
	
	public LugarCategoria RegistarCategoriaLugar (LugarCategoria lugarCategoria) {
		return lugarCategoriaRepository.save(lugarCategoria);
	}
	

	
	
	
	
	

}
