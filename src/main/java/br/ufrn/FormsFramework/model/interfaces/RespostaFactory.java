package br.ufrn.FormsFramework.model.interfaces;

import java.util.Map;

import org.mapstruct.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;

import br.ufrn.FormsFramework.mapper.resposta.RespostaCreate;
import br.ufrn.FormsFramework.mapper.resposta.RespostaUpdate;
import br.ufrn.FormsFramework.model.Resposta;
import br.ufrn.FormsFramework.model.RespostaDissertativaCurta;
import br.ufrn.FormsFramework.model.RespostaDissertativaLonga;
import br.ufrn.FormsFramework.model.RespostaObjetivaMultipla;
import br.ufrn.FormsFramework.model.RespostaObjetivaSimples;
import br.ufrn.FormsFramework.model.enums.TipoResposta;

public class RespostaFactory {

    @Autowired
    private static Jorge jorge;

    private static Map<String, Resposta> respostas;

    @ObjectFactory
    public static Resposta createResposta(RespostaCreate respostaCreate) {
        jorge.addRespostas(respostas);
        return createResposta(respostaCreate.tipoResposta());
    }

    @ObjectFactory
    public static Resposta createResposta(RespostaUpdate respostaUpdate) {
        jorge.addRespostas(respostas);
        return createResposta(respostaUpdate.tipoResposta());
    }

    private static Resposta createResposta(TipoResposta tipoResposta) {
        return respostas.get(tipoResposta.toString());
    }
}