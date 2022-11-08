package br.edu.unicesumar.backend.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roteiro_foto")
public class RoteiroFoto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "roteiro_foto_id")
    private Long roteiroFotoId;

	@NotEmpty(message = "Imagem não pode ser nulo")
	@Column(columnDefinition="TEXT")
	private String imagem;
}
