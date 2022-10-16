package br.edu.unicesumar.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.unicesumar.backend.domain.AtividadeCategoria;
import br.edu.unicesumar.backend.domain.LugarCategoria;
import br.edu.unicesumar.backend.dto.sign.DtoAtividadeCategoria;
import br.edu.unicesumar.backend.dto.sign.DtoLugarCategoria;
import br.edu.unicesumar.backend.service.CategoriaService;

@CrossOrigin
@RestController
@RequestMapping("/api/categoria")
public class CategoriaController {
	
	@Autowired
	private  CategoriaService categoriaService;
	

	@GetMapping("/buscar_todas_categorias_lugar")
	public ResponseEntity<Page<LugarCategoria>> BuscarTodosLugarCategoriaPaginado(Pageable pageable){
		return ResponseEntity.ok(categoriaService.BuscarTodosCategoriaLugar(pageable));
	}
	
	@GetMapping("/buscar_todas_categorias_atividade")
	public ResponseEntity<Page<AtividadeCategoria>> BuscarTodosAtividadeCategoriaPaginado(Pageable pageable){
		return ResponseEntity.ok(categoriaService.BuscarTodasAtividadeCategoria(pageable));
	}
	
	
	@PostMapping("/salvar_nova_categoria_atividade")
	//@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<AtividadeCategoria> SalvarNovaAtividadeCategoria(@RequestBody DtoAtividadeCategoria atividadeCategoria){
		return ResponseEntity.ok(categoriaService.SalvarCategoriaAtividade(atividadeCategoria));
	}
	
	@PostMapping("/salvar_nova_categoria_lugar")
	//@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<LugarCategoria> SalvarNovoLugarCategoria( @RequestBody DtoLugarCategoria lugarCategoria){
		return ResponseEntity.ok(categoriaService.RegistarCategoriaLugar(lugarCategoria));
	}
	


}
