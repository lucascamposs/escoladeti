package br.edu.unicesumar.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.edu.unicesumar.backend.domain.Lugar;

public interface LugarRepository extends JpaRepository<Lugar, Long> {

	@Query(value = "select * from lugar where lugar_categoria_id = ?1 and lugar_indicado = false", nativeQuery = true)
    List<Lugar> findLugaresOficiaisUsingCategoriaId(Long id);
	
	@Query(value = "select * from lugar where lugar_categoria_id = ?1 and lugar_indicado = true", nativeQuery = true)
    List<Lugar> findLugaresIndicadosUsingCategoriaId(Long id);
	
	boolean existsById(Long id);
	
}
