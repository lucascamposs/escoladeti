package br.edu.unicesumar.backend.domain;

import java.util.ArrayList;
import java.util.List;

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
@Table(name = "lugar")
public class Lugar {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "lugar_id")
	@JsonProperty(access = Access.READ_ONLY)
    private Long lugarId;
	
	@NotEmpty(message = "Nome do Lugar não pode ser vazio")
	private String nomeLugar;
	
	@NotEmpty(message = "Descrição do Lugar não pode ser vazio")
	private String descricao;
	
	@NotNull(message = "Lugar Indicado não pode ser nulo")
    private Boolean lugarIndicado;
	
	@NotEmpty(message = "Coordenada não pode ser vazio")
	private String coordenada;
	
	@NotNull(message = "Ativo não pode ser nulo")
	private Boolean ativo;
	
	@NotNull(message = "Usuario que criou não pode ser nulo")
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	@JsonIgnore
	private Usuario usuarioQueCriou;
	
	@ManyToOne
	@JoinColumn(name = "lugar_categoria_id")
	@NotNull(message = "Categoria de Lugar não pode ser nulo")
	private LugarCategoria lugarCategoria;
	
	@OneToMany
	@JoinColumn(name= "lugar_id")
	@NotNull(message = "Fotos do Lugar não pode ser nulo")
	@Builder.Default
	private List<LugarFoto> lugarFotos = new ArrayList<>();
}
