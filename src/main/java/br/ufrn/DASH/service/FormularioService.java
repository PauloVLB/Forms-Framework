package br.ufrn.DASH.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.DASH.exception.FeedbackNotInFormularioException;
import br.ufrn.DASH.exception.EntityNotFoundException;
import br.ufrn.DASH.exception.FormularioInconsistenteException;
import br.ufrn.DASH.exception.FormularioNotTemplateException;
import br.ufrn.DASH.exception.FormularioPoorlyAnsweredException;
import br.ufrn.DASH.exception.FormularioTemplateException;
import br.ufrn.DASH.exception.QuesitoNotInFormularioException;
import br.ufrn.DASH.mapper.llm.LLMResponse;
import br.ufrn.DASH.model.Feedback;
import br.ufrn.DASH.model.Opcao;
import br.ufrn.DASH.model.Formulario;
import br.ufrn.DASH.model.Quesito;
import br.ufrn.DASH.model.Resposta;
import br.ufrn.DASH.model.Secao;
import br.ufrn.DASH.model.Usuario;
import br.ufrn.DASH.model.interfaces.Item;

import static br.ufrn.DASH.model.interfaces.GenericEntitySortById.sortById;
import br.ufrn.DASH.repository.FormularioRepository;
import br.ufrn.DASH.utils.Pair;
import jakarta.transaction.Transactional;

@Service
public class FormularioService {

    @Autowired
    private FormularioRepository formularioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RespostaService respostaService;

    @Autowired
    private QuesitoService quesitoService;

    @Autowired
    private SecaoService secaoService;

    @Autowired
    private OpcaoService opcaoService;

    @Autowired
    private LLMService llmService;

    @Autowired
    private FeedbackService feedbackService;

    @Transactional
    public Formulario create(Formulario formulario) {
        return formularioRepository.save(formulario);
    }

    public List<Formulario> getAll() {
        return formularioRepository.findAll();
    }

    public Formulario getById(Long id) {
        return formularioRepository.findById(id)
            .orElseThrow(
                () -> new EntityNotFoundException(id, Formulario.class)
            );
    }

    public Formulario getById(Long id, boolean incluirDesabilitados) {
        Formulario formulario = this.getById(id);

        if(incluirDesabilitados) {
           return formulario;
        } else {
            Formulario formularioSemDesabilitados = formulario;
            List<Secao> secoesSemDesabilitados = retirarDesabilitadosSecoes(formulario.getSecoes());
            formularioSemDesabilitados.setSecoes(secoesSemDesabilitados);
            return formularioSemDesabilitados;
        }
    }

    private List<Secao> retirarDesabilitadosSecoes(List<Secao> secoes) {
        List<Secao> secoesSemDesabilitados = new ArrayList<>();
        
        for(Secao secao : secoes) {
            secao.setSubSecoes(retirarDesabilitadosSecoes(secao.getSubSecoes()));
            secao.setQuesitos(retirarDesabilitadosQuesitos(secao.getQuesitos()));
            secoesSemDesabilitados.add(secao);
        }

        return secoesSemDesabilitados;
    }

    private List<Quesito> retirarDesabilitadosQuesitos(List<Quesito> quesitos) {
        List<Quesito> quesitosSemDesabilitados = new ArrayList<>();

        for(Quesito quesito : quesitos) {
            if(quesitoService.estaHabilitado(quesito)) {
                quesitosSemDesabilitados.add(quesito);
            }
            quesito.setSubQuesitos(retirarDesabilitadosQuesitos(quesito.getSubQuesitos()));
        }

        return quesitosSemDesabilitados;
    }

    @Transactional
    public Formulario update(Long id, Formulario formulario) {
        
        Formulario formularioExistente = this.getById(id);
        
        formularioExistente.setNome(formulario.getNome());
        formularioExistente.setDescricao(formulario.getDescricao());
        formularioExistente.setEhPublico(formulario.getEhPublico());
        formularioExistente.setFinalizado(formulario.getFinalizado());

        return formularioRepository.save(formularioExistente);
    }

    @Transactional
    public Formulario tornarPublico(Long id) {
        Formulario formulario = this.getById(id);
        formulario.setEhPublico(true);
        return formularioRepository.save(formulario);
    }

    @Transactional
    public Formulario tornarPrivado(Long id) {
        Formulario formulario = this.getById(id);
        formulario.setEhPublico(false);
        return formularioRepository.save(formulario);
    }

    @Transactional
    public void delete(Long id) {
        this.getById(id);
        formularioRepository.deleteById(id);
    }

    @Transactional
    public void deleteAll() {
        formularioRepository.deleteAll();
    }

    @Transactional
    public Secao addSecao(Long idFormulario, Secao secaoNova) {
        Formulario formulario = this.getById(idFormulario);
        
        secaoNova.setOrdem(formulario.getSecoes().size());
        secaoNova.setNivel(1);
        secaoNova.setFormulario(formulario);

        formulario.getSecoes().add(secaoNova);

        formularioRepository.save(formulario);

        return formulario.getSecoes().get(formulario.getSecoes().size() - 1);
    }

    @Transactional
    public Formulario duplicar(Long idFormulario, Long idUsuario) {
        Formulario formularioToDuplicate = this.getById(idFormulario);

        Usuario novoUsuario = usuarioService.getById(idUsuario);

        Formulario formularioDuplicado = new Formulario();

        formularioDuplicado.setNome(formularioToDuplicate.getNome() + " - Cópia");
        formularioDuplicado.setDescricao(formularioToDuplicate.getDescricao());
        formularioDuplicado.setEhPublico(formularioToDuplicate.getEhPublico());
        formularioDuplicado.setEhTemplate(formularioToDuplicate.getEhTemplate());
        formularioDuplicado.setUsuario(novoUsuario);

        Map<Opcao, Opcao> opcoesDuplicadas = new HashMap<Opcao, Opcao>();
        for (Secao secao : formularioToDuplicate.getSecoes()) {
            Pair<Secao, Map<Opcao, Opcao>> pairSecaoMapa = secaoService.duplicar(opcoesDuplicadas, secao);
            Secao novaSecao = pairSecaoMapa.getFirst();
            opcoesDuplicadas = pairSecaoMapa.getSecond();
            
            novaSecao.setFormulario(formularioDuplicado);
            formularioDuplicado.getSecoes().add(novaSecao);
        }

        return formularioRepository.save(formularioDuplicado);
    }
    
    @Transactional
    public Resposta addResposta(Long idFormulario, Long idQuesito, Resposta respostaNova) {
        Formulario formulario = this.getById(idFormulario);
        if(formulario.getEhTemplate()) {
            throw new FormularioTemplateException(idFormulario);
        }
        Quesito quesito = quesitoService.getById(idQuesito);
        
        Formulario formularioDoQuesito = quesito.getFormulario();
        if(formularioDoQuesito == null || !formularioDoQuesito.getId().equals(idFormulario)) {
            throw new QuesitoNotInFormularioException(idFormulario, idQuesito);
        }
        Resposta respostaCriada;
        if(quesito.getResposta() == null){
            respostaCriada = respostaService.create(respostaNova);
            quesito.setResposta(respostaCriada);
            respostaCriada.setQuesito(quesito);            
        }else{
            respostaCriada = respostaService.getById(quesito.getResposta().getId());

            respostaCriada.setConteudo(respostaNova.getConteudo());
        }
        
        // talvez mudar isso para sintaxe melhor
        quesitoService.create(quesito);
        return respostaService.create(respostaCriada);
    }

    @Transactional
    public Formulario addFormularioFromTemplate(Long idTemplate) {
        Formulario formularioTemplate = this.getById(idTemplate);
        if(!formularioTemplate.getEhTemplate()) throw new FormularioNotTemplateException(idTemplate);
        Formulario formularioCriado = this.duplicar(formularioTemplate.getId(), null);
        formularioCriado.setEhTemplate(false);
        return formularioRepository.save(formularioCriado);
    }

    @Transactional
    public Map<String, String> getFeedbackLLM(Long idFormulario) {
        String prompt = 
        "Com base no seguinte JSON, que corresponde a um prontuário de um paciente, faça um diagnóstico do paciente. " + 
        "Você não precisa se ater a divisão de seções e quesitos, apenas faça um diagnóstico geral do paciente. " +
        "Seu diagnóstico será avaliado por um médico especialista, que pode ou não concordar com o diagnóstico gerado. " +
        "Portanto, pode dar sugestões de exames, tratamentos, ou qualquer outra informação que julgar relevante. " +
        "Pode assumir que o paciente é real, e que você está fazendo um diagnóstico real.\n" + 
        "Sua mensagem será mostrada ao usuário, deve ser transparente para ele que você está lendo as informações " +
        "de um JSON. Para ele deve ser apenas uma sugestão de diagnóstico.\n" +
        "Além disso, escreva sua resposta como plain text. Não use formatação, nem imagens.\n\n";

        Formulario formulario = this.getById(idFormulario);
        prompt += toJson(formulario);

        Map<String, String> respostas = new HashMap<>();
        LLMResponse response = llmService.getRespostaFromPrompt(prompt);
        respostas.put("content", response.choices().get(0).message().content());

        formulario.setFeedbackLLM(respostas.get("content"));
        this.update(idFormulario, formulario);
        
        return respostas;
    }

    private String toJson(Formulario formulario) {
        StringBuilder json = new StringBuilder("{\n");

        json.append("\t\"nome\": \"").append(formulario.getNome()).append("\",\n");
        json.append("\t\"descricao\": \"").append(formulario.getDescricao()).append("\",\n");
        json.append("\t\"secoes\": [\n");

        for (Secao secao : formulario.getSecoes()) {
            json.append("\t\t{\n");
            json.append("\t\t\t\"nome\": \"").append(secao.getTitulo()).append("\",\n");
            json.append("\t\t\t\"quesitos\": [\n");

            for (Quesito quesito : secao.getQuesitos()) {
                json.append("\t\t\t\t{\n");
                json.append("\t\t\t\t\t\"nome\": \"").append(quesito.getEnunciado()).append("\",\n");
                if(quesito.getResposta() != null) {
                    json.append("\t\t\t\t\t\"resposta\": \"").append(quesito.getResposta().getConteudo()).append("\"\n");
                } else {
                    json.append("\t\t\t\t\t\"resposta\": \"\"\n");
                }
                json.append("\t\t\t\t},\n");
            }

            json.append("\t\t\t]\n");
            json.append("\t\t},\n");
        }

        json.append("\t]\n");
        json.append("}");

        return json.toString();
    }

    @Transactional
    public Feedback addFeedback(Long idFormulario, Feedback feedback) {
        Formulario formulario = this.getById(idFormulario);
        feedback.setFormulario(formulario);
        
        feedback = feedbackService.create(feedback);

        formulario.getFeedbacks().add(feedback);

        this.create(formulario);
        return feedback;
    }

    @Transactional
    public void removeFeedback(Long idFormulario, Long idFeedback) {
        Formulario formulario = this.getById(idFormulario);
        Feedback feedback = feedbackService.getById(idFeedback);
        
        if(formulario.getFeedbacks().contains(feedback)){
            formulario.getFeedbacks().remove(feedback);
            this.create(formulario);
            feedbackService.delete(idFeedback);
        }else{
            throw new FeedbackNotInFormularioException(idFormulario, idFeedback);
        }
    }

    public Feedback getFeedback(Long idFormulario) {
        Formulario formulario = this.getById(idFormulario);
        List<Opcao> opcoesMarcadas = this.getOpcoesMarcadas(formulario);

        System.out.println("1");
        int qntFeedbacks = 0;
        Feedback feedbackToReturn = Feedback.inconclusivo();
        for (Feedback feedback : formulario.getFeedbacks()) {
            if(ehSubsequencia(feedback.getOpcoesMarcadas(), opcoesMarcadas)) {
                qntFeedbacks++;
                feedbackToReturn = feedback;
            }
        }
        
        if(qntFeedbacks > 1) {
            feedbackToReturn = Feedback.inconclusivo();
        } 

        return feedbackToReturn;
    }
    
    private List<Opcao> getOpcoesMarcadas(Formulario formulario) {
        List<Opcao> retorno = new ArrayList<>();
        
        for (Secao secao : formulario.getSecoes()) {
            retorno.addAll(secaoService.getOpcoesMarcadas(secao));
        }
        
        if(!retorno.isEmpty())
            sortById(retorno);
        
        return retorno;
    }
    
    private boolean ehSubsequencia(List<Opcao> opcoesFeedback, List<Opcao> opcoesResposta) {
        int slow = 0;
        int fast = 0;
        int sizeFast = opcoesResposta.size();
        int sizeSlow = opcoesFeedback.size();

        while (fast < sizeFast && slow < sizeSlow) {
            if(opcoesFeedback.get(slow).getId().compareTo(opcoesResposta.get(fast).getId()) < 0){
                return false;
            }
            if(opcoesFeedback.get(slow).getId().equals(opcoesResposta.get(fast).getId())){
                slow++;
            }
            fast++;
        }

        return true;
    }

    @Transactional
    public Formulario finalizarFormulario(Long idFormulario) {
        
        Formulario formulario = this.getById(idFormulario);       
        Queue<Secao> filaSecoes = new LinkedList<>(formulario.getSecoes());
        List<Secao> listaTodasSecoes = new ArrayList<>();
        Queue<Quesito> filaQuesitos = new LinkedList<>();
        List<Quesito> listaTodosQuesitos = new ArrayList<>();
        
        // Percorre as Secoes
        while (!filaSecoes.isEmpty()) {
            Secao secao = filaSecoes.poll();
            listaTodasSecoes.add(secao);
            filaSecoes.addAll(secao.getSubSecoes());
            filaQuesitos.addAll(secao.getQuesitos());
        }
        
        // Percorre os Quesitos
        while (!filaQuesitos.isEmpty()) {
            Quesito quesito = filaQuesitos.poll();
            listaTodosQuesitos.add(quesito);
            filaQuesitos.addAll(quesito.getSubQuesitos());
        }
        
        StringBuilder erros = new StringBuilder("");
        erros = verificaCamposObrigatoriosDeEntidades(formulario, listaTodasSecoes, listaTodosQuesitos, erros);
        erros = verificaFormularioSemSecao(formulario, erros);
        erros = secaoService.verificaSecoesVazias(listaTodasSecoes, erros);
        erros = quesitoService.verificaQuesitosObjetivosSemOpcao(listaTodosQuesitos, erros);
        erros = opcaoService.verificaOpcoesComMesmoNome(listaTodosQuesitos, erros);
        erros = verificaOrdemOpcoesHabilitadoras(formulario, erros);

        if (erros.length() > 0) {
            throw new FormularioInconsistenteException(erros.toString());
        }

        formulario.setFinalizado(true);
        return this.create(formulario);
    }

    public Formulario finalizarRespostas(Long idFormulario) {
        Formulario formulario = this.getById(idFormulario);
        
        if(formulario.getEhTemplate()) {
            throw new FormularioTemplateException(idFormulario);
        }

        Queue<Secao> filaSecoes = new LinkedList<>(formulario.getSecoes());
        List<Secao> listaTodasSecoes = new ArrayList<>();
        Queue<Quesito> filaQuesitos = new LinkedList<>();
        List<Quesito> listaTodosQuesitos = new ArrayList<>();
        
        // Percorre as Secoes
        while (!filaSecoes.isEmpty()) {
            Secao secao = filaSecoes.poll();
            listaTodasSecoes.add(secao);
            filaSecoes.addAll(secao.getSubSecoes());
            filaQuesitos.addAll(secao.getQuesitos());
        }
        
        // Percorre os Quesitos
        while (!filaQuesitos.isEmpty()) {
            Quesito quesito = filaQuesitos.poll();
            listaTodosQuesitos.add(quesito);
            filaQuesitos.addAll(quesito.getSubQuesitos());
        }

        StringBuilder erros = new StringBuilder("");
        validaRespostas(listaTodosQuesitos, erros);
        validaQuesitosObrigatorios(listaTodosQuesitos, erros);

        if (erros.length() > 0) {
            throw new FormularioPoorlyAnsweredException(erros.toString());
        }

        return formulario;
    }

    private StringBuilder verificaFormularioSemSecao(Formulario formulario, StringBuilder erros) {
        if (formulario.getSecoes().isEmpty()) {
            erros.append("O formulário deve ter pelo menos uma seção.\n");
        }
        return erros;
    }

    private StringBuilder verificaCamposObrigatoriosDeEntidades(Formulario formulario, List<Secao> listaTodasSecoes, List<Quesito>listaTodosQuesitos, StringBuilder erros) {

        if (formulario.getNome() == null || formulario.getNome().isEmpty()) {
            erros.append("O formulário deve possuir um nome.\n");
        }

        if (formulario.getDescricao() == null || formulario.getDescricao().isEmpty()) {
            erros.append("O formulário deve possuir uma descrição.\n");
        }
        
        List<Secao> secoesSemNome = new ArrayList<>();
        List<Quesito> quesitosSemEnunciado = new ArrayList<>();
        List<Opcao> opcoesSemTextoAlternativa = new ArrayList<>();
        for (Secao secao : listaTodasSecoes) {
            if (secao.getTitulo() == null || secao.getTitulo().isEmpty()) {
                secoesSemNome.add(secao);
            }
        }
        for (Quesito quesito : listaTodosQuesitos) {
            if (quesito.getEnunciado() == null || quesito.getEnunciado().isEmpty()) {
                quesitosSemEnunciado.add(quesito);
            }
            for (Opcao opcao : quesito.getOpcoes()) {
                if (opcao.getTextoAlternativa() == null || opcao.getTextoAlternativa().isEmpty()) {
                    opcoesSemTextoAlternativa.add(opcao);
                }
            }
        }
        
        if(!secoesSemNome.isEmpty()) {
            erros.append("As seguintes seções devem possuir um título: ");
            for(Secao secao : secoesSemNome) {
                erros.append(secao.getId() + ", ");
            }
            if (erros.length() > 0) {
                erros.setLength(erros.length() - 2); // Remove the last ", "
            }
            erros.append("\n");
        }
        if(!quesitosSemEnunciado.isEmpty()) {
            erros.append("Os seguintes quesitos devem possuir um enunciado: ");
            for(Quesito quesito : quesitosSemEnunciado) {
                erros.append(quesito.getId() + ", ");
            }
            if (erros.length() > 0) {
                erros.setLength(erros.length() - 2); // Remove the last ", "
            }
            erros.append("\n");
        }

        if(!opcoesSemTextoAlternativa.isEmpty()) {
            erros.append("As seguintes opções devem possuir um nome: ");
            for(Opcao opcao : opcoesSemTextoAlternativa) {
                erros.append(opcao.getId() + ", ");
            }
            if (erros.length() > 0) {
                erros.setLength(erros.length() - 2); // Remove the last ", "
            }
            erros.append("\n");
        }

        return erros;
    }

    private StringBuilder verificaOrdemOpcoesHabilitadoras(Formulario formulario, StringBuilder erros) {
        List<Quesito> quesitosJaVistos = new ArrayList<>();

        for(Item secao : formulario.getSecoes()) {
            quesitosJaVistos.addAll(percorreFormulario(secao.getSubItens()));
        }

        List<Opcao> opcoesJaVistas = new ArrayList<>();

        for(Quesito quesito : quesitosJaVistos) {

            List<Long> opcoesHabilitadorasInvalidas = new ArrayList<>();
            for(Opcao opcao : quesito.getOpcoesHabilitadoras()) {
                if(!opcoesJaVistas.contains(opcao)) {
                    opcoesHabilitadorasInvalidas.add(opcao.getId());
                }
            }

            if(!opcoesHabilitadorasInvalidas.isEmpty()) {
                erros.append("As opções habilitadoras ");
                erros.append(
                    String.join(",", opcoesHabilitadorasInvalidas.stream()
                                                                           .map(Object::toString)
                                                                           .toArray(String[]::new))
                );
                erros.append(" aparecem após o quesito habilitado ").append(quesito.getId()).append("\n");
            }

            opcoesJaVistas.addAll(quesito.getOpcoes());
        }
        
        return erros;
    }

    private List<Quesito> percorreFormulario(List<Item> itens) {

        List<Quesito> quesitos = new ArrayList<>();

        for(Item item : itens) {
            if(item instanceof Quesito) {
                quesitos.add((Quesito) item);
            } else {
                quesitos.addAll(percorreFormulario(item.getSubItens()));
            }
        }

        return quesitos;
    }

    private void validaRespostas(List<Quesito> listaTodosQuesitos, StringBuilder erros) {
        for (Quesito quesito : listaTodosQuesitos) {
            if (quesito.getResposta() != null) {
                quesito.getResposta().validar(erros);
            }
        }
    }

    private void validaQuesitosObrigatorios(List<Quesito> listaTodosQuesitos, StringBuilder erros) {
        for (Quesito quesito : listaTodosQuesitos) {
            if (quesito.getObrigatorio() && quesito.getResposta() == null) {
                erros.append("O quesito ").append(quesito.getId()).append(" é obrigatório e não foi respondido.\n");
            }
        }
    }

}