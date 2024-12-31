package br.ufrn.DASH.model;

import java.util.ArrayList;
import java.util.List;

import br.ufrn.DASH.model.interfaces.GenericEntity;
import br.ufrn.DASH.model.interfaces.ItemUtils;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Formulario implements GenericEntity{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    private Boolean finalizado = false;
    private Boolean ehPublico;
    private Boolean ehTemplate;

    @Lob
    private String feedbackLLM;

    @ManyToOne
    private Usuario usuario;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "formulario")
    private List<Secao> secoes = new ArrayList<Secao>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "formulario")
    private List<Feedback> feedbacks = new ArrayList<Feedback>();
    public List<Secao> getSecoes() {
        ItemUtils.ordenar(secoes);
        return secoes;
    }
}
