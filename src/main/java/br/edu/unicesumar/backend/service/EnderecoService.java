package br.edu.unicesumar.backend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.edu.unicesumar.backend.domain.Endereco;
import br.edu.unicesumar.backend.domain.Usuario;
import br.edu.unicesumar.backend.dto.sign.UpdateEndereco;
import br.edu.unicesumar.backend.repository.EnderecoRepository;
import br.edu.unicesumar.backend.repository.UsuarioRepository;

@Service
public class EnderecoService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    public Endereco buscarEnderecoPeloUsuarioLogado(Usuario usuario) {
        return enderecoRepository.findById(usuario.getEndereco().getEnderecoId()).orElse(null);
    }

    @SuppressWarnings("null")
    public Endereco updateEndereco(UpdateEndereco updateEndereco, Usuario usuario) {

        Optional<Usuario> usuarioDoBanco = usuarioRepository.findById(usuario.getUsuarioId());

        if (updateEndereco.getEnderecoId() != usuarioDoBanco.get().getEndereco().getEnderecoId()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Endereço ID não pertence ao Usuário logado!");
        }

        usuarioDoBanco.get().getEndereco().setCep(updateEndereco.getCep());
        usuarioDoBanco.get().getEndereco().setEstado(updateEndereco.getEstado());
        usuarioDoBanco.get().getEndereco().setCidade(updateEndereco.getCidade());
        usuarioDoBanco.get().getEndereco().setBairro(updateEndereco.getBairro());
        usuarioDoBanco.get().getEndereco().setLogradouro(updateEndereco.getLogradouro());
        usuarioDoBanco.get().getEndereco().setNumero(updateEndereco.getNumero());

        usuarioRepository.save(usuarioDoBanco.get());
        return buscarEnderecoPeloUsuarioLogado(usuario);

    }
}
