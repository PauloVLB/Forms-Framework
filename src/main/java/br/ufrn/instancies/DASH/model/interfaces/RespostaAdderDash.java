package br.ufrn.instancies.DASH.model.interfaces;

import java.util.Map;

import org.springframework.stereotype.Component;

import br.ufrn.FormsFramework.model.Resposta;
import br.ufrn.FormsFramework.model.interfaces.RespostaAdder;

@Component
public class RespostaAdderDash extends RespostaAdder {

    @Override
    public void addRespostasExtras(Map<String, Resposta> respostas) {
    }
    
}
