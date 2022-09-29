package br.edu.unicesumar.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.unicesumar.backend.domain.LugarCategoria;

public interface LugarCategoriaRepository extends JpaRepository<LugarCategoria, Long> {

	boolean existsById(Long id);
}
