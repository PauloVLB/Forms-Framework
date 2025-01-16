package br.ufrn.FormsFramework.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.FormsFramework.exception.EntityNotFoundException;
import br.ufrn.FormsFramework.exception.OpcaoMarcadaAlreadyInRespostaException;
import br.ufrn.FormsFramework.exception.RespostaAndOpcaoIncompatibleException;
import br.ufrn.FormsFramework.exception.RespostaFullOfOpcaoException;
import br.ufrn.FormsFramework.model.Opcao;
import br.ufrn.FormsFramework.model.Resposta;
import br.ufrn.FormsFramework.repository.RespostaRepository;
import jakarta.transaction.Transactional;

@Service
public class RespostaService {
    
    @Autowired
    private RespostaRepository respostaRepository;

    @Autowired
    private OpcaoService opcaoService;

    @Transactional
    public Resposta create(Resposta resposta) {
        return respostaRepository.save(resposta);
    }

    public List<Resposta> getAll() {
        return respostaRepository.findAll();
    }

    public Resposta getById(Long id) {
        return respostaRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(id, Resposta.class)
            );
    }

    @Transactional
    public Resposta update(Long id, Resposta resposta) {
        Resposta respostaExistente = this.getById(id);
        
        respostaExistente.setConteudo(resposta.getConteudo());
        
        return respostaRepository.save(respostaExistente);
    }

    @Transactional
    public void delete(Long id) {
        this.getById(id);
        respostaRepository.deleteById(id);
    }

    @Transactional
    public void deleteAll() {
        respostaRepository.deleteAll();
    }

    @Transactional
    public Opcao addOpcaoMarcada(Long idResposta, Long idOpcao) {
        Resposta resposta = this.getById(idResposta);
        Opcao opcao = opcaoService.getById(idOpcao);

        if(resposta.getQuesito() != opcao.getQuesito()){
            throw new RespostaAndOpcaoIncompatibleException(idResposta, idOpcao);
        }
        if(resposta.getQuesito().getTipoResposta() == "OBJETIVA_SIMPLES" && resposta.getOpcoesMarcadas().isEmpty()){
            resposta.getOpcoesMarcadas().add(opcao);
        } else if(resposta.getQuesito().getTipoResposta() == "OBJETIVA_MULTIPLA"){
            if(resposta.getOpcoesMarcadas().contains(opcao)){
                throw new OpcaoMarcadaAlreadyInRespostaException(idResposta, idOpcao);
            }else{
                resposta.getOpcoesMarcadas().add(opcao);
            }
        } else{
            throw new RespostaFullOfOpcaoException(idResposta);
        }

        respostaRepository.save(resposta);

        return resposta.getOpcoesMarcadas().get(resposta.getOpcoesMarcadas().size() - 1);
    }

    @Transactional
    public List<Opcao> setOpcoesMarcadas(Long idResposta, List<Long> idsOpcoes) {
        Resposta resposta = this.getById(idResposta);
        List<Opcao> opcoes = opcaoService.getAllByIds(idsOpcoes);

        if(resposta.getQuesito().getTipoResposta() != "OBJETIVA_SIMPLES" && 
            resposta.getQuesito().getTipoResposta() != "OBJETIVA_MULTIPLA"){
            throw new RespostaFullOfOpcaoException(idResposta);
        } 

        resposta.setOpcoesMarcadas(opcoes);

        respostaRepository.save(resposta);

        return resposta.getOpcoesMarcadas();
    }

}
