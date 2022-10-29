package br.edu.unicesumar.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.edu.unicesumar.backend.domain.Comentario;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

    @Query(value = "select * from comentario where atividade_id = ?1", nativeQuery = true)
    List<Comentario> comentariosByAtividadeId(Long id);

    @Query(value = "select * from comentario where lugar_id = ?1", nativeQuery = true)
    List<Comentario> comentariosByLugarId(Long id);

    @Query(value = "select * from comentario where roteiro_id = ?1", nativeQuery = true)
    List<Comentario> comentariosByRoteiroId(Long id);
}
