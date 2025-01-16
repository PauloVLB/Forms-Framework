package br.ufrn.instancies.GRAB.model.interfaces;

import java.util.Map;

import org.springframework.stereotype.Component;

import br.ufrn.FormsFramework.model.Resposta;
import br.ufrn.FormsFramework.model.interfaces.RespostaAdder;

@Component
public class RespostaAdderGrab extends RespostaAdder {

    @Override
    public void addRespostasExtras(Map<String, Resposta> respostas) {
        
    }
    
}
