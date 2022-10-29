package br.edu.unicesumar.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.unicesumar.backend.domain.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    boolean existsById(Long id);

}
