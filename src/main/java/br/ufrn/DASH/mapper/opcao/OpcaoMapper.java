package br.ufrn.DASH.mapper.opcao;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import br.ufrn.DASH.model.Feedback;
import br.ufrn.DASH.model.Opcao;
import br.ufrn.DASH.model.Quesito;
import static br.ufrn.DASH.model.interfaces.GenericEntityToId.TToIds;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OpcaoMapper {

    @Mapping(target = "textoAlternativa")
    @Mapping(target = "ordem")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "quesito", ignore = true)
    @Mapping(target = "feedbacks", ignore = true)
    Opcao toOpcaoFromCreate(OpcaoCreate opcaoCreate);

    @Mapping(target = "textoAlternativa")
    @Mapping(target = "ordem")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "quesito", ignore = true)
    @Mapping(target = "feedbacks", ignore = true)
    Opcao toOpcaoFromUpdate(OpcaoUpdate opcaoUpdate);

    @Mapping(target = "id")
    @Mapping(target = "textoAlternativa")
    @Mapping(target = "ordem")
    @Mapping(target = "quesitoId", source = "quesito.id")
    @Mapping(target = "feedbacksIds", source = "feedbacks", qualifiedByName = "feedbacksToIds")
    OpcaoOutput toOpcaoOutput(Opcao opcao);


    @Mapping(target = "id")
    @Mapping(target = "textoAlternativa")
    @Mapping(target = "ordem")
    @Mapping(target = "quesitoId", source = "quesito.id")
    OpcaoCompleteOutput toOpcaoCompleteOutput(Opcao opcao);

    @Named("quesitosToIds")
    default List<Long> quesitosToIds(List<Quesito> quesitos) {
        return TToIds(quesitos);
    }

    @Named("feedbacksToIds")
    default List<Long> feedbacksToIds(List<Feedback> feedbacks) {
        return TToIds(feedbacks);
    }
}
