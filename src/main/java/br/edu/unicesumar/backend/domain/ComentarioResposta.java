package br.edu.unicesumar.backend.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comentario_resposta")
public class ComentarioResposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comentario_resposta_id")
    private Long comentarioRespostaId;

    @NotEmpty(message = "Descrição não pode ser vazio")
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "lugar_id")
    @NotNull(message = "Comentario não pode ser nulo")
    private Comentario comentario;
}
