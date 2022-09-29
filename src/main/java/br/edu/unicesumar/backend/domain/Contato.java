package br.edu.unicesumar.backend.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import javax.validation.constraints.NotNull;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import br.edu.unicesumar.backend.domain.enums.TipoContato;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "contato")
public class Contato {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "contato_id")
	@JsonProperty(access = Access.READ_ONLY)
    private Long contatoId;
	
	@Enumerated(EnumType.STRING)
    private TipoContato tipoContato;
	
	@NotNull(message = "Numero não pode ser nulo")
	private Long numero;
	
	public void popularDadosTeste() {
		this.tipoContato = tipoContato.CELULAR;
		this.numero = 1234567890L;
	}
	
}
