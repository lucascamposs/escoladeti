package br.edu.unicesumar.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.edu.unicesumar.backend.domain.Roteiro;

public interface RoteiroRepository extends JpaRepository<Roteiro, Long>{

	@Query(value = "select * from roteiro where agencia_id = ?1", nativeQuery = true)
    List<Roteiro> findRoteirosUsingAgenciaId(Long id);
	
	@Query(value = "select \r\n"
			+ "ro.*,\r\n"
			+ "re.lugar_id\r\n"
			+ "from roteiro ro\r\n"
			+ "left join roteiro_evento re  on re.roteiro_id = ro.roteiro_id\r\n"
			+ "where re.lugar_id = ?1\r\n"
			+ "group by \r\n"
			+ "ro.roteiro_id,\r\n"
			+ "re.lugar_id ", nativeQuery = true)
	List<Roteiro> findRoteirosUsingLugarId(Long id);
}
