package br.ufrn.FormsFramework.model;

import java.util.List;
import java.util.ArrayList;

import br.ufrn.FormsFramework.model.interfaces.GenericEntity;
import br.ufrn.FormsFramework.model.interfaces.Item;
import br.ufrn.FormsFramework.model.interfaces.ItemUtils;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
public class Secao implements GenericEntity, Item{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String titulo;
    private Integer ordem;
    private Integer nivel;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "superSecao")
    private List<Secao> subSecoes = new ArrayList<Secao>();

    @ManyToOne
    private Secao superSecao;
    
    @ManyToOne
    private Formulario formulario;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "secao")
    private List<Quesito> quesitos = new ArrayList<Quesito>();

    public List<Item> getSubItens() {
        List<Item> subItens = new ArrayList<Item>();
        subItens.addAll(this.subSecoes);
        subItens.addAll(this.quesitos);
        ItemUtils.ordenar(subItens);
        return subItens;
    }

    public Formulario getFormulario() {
        if(this.superSecao != null) {
            return this.superSecao.getFormulario();
        } else {
            return this.formulario;
        }
    }
}
