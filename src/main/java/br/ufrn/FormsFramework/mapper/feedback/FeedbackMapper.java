package br.ufrn.FormsFramework.mapper.feedback;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import br.ufrn.FormsFramework.model.Feedback;
import br.ufrn.FormsFramework.model.Opcao;
import static br.ufrn.FormsFramework.model.interfaces.GenericEntityToId.TToIds;

@Mapper(componentModel  =  MappingConstants.ComponentModel.SPRING)
public interface FeedbackMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "opcoesMarcadas", ignore = true)
    @Mapping(target = "formulario", ignore = true)
    @Mapping(target = "descricao")
    Feedback toFeedbackFromCreate(FeedbackCreate feedbackCreate);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "opcoesMarcadas", ignore = true)
    @Mapping(target = "formulario", ignore = true)
    @Mapping(target = "descricao")
    Feedback toFeedbackFromUpdate(FeedbackUpdate feedbackUpdate);
    
    @Mapping(target = "id")
    @Mapping(target = "descricao")
    @Mapping(target = "formularioId", source = "formulario.id")
    @Mapping(target = "opcoesMarcadasIds", source = "opcoesMarcadas", qualifiedByName = "opcoesToIds")
    FeedbackOutput toFeedbackOutput(Feedback feedback);

    @Named("opcoesToIds")
    default List<Long> quesitosToIds(List<Opcao> opcoes) {
        return TToIds(opcoes);
    }
}
