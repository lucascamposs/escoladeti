package br.edu.unicesumar.backend.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.unicesumar.backend.domain.Endereco;
import br.edu.unicesumar.backend.domain.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

        boolean existsByUsername(String username);

        boolean existsByEmail(String email);

        Usuario findUsuariosByUsername(String username);

        @Query(value = "select e.* from usuario u \r\n"
                        + "left join endereco e on e.endereco_id =u.endereco_id\r\n"
                        + "where usuario_id = ?1", nativeQuery = true)
        Endereco retornarEnderecoDoUsuario(UUID id);

        @Query(value = "select case when count(vr) > 0 then true else false end from viajante_roteiros vr where vr.viajante_id = :viajanteId and vr.roteiro_id = :roteiroId", nativeQuery = true)
        Boolean existeViajanteRoteiro(@Param("viajanteId") Long id, @Param("roteiroId") Long roteiroId);

        @Query(value = "select case when count(vah) > 0 then true else false end from viajante_atividade_horarios vah where vah.viajante_id = :viajanteId and vah.atividade_horario_id = :atividadeHorarioId", nativeQuery = true)
        Boolean existeViajanteAtividadeHorario(@Param("viajanteId") Long id,
                        @Param("atividadeHorarioId") Long atividadeHorarioId);

        @Query(value = "select case when count(vl) > 0 then true else false end from viajante_lugares vl where vl.viajante_id = :viajanteId and vl.lugar_id = :lugarId", nativeQuery = true)
        Boolean existeViajanteLugar(@Param("viajanteId") Long viajanteId, @Param("lugarId") Long lugarId);
}
