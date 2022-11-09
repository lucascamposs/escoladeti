package br.edu.unicesumar.backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.edu.unicesumar.backend.domain.Atividade;
import br.edu.unicesumar.backend.domain.AtividadeCategoria;
import br.edu.unicesumar.backend.domain.AtividadeDia;
import br.edu.unicesumar.backend.domain.AtividadeHorario;
import br.edu.unicesumar.backend.domain.Lugar;
import br.edu.unicesumar.backend.domain.Usuario;
import br.edu.unicesumar.backend.dto.sign.SignUpAtividade;
import br.edu.unicesumar.backend.dto.sign.SignUpAtividadeDia;
import br.edu.unicesumar.backend.dto.sign.UpdateAtividade;
import br.edu.unicesumar.backend.dto.sign.UpdateAtividadeDia;
import br.edu.unicesumar.backend.repository.AtividadeCategoriaRepository;
import br.edu.unicesumar.backend.repository.AtividadeDiaRepository;
import br.edu.unicesumar.backend.repository.AtividadeRepository;
import br.edu.unicesumar.backend.repository.LugarRepository;

@Service
public class AtividadeService {

    @Autowired
    private AtividadeRepository atividadeRepository;

    @Autowired
    private AtividadeCategoriaRepository atividadeCategoriaRepository;

    @Autowired
    private AtividadeDiaRepository atividadeDiaRepository;

    @Autowired
    private LugarRepository lugarRepository;

    public List<Atividade> getCompanyAtividades(Long id) {
        return atividadeRepository.findAtividadesUsingAgenciaId(id);
    }

    public List<Atividade> getAtividadesPorCategoria(Long id) {
        return atividadeRepository.findAtividadesUsingCategoriaId(id);
    }

    public Optional<Atividade> getAtividadeById(Long id) {
        return atividadeRepository.findById(id);
    }

    public Atividade addAtividade(SignUpAtividade signUpAtividade, Usuario userLogado) {

        Optional<AtividadeCategoria> atividadeCategoriaAux = atividadeCategoriaRepository
                .findById(signUpAtividade.getAtividadeCategoriaId());

        if (signUpAtividade.getAtividadeCategoriaId() != null && !atividadeCategoriaAux.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Atividade Categoria Id não encontrado");
        }

        Lugar lugarAux = null;

        if (signUpAtividade.getLugarId() != null) {

            Optional<Lugar> lugarOpt = lugarRepository.findById(signUpAtividade.getLugarId());

            if (signUpAtividade.getLugarId() != null && !lugarOpt.isPresent()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lugar Id não encontrado");
            }

            lugarAux = lugarOpt.get();
        }

        Atividade atividade = Atividade.builder()
                .nomeAtividade(signUpAtividade.getNomeAtividade())
                .descricaoAtividade(signUpAtividade.getDescricaoAtividade())
                .ativo(true)
                .lugar(lugarAux)
                .atividadeCategoria(atividadeCategoriaAux.get())
                .atividadeFotos(signUpAtividade.getAtividadeFotos())
                .agencia(userLogado.getAgencia()).build();
        return atividadeRepository.save(atividade);
    }

    public Optional<Atividade> updateAtividade(UpdateAtividade updateAtividade, Usuario usuario) {

        Optional<Atividade> atividadeDoBanco = atividadeRepository.findById(updateAtividade.getAtividadeId());

        existAtividade(atividadeDoBanco);
        verificaSeAgenciaDonaDaAtividade(usuario, atividadeDoBanco);

        atividadeDoBanco.get().setNomeAtividade(updateAtividade.getNomeAtividade());
        atividadeDoBanco.get().setDescricaoAtividade(updateAtividade.getDescricaoAtividade());
        atividadeDoBanco.get().setAtivo(updateAtividade.getAtivo());

        if (updateAtividade.getAtividadeFotos() != null) {

            atividadeDoBanco.get().getAtividadeFotos().clear();

            for (int i = 0; i < updateAtividade.getAtividadeFotos().size(); i++) {
                atividadeDoBanco.get().getAtividadeFotos().add(updateAtividade.getAtividadeFotos().get(i));
            }
        }

        Optional<AtividadeCategoria> atividadeCategoriaNova = atividadeCategoriaRepository
                .findById(updateAtividade.getAtividadeCategoriaId());

        if (!atividadeCategoriaNova.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Atividade Categoria Id não encontrado!");
        }
        atividadeDoBanco.get().setAtividadeCategoria(atividadeCategoriaNova.get());

        if (updateAtividade.getLugarId() != null) {

            Optional<Lugar> lugarNovo = lugarRepository.findById(updateAtividade.getLugarId());

            if (!lugarNovo.isPresent()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lugar Id não encontrado");
            }
            atividadeDoBanco.get().setLugar(lugarNovo.get());

        }

        atividadeRepository.save(atividadeDoBanco.get());
        return Optional.of(atividadeDoBanco.get());

    }

    public void deleteAtividadeById(Long id) {

        Optional<Atividade> atividadeDoBanco = atividadeRepository.findById(id);

        if (!atividadeDoBanco.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Atividade Id não encontrado!");
        } else {
            atividadeRepository.deleteById(id);
        }

    }

    private void existAtividade(Optional<Atividade> atividadeDoBanco) {
        if (!atividadeDoBanco.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Atividade Id não encontrado!");
        }
    }

    private void verificaSeAgenciaDonaDaAtividade(Usuario usuario, Optional<Atividade> atividadeDoBanco) {
        if (usuario.getAgencia().getAgenciaId() != atividadeDoBanco.get().getAgencia().getAgenciaId()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Atividade ID não pertence ao Usuário logado!");

        }
    }

    // ---------------------- ATIVIDADE DIA E HORÁRIO ----------------------------

    public List<AtividadeDia> getAtividadeDiasByAtividadeId(Long id) {
        return atividadeDiaRepository.findAtividadeDiasUsingAtividadeId(id);
    }

    public AtividadeDia addAtividadeDia(SignUpAtividadeDia signUpAtividadeDia, Usuario usuario) {

        if (signUpAtividadeDia.getAtividadeId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Atividade Id não pode ser nulo!");
        }

        Optional<Atividade> atividadeDoBanco = atividadeRepository.findById(signUpAtividadeDia.getAtividadeId());

        existAtividade(atividadeDoBanco);
        verificaSeAgenciaDonaDaAtividade(usuario, atividadeDoBanco);
        if (atividadeDiaRepository.diaDisponivelCadastradoByAgenciaId(usuario.getAgencia().getAgenciaId(), signUpAtividadeDia.getDiaDisponivel())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe uma atividade cadastrada nesse dia!");
        }

        List<AtividadeHorario> atividadeHorariosAux = new ArrayList<>();

        for (int i = 0; i < signUpAtividadeDia.getAtividadeHorarios().size(); i++) {

            AtividadeHorario atividadeHorarioAux = AtividadeHorario.builder()
                    .ativo(true)
                    .quantPessoasAtual(0)
                    .quantPessoasMax(signUpAtividadeDia.getAtividadeHorarios().get(i).getQuantPessoasMax())
                    .horario_inicio(signUpAtividadeDia.getAtividadeHorarios().get(i).getHorario_inicio())
                    .horario_final(signUpAtividadeDia.getAtividadeHorarios().get(i).getHorario_final())
                    .preco(signUpAtividadeDia.getAtividadeHorarios().get(i).getPreco()).build();

            atividadeHorariosAux.add(atividadeHorarioAux);
        }

        AtividadeDia atividadeDia = AtividadeDia.builder()
                .diaDisponivel(signUpAtividadeDia.getDiaDisponivel())
                .ativo(true)
                .atividade(atividadeDoBanco.get())
                .atividadeHorarios(atividadeHorariosAux).build();

        return atividadeDiaRepository.save(atividadeDia);

    }

    @SuppressWarnings("null")
    public Optional<AtividadeDia> updateAtividadeDia(UpdateAtividadeDia updateAtividadeDia, Usuario usuario) {

        Optional<AtividadeDia> atividadeDiaDoBanco = atividadeDiaRepository
                .findById(updateAtividadeDia.getAtividadeDiaId());

        if (!atividadeDiaDoBanco.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Atividade Dia Id não encontrado!");
        }

        Optional<Atividade> atividadeDoBanco = atividadeRepository
                .findById(atividadeDiaDoBanco.get().getAtividade().getAtividadeId());

        verificaSeAgenciaDonaDaAtividade(usuario, atividadeDoBanco);

        atividadeDiaDoBanco.get().setDiaDisponivel(updateAtividadeDia.getDiaDisponivel());
        atividadeDiaDoBanco.get().setAtivo(updateAtividadeDia.getAtivo());

        Optional<Atividade> atividadeNova = atividadeRepository.findById(updateAtividadeDia.getAtividadeId());

        if (!atividadeNova.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Atividade Id não encontrado!");
        }

        atividadeDiaDoBanco.get().setAtividade(atividadeNova.get());

        if (updateAtividadeDia.getAtividadeHorarios() != null) {

            atividadeDiaDoBanco.get().getAtividadeHorarios().clear();

            for (int i = 0; i < updateAtividadeDia.getAtividadeHorarios().size(); i++) {

                AtividadeHorario atividadeHorarioAux = new AtividadeHorario();

                atividadeHorarioAux.setAtividadeHorarioId(
                        updateAtividadeDia.getAtividadeHorarios().get(i).getAtividadeHorarioId());
                atividadeHorarioAux.setAtivo(updateAtividadeDia.getAtividadeHorarios().get(i).getAtivo());
                atividadeHorarioAux
                        .setQuantPessoasMax(updateAtividadeDia.getAtividadeHorarios().get(i).getQuantPessoasMax());
                atividadeHorarioAux
                        .setHorario_inicio(updateAtividadeDia.getAtividadeHorarios().get(i).getHorario_inicio());
                atividadeHorarioAux
                        .setHorario_final(updateAtividadeDia.getAtividadeHorarios().get(i).getHorario_final());
                atividadeHorarioAux.setPreco(updateAtividadeDia.getAtividadeHorarios().get(i).getPreco());

                atividadeDiaDoBanco.get().getAtividadeHorarios().add(atividadeHorarioAux);
            }

        }

        atividadeDiaRepository.save(atividadeDiaDoBanco.get());
        return Optional.of(atividadeDiaDoBanco.get());
    }

    public void deleteAtividadeDiaById(Long id) {

        Optional<AtividadeDia> atividadeDiaDoBanco = atividadeDiaRepository
                .findById(id);

        if (!atividadeDiaDoBanco.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Atividade Dia Id não encontrado!");
        }

        atividadeDiaRepository.deleteById(id);
    }
}
