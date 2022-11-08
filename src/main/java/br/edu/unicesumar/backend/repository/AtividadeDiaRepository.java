package br.edu.unicesumar.backend.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.unicesumar.backend.domain.AtividadeDia;

public interface AtividadeDiaRepository extends JpaRepository<AtividadeDia, Long> {

	@Query(value = "select * from atividade_dia where atividade_id = ?1", nativeQuery = true)
    List<AtividadeDia> findAtividadeDiasUsingAtividadeId(Long id);

    @Query(value = "select case when count(principal) > 0 then true else false end from (select a.agencia_id, ad.dia_disponivel from atividade_dia ad left join atividade a on a.atividade_id = ad.atividade_id where a.agencia_id = :agenciaId and ad.dia_disponivel = :diaDisponivel) principal", nativeQuery = true)
    Boolean diaDisponivelCadastradoByAgenciaId(@Param("agenciaId") Long agenciaId, @Param("diaDisponivel") LocalDate diaDisponivel);
}
