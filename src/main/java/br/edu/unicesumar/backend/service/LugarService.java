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
import br.edu.unicesumar.backend.dto.sign.UpdateLugar;
import br.edu.unicesumar.backend.repository.LugarCategoriaRepository;
import br.edu.unicesumar.backend.repository.LugarRepository;

@Service
public class LugarService {

    @Autowired
    private LugarRepository lugarRepository;

    @Autowired
    private LugarCategoriaRepository lugarCategoriaRepository;

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

        Optional<LugarCategoria> lugarCategoriaAux = lugarCategoriaRepository
                .findById(signUpLugar.getLugarCategoriaId());

        if (signUpLugar.getLugarCategoriaId() != null && !lugarCategoriaAux.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria Id não encontrada!");
        }

        Lugar lugar = Lugar.builder()
                .lugarIndicado(true)
                .nomeLugar(signUpLugar.getNomeLugar())
                .descricao(signUpLugar.getDescricao())
                .coordenada(signUpLugar.getCoordenada())
                .ativo(true)
                .usuarioQueCriou(usuario)
                .lugarCategoria(lugarCategoriaAux.get())
                .lugarFotos(signUpLugar.getLugarFotos()).build();

        return lugarRepository.save(lugar);

    }

    public Optional<Lugar> updateLugar(UpdateLugar updateLugar, Usuario usuario) {

        Optional<Lugar> lugarDoBanco = lugarRepository.findById(updateLugar.getLugarId());

        existLugar(lugarDoBanco);
        verificaSeAgenciaDonaDoLugar(usuario, lugarDoBanco);

        lugarDoBanco.get().setNomeLugar(updateLugar.getNomeLugar());
        lugarDoBanco.get().setDescricao(updateLugar.getDescricao());
        lugarDoBanco.get().setCoordenada(updateLugar.getCoordenada());

        if (updateLugar.getLugarFotos() != null) {

            lugarDoBanco.get().getLugarFotos().clear();

            for (int i = 0; i < updateLugar.getLugarFotos().size(); i++) {
                lugarDoBanco.get().getLugarFotos().add(updateLugar.getLugarFotos().get(i));
            }
        }

        Optional<LugarCategoria> lugarCategoriaNovo = lugarCategoriaRepository
                .findById(updateLugar.getLugarCategoriaId());

        if (!lugarCategoriaNovo.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lugar Categoria Id não encontrado!");
        }

        lugarDoBanco.get().setLugarCategoria(lugarCategoriaNovo.get());

        lugarRepository.save(lugarDoBanco.get());
        return Optional.of(lugarDoBanco.get());

    }

    public void deleteLugarById(Long id) {

        Optional<Lugar> lugarDoBanco = lugarRepository.findById(id);

        if (!lugarDoBanco.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lugar Id não encontrado!");
        } else {
            lugarRepository.deleteById(id);
        }
    }

    private void verificaSeAgenciaDonaDoLugar(Usuario usuario, Optional<Lugar> lugarDoBanco) {
        if (usuario.getAgencia().getAgenciaId() != lugarDoBanco.get().getUsuarioQueCriou().getAgencia()
                .getAgenciaId()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Usuário inválido!");
        }
    }

    private void existLugar(Optional<Lugar> lugarDoBanco) {
        if (!lugarDoBanco.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lugar Id não encontrado!");
        }
    }

}
