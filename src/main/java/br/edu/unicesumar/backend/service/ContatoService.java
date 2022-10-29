package br.edu.unicesumar.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.edu.unicesumar.backend.domain.Contato;
import br.edu.unicesumar.backend.domain.Usuario;
import br.edu.unicesumar.backend.domain.enums.TipoUsuario;
import br.edu.unicesumar.backend.dto.sign.SignUpContato;
import br.edu.unicesumar.backend.dto.sign.UpdateContato;
import br.edu.unicesumar.backend.repository.ContatoRepository;
import br.edu.unicesumar.backend.repository.UsuarioRepository;

@Service
public class ContatoService {

    @Autowired
    private ContatoRepository contatoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Contato> buscarContatosDoUsuarioLogado(Usuario usuario) {
        return contatoRepository.findContatosUsingUsuarioUUID(usuario.getUsuarioId());
    }

    public void deleteContatoPorId(Long idContato, Usuario usuario) {

        if (!contatoRepository.existsById(idContato)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID de contato não encontrado!");
        }

        if (!contatoRepository.pertenceAoUsuario(usuario.getUsuarioId(), idContato)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Contato Id não pertence ao Usuario Logado");
        }

        Optional<Usuario> usuarioDoBanco = usuarioRepository.findById(usuario.getUsuarioId());

        if (usuarioDoBanco.get().getContato().size() == 1) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,
                    "Usuário deve ter pelo menos um contato cadastrado, adicione mais um contato e tente novamente");
        }

        for (int i = 0; i < usuarioDoBanco.get().getContato().size(); i++) {
            if (usuarioDoBanco.get().getContato().get(i).getContatoId() == idContato) {
                usuarioDoBanco.get().getContato().remove(i);
            }
        }

        usuarioRepository.save(usuarioDoBanco.get());
        // contatoRepository.deleteById(idContato);

    }

    @SuppressWarnings("null")
    public Optional<List<Contato>> updateContatos(List<UpdateContato> listUpdateContato, Usuario usuario) {

        if (usuario.getTipoUsuario() == TipoUsuario.VIAJANTE && listUpdateContato.size() > 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Usuario Viajante não pode ter mais de um contato");
        }

        Optional<Usuario> usuarioDoBanco = usuarioRepository.findById(usuario.getUsuarioId());

        existUsuario(usuarioDoBanco);

        // usuarioDoBanco.get().getContato().clear();

        for (int i = 0; i < usuarioDoBanco.get().getContato().size(); i++) {

            if (usuarioDoBanco.get().getContato().get(i).getContatoId() != listUpdateContato.get(i).getContatoId()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Essa função é somente para alterar os já existentes, não para adicionar nem deletar");
            }
            usuarioDoBanco.get().getContato().get(i).setNumero(listUpdateContato.get(i).getNumero());
            usuarioDoBanco.get().getContato().get(i).setTipoContato(listUpdateContato.get(i).getTipoContato());
        }

        usuarioRepository.save(usuarioDoBanco.get());

        return Optional.of(this.buscarContatosDoUsuarioLogado(usuarioDoBanco.get()));

    }

    public List<Contato> adicionarContato(SignUpContato signUpContato, Usuario usuario) {

        Optional<Usuario> usuarioDoBanco = usuarioRepository.findById(usuario.getUsuarioId());

        existUsuario(usuarioDoBanco);

        Contato novoContato = Contato.builder()
                .tipoContato(signUpContato.getTipoContato())
                .numero(signUpContato.getNumero()).build();

        usuarioDoBanco.get().getContato().add(novoContato);

        usuarioRepository.save(usuarioDoBanco.get());

        return this.buscarContatosDoUsuarioLogado(usuario);

    }

    private void existUsuario(Optional<Usuario> usuarioDoBanco) {
        if (!usuarioDoBanco.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não encontrado!");
        }
    }

}
