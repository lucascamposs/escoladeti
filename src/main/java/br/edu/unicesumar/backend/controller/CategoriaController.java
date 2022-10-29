package br.edu.unicesumar.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.unicesumar.backend.domain.Atividade;
import br.edu.unicesumar.backend.domain.AtividadeCategoria;
import br.edu.unicesumar.backend.domain.LugarCategoria;
import br.edu.unicesumar.backend.service.CategoriaService;

@RestController
@RequestMapping("/api/categoria")
public class CategoriaController {
	
	@Autowired
	private  CategoriaService categoriaService;
	

	@GetMapping("/categorias_de_lugar")
	public ResponseEntity<Page<LugarCategoria>> BuscarTodosLugarCategoriaPaginado(Pageable pageable){
		return ResponseEntity.ok(categoriaService.BuscarTodosCategoriaLugar(pageable));
	}
	
	@GetMapping("/categorias_de_atividade")
	public ResponseEntity<Page<AtividadeCategoria>> BuscarTodosAtividadeCategoriaPaginado(Pageable pageable){
		return ResponseEntity.ok(categoriaService.BuscarTodasAtividadeCategoria(pageable));
	}
	
	
	@PostMapping("/adicionar_categoria_atividade")
	//@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<AtividadeCategoria> SalvarNovaAtividadeCategoria(@RequestBody AtividadeCategoria atividadeCategoria){
		return ResponseEntity.ok(categoriaService.SalvarCategoriaAtividade(atividadeCategoria));
	}
	
	@PostMapping("/adicionar_categoria_lugar")
	//@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<LugarCategoria> SalvarNovoLugarCategoria( @RequestBody LugarCategoria lugarCategoria){
		return ResponseEntity.ok(categoriaService.RegistarCategoriaLugar(lugarCategoria));
	}
	


}
