package br.ufrn.instancies.GRAB.model.interfaces;

import java.util.Map;

import org.springframework.stereotype.Component;

import br.ufrn.FormsFramework.model.Resposta;
import br.ufrn.FormsFramework.model.interfaces.RespostaAdder;
import br.ufrn.instancies.GRAB.model.RespostaArquivo;

@Component
public class RespostaAdderGrab extends RespostaAdder {

    @Override
    public void addRespostasExtras(Map<String, Resposta> respostas) {
        respostas.put("ARQUIVO", new RespostaArquivo());
    }
    
}
