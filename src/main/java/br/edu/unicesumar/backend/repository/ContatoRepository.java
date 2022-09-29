package br.edu.unicesumar.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.unicesumar.backend.domain.Contato;

public interface ContatoRepository extends JpaRepository<Contato, Long> {
	
	
	boolean existsById(Long id);

}
