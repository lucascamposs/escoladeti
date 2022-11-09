package br.edu.unicesumar.backend.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comentario")
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comentario_id")
    private Long comentarioId;

    @NotNull(message = "Avaliação não pode ser nulo")
    private Integer avaliacao;

    @NotEmpty(message = "Descrição não pode ser vazio")
    @Column(columnDefinition = "TEXT")
    private String descricao;

    @JsonProperty(access = Access.READ_ONLY)
    private Long curtidas;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "comentario_id")
    @Builder.Default
    private List<ComentarioFoto> comentarioFotos = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "viajante_id")
    @JsonProperty(access = Access.READ_ONLY)
    private Viajante viajante;

    @ManyToOne
    @JoinColumn(name = "lugar_id")
    private Lugar lugar;

    @ManyToOne
    @JoinColumn(name = "atividade_id")
    private Atividade atividade;

    @ManyToOne
    @JoinColumn(name = "roteiro_id")
    private Roteiro roteiro;
}
