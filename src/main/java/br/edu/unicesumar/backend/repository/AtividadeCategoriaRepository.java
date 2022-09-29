package br.edu.unicesumar.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.unicesumar.backend.domain.AtividadeCategoria;

public interface AtividadeCategoriaRepository extends JpaRepository<AtividadeCategoria, Long> {

	boolean existsById(Long id);
}
