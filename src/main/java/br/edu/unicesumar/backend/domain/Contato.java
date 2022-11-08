package br.edu.unicesumar.backend.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

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
    private Long contatoId;
	
	@Enumerated(EnumType.STRING)
    private TipoContato tipoContato;
	
	@NotEmpty(message = "Numero não pode ser vazio")
	private String numero;
	
	public void popularDadosAdmin() {
		this.tipoContato = TipoContato.CELULAR;
		this.numero = "44912345678";
	}
	
}
