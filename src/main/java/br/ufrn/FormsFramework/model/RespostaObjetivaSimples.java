package br.ufrn.FormsFramework.model;

import jakarta.persistence.Entity;

@Entity
public class RespostaObjetivaSimples extends Resposta {

    public void validar(StringBuilder erros) {
        if (this.getConteudo().size() > 1) {
            erros.append("Resposta objetiva simples não pode ter mais de uma opção como resposta\n");
        }
    }
    
}
