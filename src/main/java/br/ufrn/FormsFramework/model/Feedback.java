package br.ufrn.FormsFramework.model;

import java.util.ArrayList;
import java.util.List;

import br.ufrn.FormsFramework.model.interfaces.GenericEntity;
import static br.ufrn.FormsFramework.model.interfaces.GenericEntitySortById.sortById;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Feedback implements GenericEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String descricao;
    
    @ManyToMany
    private List<Opcao> opcoesMarcadas = new ArrayList<Opcao>(); 
    
    @ManyToOne
    private Formulario formulario;
    
    public List<Opcao> getOpcoesMarcadas(){
        if(opcoesMarcadas != null)
            sortById(opcoesMarcadas);
        return opcoesMarcadas;
    }
    public static Feedback inconclusivo() {
        return new Feedback(null, "Inconclusivo", null, null);
    }
}
