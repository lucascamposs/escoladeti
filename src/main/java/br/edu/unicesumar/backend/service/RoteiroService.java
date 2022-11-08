package br.edu.unicesumar.backend.service;

import java.util.ArrayList;
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
import br.edu.unicesumar.backend.dto.sign.UpdateRoteiro;
import br.edu.unicesumar.backend.repository.AtividadeRepository;
import br.edu.unicesumar.backend.repository.LugarRepository;
import br.edu.unicesumar.backend.repository.RoteiroRepository;

@Service
public class RoteiroService {

    @Autowired
    private RoteiroRepository roteiroRepository;

    @Autowired
    private LugarRepository lugarRepository;

    @Autowired
    private AtividadeRepository atividadeRepository;

    public List<Roteiro> getRoteirosByAgenciaId(Long id) {
        return roteiroRepository.findRoteirosUsingAgenciaId(id);
    }

    public List<Roteiro> getRoteirosByLugarId(Long id) {
        return roteiroRepository.findRoteirosUsingLugarId(id);
    }

    public Optional<Roteiro> getRoteiroById(Long id) {
        return roteiroRepository.findById(id);
    }

    public Roteiro addRoteiro(SignUpRoteiro signUpRoteiro, Usuario userLogado) {

        List<RoteiroEvento> eventosRoteiro = new ArrayList<>();

        for (int i = 0; i < signUpRoteiro.getEventosRoteiro().size(); i++) {

            RoteiroEvento roteiroEventoAux = new RoteiroEvento();

            roteiroEventoAux.setNomeEvento(signUpRoteiro.getEventosRoteiro().get(i).getNomeEvento());
            roteiroEventoAux.setDescricaoEvento(signUpRoteiro.getEventosRoteiro().get(i).getDescricaoEvento());
            roteiroEventoAux.setOrdem(signUpRoteiro.getEventosRoteiro().get(i).getOrdem());

            if (signUpRoteiro.getEventosRoteiro().get(i).getLugarId() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lugar Id não pode ser nulo");
            }

            Optional<Lugar> lugarAux = lugarRepository.findById(signUpRoteiro.getEventosRoteiro().get(i).getLugarId());

            if (lugarAux.isPresent()) {
                roteiroEventoAux.setLugar(lugarAux.get());
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lugar Id não encontrado");
            }

            if (signUpRoteiro.getEventosRoteiro().get(i).getAtividadeId() != null) {

                Optional<Atividade> atividadeAux = atividadeRepository
                        .findById(signUpRoteiro.getEventosRoteiro().get(i).getAtividadeId());

                if (signUpRoteiro.getEventosRoteiro().get(i).getAtividadeId() != null && !atividadeAux.isPresent()) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Atividade Id não encontrado");
                }

                roteiroEventoAux.setAtividade(atividadeAux.get());
            }

            eventosRoteiro.add(roteiroEventoAux);
        }

        Roteiro roteiro = Roteiro.builder()
                .nomeRoteiro(signUpRoteiro.getNomeRoteiro())
                .descricaoRoteiro(signUpRoteiro.getDescricaoRoteiro())
                .ativo(true)
                .quantidadePessoasAtual(0)
                .dataInicio(signUpRoteiro.getDataInicio())
                .dataFinal(signUpRoteiro.getDataFinal())
                .localDeSaida(signUpRoteiro.getLocalDeSaida())
                .localDeChegada(signUpRoteiro.getLocalDeChegada())
                .preco(signUpRoteiro.getPreco())
                .quantidadePessoasMax(signUpRoteiro.getQuantidadePessoasMax())
                .agencia(userLogado.getAgencia())
                .roteiroFotos(signUpRoteiro.getRoteiroFotos())
                .eventosRoteiro(eventosRoteiro).build();

        return roteiroRepository.save(roteiro);

    }

    public Optional<Roteiro> updateRoteiro(UpdateRoteiro updateRoteiro, Usuario usuario) {

        Optional<Roteiro> roteiroDoBanco = roteiroRepository.findById(updateRoteiro.getRoteiroId());

        existRoteiro(roteiroDoBanco);
        verificaSeAgenciaDonaDoRoteiro(usuario, roteiroDoBanco);

        roteiroDoBanco.get().setNomeRoteiro(updateRoteiro.getNomeRoteiro());
        roteiroDoBanco.get().setDescricaoRoteiro(updateRoteiro.getDescricaoRoteiro());
        roteiroDoBanco.get().setAtivo(updateRoteiro.getAtivo());
        roteiroDoBanco.get().setDataInicio(updateRoteiro.getDataInicio());
        roteiroDoBanco.get().setDataFinal(updateRoteiro.getDataFinal());
        roteiroDoBanco.get().setLocalDeSaida(updateRoteiro.getLocalDeSaida());
        roteiroDoBanco.get().setLocalDeChegada(updateRoteiro.getLocalDeChegada());
        roteiroDoBanco.get().setPreco(updateRoteiro.getPreco());
        roteiroDoBanco.get().setQuantidadePessoasMax(updateRoteiro.getQuantidadePessoasMax());

        if (updateRoteiro.getRoteiroFotos() != null) {

            roteiroDoBanco.get().getRoteiroFotos().clear();

            for (int i = 0; i < updateRoteiro.getRoteiroFotos().size(); i++) {
                roteiroDoBanco.get().getRoteiroFotos().add(updateRoteiro.getRoteiroFotos().get(i));
            }
        }

        List<RoteiroEvento> eventosRoteiro = montarEventosRoteirosAux(updateRoteiro);

        if (updateRoteiro.getEventosRoteiro() != null) {
            roteiroDoBanco.get().getEventosRoteiro().clear();

            for (int i = 0; i < updateRoteiro.getEventosRoteiro().size(); i++) {
                roteiroDoBanco.get().getEventosRoteiro().add(eventosRoteiro.get(i));
            }
        }

        roteiroRepository.save(roteiroDoBanco.get());
        return Optional.of(roteiroDoBanco.get());

    }

    public void deleteRoteiroById(Long id) {

        Optional<Roteiro> roteiroDoBanco = roteiroRepository.findById(id);

        if (!roteiroDoBanco.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Roteiro Id não encontrado!");
        } else {
            roteiroRepository.deleteById(id);
        }
    }

    @SuppressWarnings("null")
    private List<RoteiroEvento> montarEventosRoteirosAux(UpdateRoteiro updateRoteiro) {
        List<RoteiroEvento> eventosRoteiro = null;

        for (int i = 0; i < updateRoteiro.getEventosRoteiro().size(); i++) {
            RoteiroEvento roteiroEventoAux = null;

            roteiroEventoAux.setRoteiroEventoId(updateRoteiro.getEventosRoteiro().get(i).getRoteiroEventoId());
            roteiroEventoAux.setNomeEvento(updateRoteiro.getEventosRoteiro().get(i).getNomeEvento());
            roteiroEventoAux.setDescricaoEvento(updateRoteiro.getEventosRoteiro().get(i).getDescricaoEvento());
            roteiroEventoAux.setOrdem(updateRoteiro.getEventosRoteiro().get(i).getOrdem());

            if (updateRoteiro.getEventosRoteiro().get(i).getLugarId() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lugar Id não pode ser nulo");
            }

            Optional<Lugar> lugarAux = lugarRepository.findById(updateRoteiro.getEventosRoteiro().get(i).getLugarId());

            if (lugarAux.isPresent()) {
                roteiroEventoAux.setLugar(lugarAux.get());
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lugar Id não encontrado");
            }

            if (updateRoteiro.getEventosRoteiro().get(i).getAtividadeId() != null) {

                Optional<Atividade> atividadeAux = atividadeRepository
                        .findById(updateRoteiro.getEventosRoteiro().get(i).getAtividadeId());

                if (updateRoteiro.getEventosRoteiro().get(i).getAtividadeId() != null && !atividadeAux.isPresent()) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Atividade Id não encontrado");
                }

                roteiroEventoAux.setAtividade(atividadeAux.get());
            }

            eventosRoteiro.add(roteiroEventoAux);
        }
        return eventosRoteiro;
    }

    private void verificaSeAgenciaDonaDoRoteiro(Usuario usuario, Optional<Roteiro> roteiroDoBanco) {
        if (usuario.getAgencia().getAgenciaId() != roteiroDoBanco.get().getAgencia().getAgenciaId()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Usuário inválido!");

        }
    }

    private void existRoteiro(Optional<Roteiro> roteiroDoBanco) {
        if (!roteiroDoBanco.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Roteiro Id não encontrado!");
        }
    }

}
