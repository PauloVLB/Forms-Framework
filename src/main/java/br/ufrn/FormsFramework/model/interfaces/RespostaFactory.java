package br.ufrn.FormsFramework.model.interfaces;

import java.util.HashMap;
import java.util.Map;

import org.mapstruct.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufrn.FormsFramework.mapper.resposta.RespostaCreate;
import br.ufrn.FormsFramework.mapper.resposta.RespostaUpdate;
import br.ufrn.FormsFramework.model.Resposta;
import br.ufrn.FormsFramework.model.enums.TipoResposta;

@Component
public class RespostaFactory {

    @Autowired
    private RespostaAdder jorge;

    private Map<String, Resposta> respostas;

    public RespostaFactory() {
        respostas = new HashMap<>();
    }

    @ObjectFactory
    public Resposta createResposta(RespostaCreate respostaCreate) {
        jorge.addRespostas(respostas);
        return createResposta(respostaCreate.tipoResposta());
    }

    @ObjectFactory
    public Resposta createResposta(RespostaUpdate respostaUpdate) {
        jorge.addRespostas(respostas);
        return createResposta(respostaUpdate.tipoResposta());
    }

    private Resposta createResposta(TipoResposta tipoResposta) {
        return respostas.get(tipoResposta.toString());
    }
}