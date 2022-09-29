package br.edu.unicesumar.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.edu.unicesumar.backend.domain.Lugar;
import br.edu.unicesumar.backend.domain.LugarCategoria;
import br.edu.unicesumar.backend.domain.Usuario;
import br.edu.unicesumar.backend.dto.sign.SignUpLugar;
import br.edu.unicesumar.backend.repository.LugarRepository;

@Service
public class LugarService {

	@Autowired
	private LugarRepository lugarRepository;

	@Autowired
	private CategoriaService categoriaService;

	public List<Lugar> getLugaresOficiaisPorCategoria(Long id) {
		return lugarRepository.findLugaresOficiaisUsingCategoriaId(id);
	}

	public List<Lugar> getLugaresIndicadosPorCategoria(Long id) {
		return lugarRepository.findLugaresIndicadosUsingCategoriaId(id);
	}

	public Optional<Lugar> getLugarPorId(Long id) {
		return lugarRepository.findById(id);
	}

	public Lugar addLugar(SignUpLugar signUpLugar, Usuario usuario) {

		Optional<LugarCategoria> lugarCategoriaOpt = categoriaService
				.BuscarCategoriaLugarPorId(signUpLugar.getLugarCategoriaId());

		if (lugarCategoriaOpt.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria Id não encontrada!");
		} else {

			Lugar lugar = Lugar.builder().lugarIndicado(signUpLugar.getLugarIndicado())
					.nomeLugar(signUpLugar.getNomeLugar()).descricao(signUpLugar.getDescricao())
					.coordenada(signUpLugar.getCoordenada()).ativo(true).usuarioQueCriou(usuario)
					.lugarCategoria(lugarCategoriaOpt.get()).build();

			return lugarRepository.save(lugar);
		}

	}

	public Optional<Lugar> updateLugar(Lugar lugarExistente) {
		if (lugarRepository.existsById(lugarExistente.getLugarId())) {
			Lugar lugarAtualizado = lugarRepository.save(lugarExistente);
			return Optional.of(lugarAtualizado);
		}
		return Optional.empty();
	}

	public void deleteLugarById(Long id) {
		if (lugarRepository.existsById(id)) {
			lugarRepository.deleteById(id);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lugar Id não encontrado!");
		}

	}
}
