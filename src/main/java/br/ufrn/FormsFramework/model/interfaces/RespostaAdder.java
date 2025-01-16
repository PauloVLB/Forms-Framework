package br.ufrn.FormsFramework.model.interfaces;

import java.util.Map;

import org.springframework.stereotype.Component;

import br.ufrn.FormsFramework.model.Resposta;
import br.ufrn.FormsFramework.model.RespostaDissertativaCurta;
import br.ufrn.FormsFramework.model.RespostaDissertativaLonga;
import br.ufrn.FormsFramework.model.RespostaObjetivaMultipla;
import br.ufrn.FormsFramework.model.RespostaObjetivaSimples;

@Component
public abstract class RespostaAdder {

    public void addRespostas(Map<String, Resposta> respostas) {
        respostas.put("DISSERTATIVA_CURTA", new RespostaDissertativaCurta());
        respostas.put("DISSERTATIVA_LONGA", new RespostaDissertativaLonga());
        respostas.put("OBJETIVA_MULTIPLA", new RespostaObjetivaMultipla());
        respostas.put("OBJETIVA_SIMPLES", new RespostaObjetivaSimples());
        
        addRespostasExtras(respostas);
    }


    public abstract void addRespostasExtras(Map<String, Resposta> respostas);
}
