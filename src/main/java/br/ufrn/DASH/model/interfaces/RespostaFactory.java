package br.ufrn.DASH.model.interfaces;

import org.mapstruct.ObjectFactory;

import br.ufrn.DASH.mapper.resposta.RespostaCreate;
import br.ufrn.DASH.mapper.resposta.RespostaUpdate;
import br.ufrn.DASH.model.Resposta;
import br.ufrn.DASH.model.RespostaDissertativaCurta;
import br.ufrn.DASH.model.RespostaDissertativaLonga;
import br.ufrn.DASH.model.RespostaObjetivaMultipla;
import br.ufrn.DASH.model.RespostaObjetivaSimples;
import br.ufrn.DASH.model.enums.TipoResposta;

public class RespostaFactory {

    @ObjectFactory
    public static Resposta createResposta(RespostaCreate respostaCreate) {
        return createResposta(respostaCreate.tipoResposta());
    }

    @ObjectFactory
    public static Resposta createResposta(RespostaUpdate respostaUpdate) {
        return createResposta(respostaUpdate.tipoResposta());
    }

    private static Resposta createResposta(TipoResposta tipoResposta) {
        switch (tipoResposta) {
            case TipoResposta.DISSERTATIVA_CURTA:
                return new RespostaDissertativaCurta();
            case TipoResposta.DISSERTATIVA_LONGA:
                return new RespostaDissertativaLonga();
            case TipoResposta.OBJETIVA_SIMPLES:
                return new RespostaObjetivaSimples();
            case TipoResposta.OBJETIVA_MULTIPLA:
                return new RespostaObjetivaMultipla();
            default:
                throw new IllegalArgumentException("Tipo de resposta desconhecido: " + tipoResposta);
        }
    }
}