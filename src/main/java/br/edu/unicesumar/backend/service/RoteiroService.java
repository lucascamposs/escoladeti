package br.edu.unicesumar.backend.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.edu.unicesumar.backend.domain.Atividade;
import br.edu.unicesumar.backend.domain.Lugar;
import br.edu.unicesumar.backend.domain.Roteiro;
import br.edu.unicesumar.backend.domain.RoteiroEvento;
import br.edu.unicesumar.backend.domain.Usuario;
import br.edu.unicesumar.backend.dto.sign.SignUpRoteiro;
import br.edu.unicesumar.backend.repository.AtividadeRepository;
import br.edu.unicesumar.backend.repository.LugarRepository;
import br.edu.unicesumar.backend.repository.RoteiroEventoRepository;
import br.edu.unicesumar.backend.repository.RoteiroRepository;

@Service
public class RoteiroService {

	@Autowired
	private RoteiroRepository roteiroRepository;

	@Autowired
	private RoteiroEventoRepository roteiroEventoRepository;

	@Autowired
	private LugarRepository lugarRepository;

	@Autowired
	private AtividadeRepository atividadeRepository;

	public Roteiro addRoteiro(SignUpRoteiro signUpRoteiro, Usuario userLogado) {

		List<RoteiroEvento> roteiroEventosAux = new ArrayList<>();

		for (int i = 0; i < signUpRoteiro.getEventosRoteiro().size(); i++) {

			Lugar lugarAux = lugarRepository.findById(signUpRoteiro.getEventosRoteiro().get(i).getLugarId()).orElse(null);

			if (lugarAux == null) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lugar Id não encontrado!");
			}

			Atividade atividade = null;

			if (signUpRoteiro.getEventosRoteiro().get(i).getAtividadeId() != null) {
				atividade = atividadeRepository.findById(signUpRoteiro.getEventosRoteiro().get(i).getAtividadeId()).orElse(null);
			}

			RoteiroEvento roteiroEventoAux = RoteiroEvento.builder()
					.nomeEvento(signUpRoteiro.getEventosRoteiro().get(i).getNomeEvento())
					.descricaoEvento(signUpRoteiro.getEventosRoteiro().get(i).getDescricaoEvento())
					.ordem(signUpRoteiro.getEventosRoteiro().get(i).getOrdem()).lugar(lugarAux).atividade(atividade)
					.build();

			roteiroEventoRepository.save(roteiroEventoAux);
			roteiroEventosAux.add(roteiroEventoAux);
		}

		Roteiro roteiro = Roteiro.builder()
				.nomeRoteiro(signUpRoteiro.getNomeRoteiro())
				.descricaoRoteiro(signUpRoteiro.getDescricaoRoteiro())
				.ativo(true)
				.dataInicio(signUpRoteiro.getDataInicio())
				.dataFinal(signUpRoteiro.getDataFinal())
				.preco(signUpRoteiro.getPreco())
				.quantidadePessoasMax(signUpRoteiro.getQuantidadePessoasMax())
				.agencia(userLogado.getAgencia())
				.eventosRoteiro(roteiroEventosAux).build();

		return roteiroRepository.save(roteiro);
	
	}

	public List<Roteiro> getRoteirosByAgenciaId (Long id) {
		return roteiroRepository.findRoteirosUsingAgenciaId(id);
	}

	public List<Roteiro> getRoteirosByLugarId (Long id) {
		return roteiroRepository.findRoteirosUsingLugarId(id);
	}

	public Optional<Roteiro> getRoteiroById(Long id) {
		return roteiroRepository.findById(id);
	}

	public Optional<Roteiro> updateRoteiro(Roteiro roteiroExistente){
		if (roteiroRepository.existsById(roteiroExistente.getRoteiroId())) {
			Roteiro roteiroAtualizado = roteiroRepository.save(roteiroExistente);
			return Optional.of(roteiroAtualizado);
		}	return Optional.empty();
	}

	public void deleteRoteiroById(Long id) {
		if (roteiroRepository.existsById(id)) {
			roteiroRepository.deleteById(id);
		}	else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Roteiro ID não encontrada!");
		}
	}

}
