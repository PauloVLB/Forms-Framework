package br.ufrn.FormsFramework.mapper.formulario;

import static br.ufrn.FormsFramework.model.interfaces.GenericEntityToId.TToIds;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import br.ufrn.FormsFramework.model.Feedback;
import br.ufrn.FormsFramework.mapper.opcao.OpcaoCompleteOutput;
import br.ufrn.FormsFramework.mapper.quesito.QuesitoCompleteOutput;
import br.ufrn.FormsFramework.mapper.resposta.RespostaCompleteOutput;
import br.ufrn.FormsFramework.mapper.secao.SecaoCompleteOutput;
import br.ufrn.FormsFramework.mapper.secao.SecaoMapper;
import br.ufrn.FormsFramework.mapper.subItem.ItemOutput;
import br.ufrn.FormsFramework.model.Opcao;
import br.ufrn.FormsFramework.model.Formulario;
import br.ufrn.FormsFramework.model.Quesito;
import br.ufrn.FormsFramework.model.Resposta;
import br.ufrn.FormsFramework.model.Secao;
import br.ufrn.FormsFramework.model.interfaces.Item;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    uses = {SecaoMapper.class}
)
public interface FormularioMapper {

    @Mapping(target = "nome")
    @Mapping(target = "descricao")
    @Mapping(target = "ehPublico")
    @Mapping(target = "ehTemplate")
    @Mapping(target = "finalizado", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "secoes", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "feedbackLLM", ignore = true)
    @Mapping(target = "feedbacks", ignore = true)
    @Mapping(target = "formularioPai", ignore = true)
    @Mapping(target = "instanciasFormulario", ignore = true)
    @Mapping(target = "respondido", ignore = true)
    Formulario toFormularioFromCreate(FormularioCreate formularioCreate);

    @Mapping(target = "nome")
    @Mapping(target = "descricao")
    @Mapping(target = "ehPublico")
    @Mapping(target = "finalizado")
    @Mapping(target = "ehTemplate", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "secoes", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "feedbackLLM", ignore = true)
    @Mapping(target = "feedbacks", ignore = true)
    @Mapping(target = "formularioPai", ignore = true)
    @Mapping(target = "instanciasFormulario", ignore = true)
    @Mapping(target = "respondido", ignore = true)
    Formulario toFormularioFromUpdate(FormularioUpdate formularioUpdate);

    @Mapping(target = "nome")
    @Mapping(target = "descricao")
    @Mapping(target = "ehPublico")
    @Mapping(target = "ehTemplate")
    @Mapping(target = "finalizado")
    @Mapping(target = "respondido")
    @Mapping(target = "id")
    @Mapping(target = "usuarioId", source = "usuario.id")
    @Mapping(target = "secoesIds", source = "secoes", qualifiedByName = "secoesToIds")
    @Mapping(target = "feedbacksIds", source = "feedbacks", qualifiedByName = "feedbacksToIds")
    @Mapping(target = "instanciasFormularioIds", source = "instanciasFormulario", qualifiedByName = "instanciasFormularioToIds")
    @Mapping(target = "formularioPaiId", source = "formularioPai.id")
    FormularioOutput toFormularioOutput(Formulario formulario);
    
    @Named("feedbacksToIds")
    default List<Long> feedbacksToIds(List<Feedback> feedbacks) {
        return TToIds(feedbacks);
    }

    @Named("instanciasFormularioToIds")
    default List<Long> instanciasFormularioToIds(List<Formulario> instanciasFormulario) {
        return TToIds(instanciasFormulario);
    }

    @Mapping(target = "nome")
    @Mapping(target = "descricao")
    @Mapping(target = "ehPublico")
    @Mapping(target = "ehTemplate")
    @Mapping(target = "finalizado")
    @Mapping(target = "respondido")
    @Mapping(target = "id")
    @Mapping(target = "usuarioId", source = "usuario.id")
    @Mapping(target = "secoes", source = "secoes")
    @Mapping(target = "instanciasFormularioIds", source = "instanciasFormulario", qualifiedByName = "instanciasFormularioToIds")
    @Mapping(target = "formularioPaiId", source = "formularioPai.id")
    FormularioCompleteOutput toFormularioCompleteOutput(Formulario formulario);
    
    default List<SecaoCompleteOutput> toSecoesCompleteOutput(List<Secao> secoes) {
        List<SecaoCompleteOutput> secoesCompleteOutput = new ArrayList<>();

        int ordem = 1;
        for (Secao secao : secoes) {
            secoesCompleteOutput.add(toSecaoCompleteOutput(secao, ordem + "."));
            ordem++;
        }

        return secoesCompleteOutput;
    }

    default SecaoCompleteOutput toSecaoCompleteOutput(Secao secao, String numeracao) {
        List<ItemOutput> subItensOutput = new ArrayList<>();

        List<Item> subItens = secao.getSubItens();
        int ordem = 1;
        for (Item subItem : subItens) {
            if (subItem instanceof Secao) {
                subItensOutput.add(toSecaoCompleteOutput((Secao) subItem, numeracao + ordem + "."));
            } else {
                subItensOutput.add(toQuesitoCompleteOutput((Quesito) subItem, numeracao + ordem + "."));
            }
            ordem++;
        }


        return new SecaoCompleteOutput(
            secao.getId(),
            "secao",
            numeracao,
            secao.getTitulo(),
            secao.getOrdem(),
            secao.getNivel(),
            subItensOutput,
            secao.getSuperSecao() != null ? secao.getSuperSecao().getId() : null,
            secao.getFormulario() != null ? secao.getFormulario().getId() : null
        );
    }

    default QuesitoCompleteOutput toQuesitoCompleteOutput(Quesito quesito, String numeracao) {
        List<QuesitoCompleteOutput> subQuesitosOutput = new ArrayList<>();

        int ordem = 1;
        for (Quesito subQuesito : quesito.getSubQuesitos()) {
            subQuesitosOutput.add(toQuesitoCompleteOutput(subQuesito, numeracao + ordem + "."));
            ordem++;
        }

        return new QuesitoCompleteOutput(
            quesito.getId(),
            "quesito",
            numeracao,
            quesito.getEnunciado(),
            quesito.getObrigatorio(),
            quesito.getOrdem(),
            quesito.getNivel(),
            quesito.getTipoResposta(),
            quesito.getSuperQuesito() != null ? quesito.getSuperQuesito().getId() : null,
            quesito.getSecao() != null ? quesito.getSecao().getId() : null,
            toRespostaCompleteOutput(quesito.getResposta()),
            opcoesToIds(quesito.getOpcoesHabilitadoras()),
            subQuesitosOutput,
            quesito.getOpcoes().stream().map(this::toOpcaoCompleteOutput).toList()
        );
    }

    @Mapping(target = "id")
    @Mapping(target = "conteudo")
    @Mapping(target = "opcoesMarcadas", source = "opcoesMarcadas")
    @Mapping(target = "idQuesito", source = "quesito.id")
    RespostaCompleteOutput toRespostaCompleteOutput(Resposta resposta);

    @Mapping(target = "id")
    @Mapping(target = "textoAlternativa")
    @Mapping(target = "ordem")
    @Mapping(target = "quesitoId", source = "quesito.id")
    OpcaoCompleteOutput toOpcaoCompleteOutput(Opcao opcao);

    default List<Long> opcoesToIds(List<Opcao> opcoes) {
        return TToIds(opcoes);
    }
    
}
