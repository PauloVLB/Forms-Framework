package br.ufrn.FormsFramework.model;

import java.util.List;
import java.util.ArrayList;

import br.ufrn.FormsFramework.model.enums.TipoUsuario;
import br.ufrn.FormsFramework.model.interfaces.GenericEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario implements GenericEntity{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   
    private String nome;
    private String login;
    private String senha;

    private TipoUsuario tipoUsuario;

    @OneToMany(mappedBy = "usuario")
    private List<Formulario> formularios = new ArrayList<Formulario>();
}
