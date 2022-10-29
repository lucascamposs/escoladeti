package br.edu.unicesumar.backend.domain;

import java.time.LocalDate;
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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "roteiro")
public class Roteiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roteiro_id")
    private Long roteiroId;

    @NotEmpty(message = "Nome Roteiro não pode ser vazio")
    private String nomeRoteiro;

    @NotEmpty(message = "Descricao Roteiro não pode ser vazio")
    private String descricaoRoteiro;

    @NotNull(message = "Ativo não pode ser nulo")
    private Boolean ativo;

    @NotNull(message = "Data Inicio não pode ser nulo")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataInicio;

    @NotNull(message = "Data Final não pode ser nulo")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataFinal;

    @NotNull(message = "Preço não pode ser nulo")
    private Double preco;

    @JsonProperty(access = Access.READ_ONLY)
    @NotNull(message = "Quantidade Pessoas Atual não pode ser nulo")
    private Integer quantidadePessoasAtual;

    @NotNull(message = "Quantidade Pessoas Máximo não pode ser nulo")
    private Integer quantidadePessoasMax;

    @NotNull(message = "Agência não pode ser nulo")
    @ManyToOne
    @JoinColumn(name = "agencia_id")
    @JsonIgnore
    private Agencia agencia;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "roteiro_id")
    @NotNull(message = "Fotos do Roteiro não pode ser nulo")
    @Builder.Default
    private List<RoteiroFoto> roteiroFotos = new ArrayList<>();

    @NotNull(message = "Eventos de Roteiro não pode ser nulo")
    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "roteiro_id")
    private List<RoteiroEvento> eventosRoteiro = new ArrayList<>();
}
