package br.ufrn.FormsFramework.mapper.resposta;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import br.ufrn.FormsFramework.mapper.opcao.OpcaoMapper;
import br.ufrn.FormsFramework.model.Opcao;
import br.ufrn.FormsFramework.model.Resposta;
import br.ufrn.FormsFramework.model.interfaces.RespostaFactory;

import static br.ufrn.FormsFramework.model.interfaces.GenericEntityToId.TToIds;

@Mapper(
    componentModel= MappingConstants.ComponentModel.SPRING,
    uses = {OpcaoMapper.class, RespostaFactory.class}    
)
public interface RespostaMapper {

    @Mapping(target = "conteudo", source = "conteudo")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "opcoesMarcadas", ignore = true)
    @Mapping(target = "quesito", ignore = true)
    Resposta toRespostaFromCreate(RespostaCreate respostaCreate);

    @Mapping(target = "conteudo")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "opcoesMarcadas", ignore = true)
    @Mapping(target = "quesito", ignore = true)
    Resposta toRespostaFromUpdate(RespostaUpdate respostaUpdate);
    
    @Mapping(target = "conteudo")
    @Mapping(target = "id")
    @Mapping(target = "tipoResposta", source = "resposta.quesito.tipoResposta")
    @Mapping(target = "opcoesMarcadasIds", source = "opcoesMarcadas", qualifiedByName = "opcoesToIds")
    @Mapping(target = "idQuesito", source = "quesito.id")
    RespostaOutput toRespostaOutput(Resposta resposta);
    
    @Mapping(target = "id")
    @Mapping(target = "conteudo")
    @Mapping(target = "tipoResposta", source = "resposta.quesito.tipoResposta")
    @Mapping(target = "opcoesMarcadas", source = "opcoesMarcadas")
    @Mapping(target = "idQuesito", source = "quesito.id")
    RespostaCompleteOutput toRespostaCompleteOutput(Resposta resposta);
    
    @Named("opcoesToIds")
    default List<Long> opcoesToIds(List<Opcao> opcoes) {
        return TToIds(opcoes);
    }
    
    default Resposta mapResposta(RespostaCreate respostaCreate) {
        Resposta resposta = RespostaFactory.createResposta(respostaCreate);
        return resposta;
    }

    default Resposta mapResposta(RespostaUpdate respostaUpdate) {
        Resposta resposta = RespostaFactory.createResposta(respostaUpdate);
        return resposta;
    } 

}
