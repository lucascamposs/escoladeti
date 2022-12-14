package br.edu.unicesumar.backend.dto.sign;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.edu.unicesumar.backend.domain.RoteiroFoto;
import lombok.Data;

@Data
public class UpdateRoteiro {

    @NotNull(message = "Roteiro Id não pode ser nulo")
    private Long roteiroId;

    @NotEmpty(message = "Nome Roteiro não pode ser vazio")
    private String nomeRoteiro;

    @NotEmpty(message = "Descrição Roteiro não pode ser vazio")
    private String descricaoRoteiro;

    @NotNull(message = "Ativo não pode ser nulo")
    private Boolean ativo;

    @NotNull(message = "Data Inicio não pode ser nulo")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataInicio;

    @NotNull(message = "Data Final não pode ser nulo")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataFinal;

    @NotEmpty(message = "Local de Saída não pode ser vazio")
    private String localDeSaida;

    @NotEmpty(message = "Local de Chegada não pode ser vazio")
    private String localDeChegada;

    @NotNull(message = "Preço não pode ser nulo")
    private Double preco;

    @NotNull(message = "Quantidade Pessoas Máximo não pode ser nulo")
    private Integer quantidadePessoasMax;

    @NotNull(message = "Fotos do Roteiro não pode ser nulo")
    private List<RoteiroFoto> roteiroFotos = new ArrayList<>();

    @NotNull(message = "Eventos de Roteiro não pode ser nulo")
    private List<UpdateRoteiroEvento> eventosRoteiro = new ArrayList<>();
}
