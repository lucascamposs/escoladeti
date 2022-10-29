package br.edu.unicesumar.backend.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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
}
