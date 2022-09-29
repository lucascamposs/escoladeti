package br.edu.unicesumar.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.unicesumar.backend.domain.Agencia;

public interface AgenciaRepository extends JpaRepository<Agencia, Long> {

}
