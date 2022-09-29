package br.edu.unicesumar.backend.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.unicesumar.backend.domain.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Usuario findUsuariosByUsername(String username);

}
