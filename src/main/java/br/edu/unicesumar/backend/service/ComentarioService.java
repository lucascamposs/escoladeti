package br.edu.unicesumar.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.edu.unicesumar.backend.domain.Atividade;
import br.edu.unicesumar.backend.domain.Comentario;
import br.edu.unicesumar.backend.domain.Lugar;
import br.edu.unicesumar.backend.domain.Roteiro;
import br.edu.unicesumar.backend.domain.Usuario;
import br.edu.unicesumar.backend.dto.sign.SignUpComentario;
import br.edu.unicesumar.backend.repository.AtividadeRepository;
import br.edu.unicesumar.backend.repository.ComentarioRepository;
import br.edu.unicesumar.backend.repository.LugarRepository;
import br.edu.unicesumar.backend.repository.RoteiroRepository;

@Service
public class ComentarioService {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private AtividadeRepository atividadeRepository;

    @Autowired
    private LugarRepository lugarRepository;

    @Autowired
    private RoteiroRepository roteiroRepository;

    // Comentario Atividade

    public List<Comentario> getComentariosAtividade(Long id) {
        return comentarioRepository.comentariosByAtividadeId(id);
    }

    public Comentario addComentarioAtividade(SignUpComentario signUpComentario, Usuario usuario) {

        verificaSeViajante(usuario);

        if (signUpComentario.getAtividadeId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Atividade Id não pode ser nulo para essa ação");
        }

        Optional<Atividade> atividadeDoBanco = atividadeRepository.findById(signUpComentario.getAtividadeId());

        Comentario comentario = Comentario.builder()
                .avaliacao(signUpComentario.getAvaliacao())
                .descricao(signUpComentario.getDescricao())
                .comentarioFotos(signUpComentario.getComentarioFotos())
                .viajante(usuario.getViajante())
                .atividade(atividadeDoBanco.get()).build();

        return comentarioRepository.save(comentario);
    }

    // Comentario Lugar

    public List<Comentario> getComentariosLugar(Long id) {
        return comentarioRepository.comentariosByLugarId(id);
    }

    public Comentario addComentarioLugar(SignUpComentario signUpComentario, Usuario usuario) {

        verificaSeViajante(usuario);

        if (signUpComentario.getLugarId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Lugar Id não pode ser nulo para essa ação");
        }

        Optional<Lugar> lugarDoBanco = lugarRepository.findById(signUpComentario.getLugarId());

        Comentario comentario = Comentario.builder()
                .avaliacao(signUpComentario.getAvaliacao())
                .descricao(signUpComentario.getDescricao())
                .comentarioFotos(signUpComentario.getComentarioFotos())
                .viajante(usuario.getViajante())
                .lugar(lugarDoBanco.get()).build();

        return comentarioRepository.save(comentario);
    }

    // Comentario Roteiro

    public List<Comentario> getComentariosRoteiro(Long id) {
        return comentarioRepository.comentariosByRoteiroId(id);
    }

    public Comentario addComentarioRoteiro(SignUpComentario signUpComentario, Usuario usuario) {

        verificaSeViajante(usuario);

        if (signUpComentario.getRoteiroId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Roteiro Id não pode ser nulo para essa ação");
        }

        Optional<Roteiro> roteiroDoBanco = roteiroRepository.findById(signUpComentario.getRoteiroId());

        Comentario comentario = Comentario.builder()
                .avaliacao(signUpComentario.getAvaliacao())
                .descricao(signUpComentario.getDescricao())
                .comentarioFotos(signUpComentario.getComentarioFotos())
                .viajante(usuario.getViajante())
                .roteiro(roteiroDoBanco.get()).build();

        return comentarioRepository.save(comentario);
    }
    // Geral

    public void deleteComentarioById(Long id) {

        Optional<Comentario> comentarioDoBanco = comentarioRepository
                .findById(id);

        if (!comentarioDoBanco.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comentario Id não encontrado!");
        }

        comentarioRepository.deleteById(id);
    }

    private void verificaSeViajante(Usuario usuario) {
        if (usuario.getViajante() == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Somente usuários Viajantes podem realizar essa ação");
        }
    }

    // Comentario Resposta

}
