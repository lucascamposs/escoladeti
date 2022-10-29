package br.edu.unicesumar.backend.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.unicesumar.backend.domain.Contato;

public interface ContatoRepository extends JpaRepository<Contato, Long> {

    boolean existsById(Long id);

    @Query(value = "select * from contato where usuario_id = ?1", nativeQuery = true)
    List<Contato> findContatosUsingUsuarioUUID(UUID id);

    @Query(value = "select case when count(c) > 0 then true else false end from contato c where c.usuario_id = :usuarioId and c.contato_id = :idContato", nativeQuery = true)
    Boolean pertenceAoUsuario(@Param("usuarioId") UUID id, @Param("idContato") Long idContato);

    @Query(value = "delete from contato where contato_id = ?1", nativeQuery = true)
    void deletarById(Long id);
}
