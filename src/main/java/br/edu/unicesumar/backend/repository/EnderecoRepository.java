package br.edu.unicesumar.backend.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.unicesumar.backend.domain.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    boolean existsById(Long id);

    @Query(value = "select * from endereco where usuario_id = ?1", nativeQuery = true)
    List<Endereco> findEnderecosUsingUsuarioUUID(UUID id);

    @Query(value = "select case when count(e) > 0 then true else false end from endereco e where e.usuario_id = :usuarioId and e.endereco_id = :idEndereco", nativeQuery = true)
    Boolean pertenceAoUsuario(@Param("usuarioId") UUID id, @Param("idEndereco") Long idEndereco);

}
