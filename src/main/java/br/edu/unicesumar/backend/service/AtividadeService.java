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
	
	public List<Atividade> getCompanyAtividades(Long id){
		return atividadeRepository.findAtividadesUsingAgenciaId(id);
	}
	
	public List<Atividade> getAtividadesPorCategoria(Long id){
		return atividadeRepository.findAtividadesUsingCategoriaId(id);
	}
	
	public Optional<Atividade> getAtividadeById(Long id){
		return atividadeRepository.findById(id);
	}
	
	public Atividade addAtividade(SignUpAtividade signUpAtividade, Usuario userLogado) {
		
		Optional<Lugar> lugarOpt = lugarRepository.findById(signUpAtividade.getLugarId());
		
		Optional<AtividadeCategoria> atividadeCategoriaOpt = atividadeCategoriaRepository.findById(signUpAtividade.getAtividadeCategoriaId());
		
		if (lugarOpt.isPresent() && atividadeCategoriaOpt.isPresent()) {
			Atividade atividade = Atividade.builder()
					.nomeAtividade(signUpAtividade.getNomeAtividade())
					.descricaoAtividade(signUpAtividade.getDescricaoAtividade())
					.ativo(true)
					.lugar(lugarOpt.get())
					.atividadeCategoria(atividadeCategoriaOpt.get())
					.atividadeFotos(signUpAtividade.getAtividadeFotos())
					.agencia(userLogado.getAgencia()).build();
			
			return atividadeRepository.save(atividade);
		}	
		
		if (lugarOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lugar Id não encontrado");
        }
		
		if (atividadeCategoriaOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Atividade Categoria Id não encontrado");
        }
		
		return null;
	}
	
	public Optional<Atividade> updateAtividade(Atividade atividadeExistente) {
		if (atividadeRepository.existsById(atividadeExistente.getAtividadeId())) {
			Atividade atividadeAtualizada = atividadeRepository.save(atividadeExistente);
			return Optional.of(atividadeAtualizada);
		}
		return Optional.empty();
	}
	
	public void deleteAtividadeById(Long id) {
		if (atividadeRepository.existsById(id)) {
			atividadeRepository.deleteById(id);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Atividade Id não encontrado!");
		}

	}
	
	//Atividade Dia e Horário
	public List<AtividadeDia> getAtividadeDiasByAtividadeId(Long id){
		return atividadeDiaRepository.findAtividadeDiasUsingAtividadeId(id);
	}
	
	public AtividadeDia addAtividadeDia(SignUpAtividadeDia signUpAtividadeDia) {
		
		List<AtividadeHorario> atividadeHorariosAux = new ArrayList<>();
		
		Optional<Atividade> atividadeAux = atividadeRepository.findById(signUpAtividadeDia.getAtividadeId());
		
		if (atividadeAux.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Atividade Id não encontrado");
		} else {
			for (int i = 0; i < signUpAtividadeDia.getAtividadeHorarios().size(); i++) {
				AtividadeHorario atividadeHorarioAux = AtividadeHorario.builder()
						.ativo(true)
						.quantPessoasMax(signUpAtividadeDia.getAtividadeHorarios().get(i).getQuantPessoasMax())
						.horario_inicio(signUpAtividadeDia.getAtividadeHorarios().get(i).getHorario_inicio())
						.horario_final(signUpAtividadeDia.getAtividadeHorarios().get(i).getHorario_final())
						.preco(signUpAtividadeDia.getAtividadeHorarios().get(i).getPreco()).build();
				
				atividadeHorariosAux.add(atividadeHorarioAux);
			}
			
			
			
			AtividadeDia atividadeDia = AtividadeDia.builder()
					.diaDisponivel(signUpAtividadeDia.getDiaDisponivel())
					.ativo(true)
					.atividade(atividadeAux.get())
					.atividadeHorarios(atividadeHorariosAux).build();
			
			return atividadeDiaRepository.save(atividadeDia);
		}
	}
	
}
