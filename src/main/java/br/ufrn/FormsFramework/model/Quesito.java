package br.ufrn.FormsFramework.model;

import java.util.ArrayList;
import java.util.List;

import br.ufrn.FormsFramework.model.interfaces.GenericEntity;
import br.ufrn.FormsFramework.model.interfaces.Item;
import br.ufrn.FormsFramework.model.interfaces.ItemUtils;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Quesito implements GenericEntity, Item{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String enunciado;
    private Boolean obrigatorio;
    private Integer ordem;
    private Integer nivel;
    private String tipoResposta;

    @OneToOne(cascade = CascadeType.REMOVE, mappedBy = "quesito")
    private Resposta resposta;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "quesito")
    private List<Opcao> opcoes = new ArrayList<Opcao>();

    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Opcao> opcoesHabilitadoras = new ArrayList<Opcao>(); 

    @ManyToOne
    private Secao secao;

    @ManyToOne
    private Quesito superQuesito;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Quesito> subQuesitos = new ArrayList<Quesito>();

    public List<Item> getSubItens() {
        List<Item> subItens = new ArrayList<Item>();
        subItens.addAll(this.subQuesitos);
        ItemUtils.ordenar(subItens);
        return subItens;
    }

    public Formulario getFormulario() {
        if(this.secao != null) {
            return this.secao.getFormulario();
        } else if(this.superQuesito != null) {
            return this.superQuesito.getFormulario();
        } else {
            return null;
        }
    }
}
