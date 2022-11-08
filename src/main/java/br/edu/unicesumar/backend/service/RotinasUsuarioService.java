package br.edu.unicesumar.backend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.edu.unicesumar.backend.domain.AtividadeHorario;
import br.edu.unicesumar.backend.domain.Lugar;
import br.edu.unicesumar.backend.domain.Roteiro;
import br.edu.unicesumar.backend.domain.Usuario;
import br.edu.unicesumar.backend.dto.sign.EntrarSairAtividadeHorario;
import br.edu.unicesumar.backend.dto.sign.EntrarSairRoteiro;
import br.edu.unicesumar.backend.dto.sign.IndicarDesindicarLugar;
import br.edu.unicesumar.backend.repository.AtividadeHorarioRepository;
import br.edu.unicesumar.backend.repository.LugarRepository;
import br.edu.unicesumar.backend.repository.RoteiroRepository;
import br.edu.unicesumar.backend.repository.UsuarioRepository;

@Service
public class RotinasUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RoteiroRepository roteiroRepository;

    @Autowired
    private AtividadeHorarioRepository atividadeHorarioRepository;

    @Autowired
    private LugarRepository lugarRepository;

    private Long viraLugarOficial = 2L;

    public void entrarEmRoteiro(EntrarSairRoteiro entrarSairRoteiro, Usuario usuario) {

        Optional<Usuario> usuarioDoBanco = usuarioRepository.findById(usuario.getUsuarioId());

        verificaViajante(usuarioDoBanco);

        Optional<Roteiro> roteiroDoBanco = roteiroRepository.findById(entrarSairRoteiro.getRoteiroId());

        existRoteiro(roteiroDoBanco);

        if (roteiroDoBanco.get().getQuantidadePessoasAtual() == roteiroDoBanco.get().getQuantidadePessoasMax()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Roteiro já está lotado");
        }

        if (usuarioRepository.existeViajanteRoteiro(usuario.getViajante().getViajanteId(),
                entrarSairRoteiro.getRoteiroId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Viajante já está participando do Roteiro");
        }

        usuarioDoBanco.get().getViajante().getViajanteRoteiros().add(roteiroDoBanco.get());

        roteiroDoBanco.get().setQuantidadePessoasAtual(roteiroDoBanco.get().getQuantidadePessoasAtual() + 1);

        usuarioRepository.save(usuarioDoBanco.get());
    }

    public void sairDeRoteiro(EntrarSairRoteiro entrarSairRoteiro, Usuario usuario) {

        Optional<Usuario> usuarioDoBanco = usuarioRepository.findById(usuario.getUsuarioId());

        verificaViajante(usuarioDoBanco);

        Optional<Roteiro> roteiroDoBanco = roteiroRepository.findById(entrarSairRoteiro.getRoteiroId());

        existRoteiro(roteiroDoBanco);

        if (usuarioRepository.existeViajanteRoteiro(usuario.getViajante().getViajanteId(),
                entrarSairRoteiro.getRoteiroId())) {
            for (int i = 0; i < usuarioDoBanco.get().getViajante().getViajanteRoteiros().size(); i++) {
                if (usuarioDoBanco.get().getViajante().getViajanteRoteiros().get(i).getRoteiroId() == entrarSairRoteiro
                        .getRoteiroId()) {
                    usuarioDoBanco.get().getViajante().getViajanteRoteiros().remove(i);
                }
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Viajante não está participando do Roteiro");
        }

        roteiroDoBanco.get().setQuantidadePessoasAtual(roteiroDoBanco.get().getQuantidadePessoasAtual() - 1);

        usuarioRepository.save(usuarioDoBanco.get());
    }

    public void entrarEmAtividadeHorario(EntrarSairAtividadeHorario entrarSairAtividadeHorario, Usuario usuario) {

        Optional<Usuario> usuarioDoBanco = usuarioRepository.findById(usuario.getUsuarioId());

        verificaViajante(usuarioDoBanco);

        Optional<AtividadeHorario> atividadeHorarioDoBanco = atividadeHorarioRepository
                .findById(entrarSairAtividadeHorario.getAtividadeHorarioId());

        existAtividadeHorario(atividadeHorarioDoBanco);

        if (atividadeHorarioDoBanco.get().getQuantPessoasAtual() == atividadeHorarioDoBanco.get()
                .getQuantPessoasMax()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Horário da Atividade já está lotado");
        }

        if (usuarioRepository.existeViajanteAtividadeHorario(usuario.getViajante().getViajanteId(),
                entrarSairAtividadeHorario.getAtividadeHorarioId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Viajante já está participando do Horário da Atividade");
        }

        usuarioDoBanco.get().getViajante().getViajanteAtividadeHorarios().add(atividadeHorarioDoBanco.get());

        atividadeHorarioDoBanco.get().setQuantPessoasAtual(atividadeHorarioDoBanco.get().getQuantPessoasAtual() + 1);

        usuarioRepository.save(usuarioDoBanco.get());
    }

    public void sairDeAtividadeHorario(EntrarSairAtividadeHorario entrarSairAtividadeHorario, Usuario usuario) {

        Optional<Usuario> usuarioDoBanco = usuarioRepository.findById(usuario.getUsuarioId());

        verificaViajante(usuarioDoBanco);

        Optional<AtividadeHorario> atividadeHorarioDoBanco = atividadeHorarioRepository
                .findById(entrarSairAtividadeHorario.getAtividadeHorarioId());

        existAtividadeHorario(atividadeHorarioDoBanco);

        if (usuarioRepository.existeViajanteAtividadeHorario(usuario.getViajante().getViajanteId(),
                entrarSairAtividadeHorario.getAtividadeHorarioId())) {
            for (int i = 0; i < usuarioDoBanco.get().getViajante().getViajanteAtividadeHorarios().size(); i++) {
                if (usuarioDoBanco.get().getViajante().getViajanteAtividadeHorarios().get(i)
                        .getAtividadeHorarioId() == entrarSairAtividadeHorario
                                .getAtividadeHorarioId()) {
                    usuarioDoBanco.get().getViajante().getViajanteAtividadeHorarios().remove(i);
                }
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Viajante não está participando do Horário da Atividade");
        }

        atividadeHorarioDoBanco.get().setQuantPessoasAtual(atividadeHorarioDoBanco.get().getQuantPessoasAtual() - 1);

        usuarioRepository.save(usuarioDoBanco.get());
    }

    public void indicarLugar(IndicarDesindicarLugar indicarDesindicarLugar, Usuario usuario) {

        Optional<Lugar> lugarDoBanco = lugarRepository.findById(indicarDesindicarLugar.getLugarId());

        existLugar(lugarDoBanco);

        Optional<Usuario> usuarioDoBanco = usuarioRepository.findById(usuario.getUsuarioId());

        verificaViajante(usuarioDoBanco);

        if (usuarioRepository.existeViajanteLugar(usuario.getViajante().getViajanteId(),
                indicarDesindicarLugar.getLugarId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Usuário já indicou esse lugar");
        }

        usuarioDoBanco.get().getViajante().getLugaresIndicados().add(lugarDoBanco.get());

        lugarDoBanco.get().setIndicacoes(lugarDoBanco.get().getIndicacoes() + 1);

        if (lugarDoBanco.get().getLugarIndicado()) {
            if (lugarDoBanco.get().getIndicacoes() > viraLugarOficial) {
                lugarDoBanco.get().setLugarIndicado(false);
            }
        }

        usuarioRepository.save(usuarioDoBanco.get());

    }

    public void desindicarLugar(IndicarDesindicarLugar indicarDesindicarLugar, Usuario usuario) {

        Optional<Lugar> lugarDoBanco = lugarRepository.findById(indicarDesindicarLugar.getLugarId());

        existLugar(lugarDoBanco);

        Optional<Usuario> usuarioDoBanco = usuarioRepository.findById(usuario.getUsuarioId());

        verificaViajante(usuarioDoBanco);

        if (usuarioRepository.existeViajanteLugar(usuario.getViajante().getViajanteId(),
                indicarDesindicarLugar.getLugarId())) {
                    for (int i = 0; i < usuarioDoBanco.get().getViajante().getLugaresIndicados().size(); i++) {
                        if (usuarioDoBanco.get().getViajante().getLugaresIndicados().get(i)
                                .getLugarId() == indicarDesindicarLugar
                                        .getLugarId()) {
                            usuarioDoBanco.get().getViajante().getLugaresIndicados().remove(i);
                        }
                    }
        } else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Usuário não indicou esse lugar");
        }

        lugarDoBanco.get().setIndicacoes(lugarDoBanco.get().getIndicacoes() - 1);

        usuarioRepository.save(usuarioDoBanco.get());

    }

    private void existLugar(Optional<Lugar> lugarDoBanco) {
        if (!lugarDoBanco.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Lugar Id não encontrado");
        }
    }

    private void verificaViajante(Optional<Usuario> usuarioDoBanco) {
        if (usuarioDoBanco.get().getViajante() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Somente Usuários do tipo Viajante podem realizar essa ação");
        }
    }

    private void existRoteiro(Optional<Roteiro> roteiroDoBanco) {
        if (!roteiroDoBanco.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Roteiro Id não encontrado");
        }
    }

    private void existAtividadeHorario(Optional<AtividadeHorario> atividadeHorarioDoBanco) {
        if (!atividadeHorarioDoBanco.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Atividade Horario Id não encontrado");
        }
    }
}
