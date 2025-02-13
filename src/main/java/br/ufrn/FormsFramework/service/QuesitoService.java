package br.ufrn.FormsFramework.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import br.ufrn.FormsFramework.exception.EntityNotFoundException;
import br.ufrn.FormsFramework.exception.OpcaoHabilitadoraAlreadyInQuesitoException;
import br.ufrn.FormsFramework.model.Opcao;
import br.ufrn.FormsFramework.model.Quesito;
import br.ufrn.FormsFramework.model.Resposta;
import br.ufrn.FormsFramework.model.Secao;
import br.ufrn.FormsFramework.repository.QuesitoRepository;
import br.ufrn.FormsFramework.utils.Pair;
import jakarta.transaction.Transactional;

@Service
public class QuesitoService {

    @Autowired
    private QuesitoRepository quesitoRepository;

    @Autowired
    @Lazy
    private OpcaoService opcaoService;

    @Autowired
    private SecaoService secaoService;

    @Autowired
    @Lazy
    private RespostaService respostaService;

    @Transactional
    public Quesito create(Quesito quesito) {
        return quesitoRepository.save(quesito);
    }

    public List<Quesito> getAll() {
        return quesitoRepository.findAll();
    }

    public Quesito getById(Long id) {
        return quesitoRepository.findById(id)
        .orElseThrow(
                () -> new EntityNotFoundException(id, Quesito.class)
            );
    }

    @Transactional
    public Quesito update(Long id, Quesito quesito) {
        Quesito quesitoExistente = this.getById(id);
        
        quesitoExistente.setEnunciado(quesito.getEnunciado());
        quesitoExistente.setOrdem(quesito.getOrdem());
        quesitoExistente.setNivel(quesito.getNivel());
        quesitoExistente.setObrigatorio(quesito.getObrigatorio());
        quesitoExistente.setTipoResposta(quesito.getTipoResposta());

        return quesitoRepository.save(quesitoExistente);
    }

    @Transactional
    public void delete(Long id) {
        Quesito quesitoARemover = this.getById(id);
        Secao secao = quesitoARemover.getSecao();
        if(secao != null){
            secao.getQuesitos().removeIf(quesito -> quesito.getId().equals(id));
            secaoService.update(secao.getId(), secao);
        }
        Quesito superQuesito = quesitoARemover.getSuperQuesito();
        if(superQuesito != null){
            superQuesito.getSubQuesitos().removeIf(quesito -> quesito.getId().equals(id));
            quesitoRepository.save(superQuesito);
        }

        quesitoRepository.deleteById(id);
    }

    @Transactional
    public void deleteAll() {
        List<Quesito> quesitos = this.getAll();
        for (Quesito quesito : quesitos) {
            Secao secao = quesito.getSecao();
            if(secao != null){
                secao.getQuesitos().clear();
                secaoService.update(secao.getId(), secao);
            }
            Quesito superQuesito = quesito.getSuperQuesito();
            if(superQuesito != null){
                superQuesito.getSubQuesitos().clear();
                quesitoRepository.save(superQuesito);
            }
        }
        quesitoRepository.deleteAll();
    }

    @Transactional
    public Quesito addSubQuesito(Long idQuesito, Quesito subQuesito) {
        Quesito superQuesito = this.getById(idQuesito);

        subQuesito.setOrdem(superQuesito.getSubQuesitos().size());
        subQuesito.setNivel(superQuesito.getNivel() + 1);

        subQuesito.setSuperQuesito(superQuesito);
        superQuesito.getSubQuesitos().add(subQuesito);
        quesitoRepository.save(superQuesito);
        
        return superQuesito.getSubQuesitos().get(superQuesito.getSubQuesitos().size() - 1);
        
    }

    @Transactional
    public Opcao addOpcao(Long idQuesito, Opcao opcaoNovo) {
        Quesito quesito = this.getById(idQuesito);
        
        opcaoNovo.setOrdem(quesito.getOpcoes().size());
        opcaoNovo.setQuesito(quesito);
        quesito.getOpcoes().add(opcaoNovo);
        quesitoRepository.save(quesito);
        
        return quesito.getOpcoes().get(quesito.getOpcoes().size() - 1);
    }

    public List<Opcao> getOpcoes(Long idQuesito) {
        Quesito quesito = this.getById(idQuesito);
        
        return quesito.getOpcoes();
    }

    public List<Quesito> getSubQuesitos(Long idQuesito) {
        Quesito quesito = this.getById(idQuesito);
        
        return quesito.getSubQuesitos();
    }   

    @Transactional
    public Opcao addOpcaoHabilitadora(Long idQuesito, Long idOpcao) {
        Quesito quesito = this.getById(idQuesito);
        Opcao opcao = opcaoService.getById(idOpcao);

        if(quesito.getOpcoesHabilitadoras().contains(opcao)){
            throw new OpcaoHabilitadoraAlreadyInQuesitoException(idQuesito, idOpcao);
        }

        quesito.getOpcoesHabilitadoras().add(opcao);
        quesitoRepository.save(quesito);
        
        return opcao;
    }
    
    public Boolean estaHabilitado(Quesito quesito) {
        List<Opcao> opcoesHabilitadoras = quesito.getOpcoesHabilitadoras();
        if(opcoesHabilitadoras.isEmpty()){
            return true;
        }

        List<Quesito> quesitosPai = opcoesHabilitadoras.stream()
            .map(Opcao::getQuesito)
            .distinct()
            .toList();
        
        List<Resposta> respostas = quesitosPai.stream()
            .filter(q -> q != null)
            .filter(q -> q.getResposta() != null)
            .map(Quesito::getResposta)
            .toList();

        for (Resposta resposta : respostas) {
            List<Opcao> opcoesMarcadas = resposta.getOpcoesMarcadas();
            if(opcoesHabilitadoras.stream().anyMatch(opcao -> opcoesMarcadas.contains(opcao))){
                return true;
            }
            
        }
        return false;
    }

    public Boolean estaHabilitado(Long id) {
        Quesito quesito = this.getById(id);
        return estaHabilitado(quesito);
    }

    protected List<Opcao> getOpcoesMarcadas(Quesito quesito) {
        List<Opcao> retorno = quesito.getResposta().getOpcoesMarcadas();

        for (Quesito subQuesito : quesito.getSubQuesitos()) {
            retorno.addAll(this.getOpcoesMarcadas(subQuesito));
        }
    
        return retorno;
    }

    public StringBuilder verificaQuesitosObjetivosSemOpcao(List<Quesito> listaTodosQuesitos, StringBuilder erros) {
        List<Quesito> quesitosObjetivosSemOpcao = new ArrayList<>();
        for (Quesito quesito : listaTodosQuesitos) {
            String tipoResposta = quesito.getTipoResposta();
            if ((tipoResposta.equals("OBJETIVA_SIMPLES") || tipoResposta.equals("OBJETIVA_SIMPLES"))
            && quesito.getOpcoes().isEmpty()) {
                quesitosObjetivosSemOpcao.add(quesito);
            }
        }

        if(!quesitosObjetivosSemOpcao.isEmpty()) {
            erros.append("Os seguintes quesitos objetivos devem possuir pelo menos uma opção: ");
            for(Quesito quesito : quesitosObjetivosSemOpcao) {
                erros.append(quesito.getId() + ", ");
            }
            if (erros.length() > 0) {
                erros.setLength(erros.length() - 2); // Remove the last ", "
            }
            erros.append("\n");
        }
        return erros;
    }

    public Pair<Quesito, Map<Opcao, Opcao>> duplicar(Map<Opcao, Opcao> opcoesDuplicadas, Quesito quesitioToDuplicate) {
        Quesito quesito = new Quesito();
        quesito.setEnunciado(quesitioToDuplicate.getEnunciado());
        quesito.setObrigatorio(quesitioToDuplicate.getObrigatorio());
        quesito.setOrdem(quesitioToDuplicate.getOrdem());
        quesito.setNivel(quesitioToDuplicate.getNivel());
        quesito.setTipoResposta(quesitioToDuplicate.getTipoResposta());
        
        for(Opcao opcao : quesitioToDuplicate.getOpcoes()) {
            if(opcoesDuplicadas.containsKey(opcao)) {
                quesito.getOpcoes().add(opcoesDuplicadas.get(opcao));
            } else {
                Opcao novaOpcao = opcaoService.duplicar(opcao);
                novaOpcao.setQuesito(quesito);
                quesito.getOpcoes().add(novaOpcao);
                opcoesDuplicadas.put(opcao, novaOpcao);
            }
        }

        for(Opcao opcaoHabilitadora : quesitioToDuplicate.getOpcoesHabilitadoras()) {
            if(opcoesDuplicadas.containsKey(opcaoHabilitadora)) {
                quesito.getOpcoesHabilitadoras().add(opcoesDuplicadas.get(opcaoHabilitadora));
            } else {
                Opcao novaOpcaoHabilitadora = opcaoService.duplicar(opcaoHabilitadora);
                novaOpcaoHabilitadora.setQuesito(quesito);
                quesito.getOpcoesHabilitadoras().add(novaOpcaoHabilitadora);
                opcoesDuplicadas.put(opcaoHabilitadora, novaOpcaoHabilitadora);
            }
        }

        for(Quesito subQuesito : quesitioToDuplicate.getSubQuesitos()) {
            Pair<Quesito, Map<Opcao, Opcao>> pairQuesitoMapa = this.duplicar(opcoesDuplicadas, subQuesito);
            Quesito novoSubQuesito = pairQuesitoMapa.getFirst();
            opcoesDuplicadas = pairQuesitoMapa.getSecond();
            if(subQuesito.getSecao() != null) {
                novoSubQuesito.setSecao(quesito.getSecao());
            }
            if(subQuesito.getSuperQuesito() != null) {
                novoSubQuesito.setSuperQuesito(quesito);
            }
            quesito.getSubQuesitos().add(novoSubQuesito);
        }

        return new Pair<Quesito, Map<Opcao, Opcao>>(quesito, opcoesDuplicadas);
    }
}
