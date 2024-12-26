package br.ufrn.DASH.mapper.usuario;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import br.ufrn.DASH.model.Formulario;
import br.ufrn.DASH.model.Usuario;
import static br.ufrn.DASH.model.interfaces.GenericEntityToId.TToIds;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UsuarioMapper{

    @Mapping(target = "nome")
    @Mapping(target = "login")
    @Mapping(target = "senha")
    @Mapping(target = "tipoUsuario")    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "formularios", ignore = true)
    Usuario toUsuarioFromCreate(UsuarioCreate usuarioCreate);

    @Mapping(target = "nome")
    @Mapping(target = "login")
    @Mapping(target = "senha")
    @Mapping(target = "tipoUsuario")    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "formularios", ignore = true)
    Usuario toUsuarioFromUpdate(UsuarioUpdate usuarioUpdate);
    
    @Mapping(target = "id")
    @Mapping(target = "nome")
    @Mapping(target = "login")
    @Mapping(target = "senha")
    @Mapping(target = "tipoUsuario")    
    @Mapping(target = "formulariosIds", source = "formularios", qualifiedByName = "formulariosToIds")
    UsuarioOutput toUsuarioOutput(Usuario usuario);

    @Named("formulariosToIds")
    default List<Long> formulariosToIds(List<Formulario> secoes) {
        return TToIds(secoes);
    }
}
