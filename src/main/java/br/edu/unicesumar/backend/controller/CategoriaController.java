package br.edu.unicesumar.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.unicesumar.backend.domain.AtividadeCategoria;
import br.edu.unicesumar.backend.domain.LugarCategoria;
import br.edu.unicesumar.backend.service.CategoriaService;

@RestController
@RequestMapping("/api/categoria")
public class CategoriaController {
	
	@Autowired
	private  CategoriaService categoriaService;
	

	@GetMapping("/categorias_de_lugar")
	public ResponseEntity<List<LugarCategoria>> BuscarTodosLugarCategoriaPaginado(){
		return ResponseEntity.ok(categoriaService.buscarTodosCategoriaLugar());
	}
	
	@GetMapping("/categorias_de_atividade")
	public ResponseEntity<List<AtividadeCategoria>> BuscarTodosAtividadeCategoriaPaginado(){
		return ResponseEntity.ok(categoriaService.buscarTodasAtividadeCategoria());
	}
	
	
	@PostMapping("/ADMIN/adicionar_categoria_atividade")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<AtividadeCategoria> SalvarNovaAtividadeCategoria(@RequestBody AtividadeCategoria atividadeCategoria){
		return ResponseEntity.ok(categoriaService.salvarCategoriaAtividade(atividadeCategoria));
	}
	
	@PostMapping("/ADMIN/adicionar_categoria_lugar")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<LugarCategoria> SalvarNovoLugarCategoria( @RequestBody LugarCategoria lugarCategoria){
		return ResponseEntity.ok(categoriaService.salvarCategoriaLugar(lugarCategoria));
	}
	


}
