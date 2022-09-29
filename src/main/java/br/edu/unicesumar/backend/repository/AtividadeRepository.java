package br.edu.unicesumar.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.edu.unicesumar.backend.domain.Atividade;

public interface AtividadeRepository extends JpaRepository<Atividade, Long>{
	
	@Query(value = "select * from atividade where agencia_id = ?1", nativeQuery = true)
    List<Atividade> findAtividadesUsingAgenciaId(Long id);
	
	@Query(value = "select * from atividade where atividade_categoria_id = ?1", nativeQuery = true)
    List<Atividade> findAtividadesUsingCategoriaId(Long id);
}
