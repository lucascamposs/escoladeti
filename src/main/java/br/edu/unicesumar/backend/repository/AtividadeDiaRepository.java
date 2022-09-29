package br.edu.unicesumar.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.edu.unicesumar.backend.domain.Atividade;
import br.edu.unicesumar.backend.domain.AtividadeDia;

public interface AtividadeDiaRepository extends JpaRepository<AtividadeDia, Long> {

	@Query(value = "select * from atividade_dia where atividade_id = ?1", nativeQuery = true)
    List<AtividadeDia> findAtividadeDiasUsingAtividadeId(Long id);
}
