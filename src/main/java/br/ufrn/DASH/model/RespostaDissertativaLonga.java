package br.ufrn.DASH.model;

import jakarta.persistence.Entity;

@Entity
public class RespostaDissertativaLonga extends Resposta {

    public void validar(StringBuilder erros) {
        if (this.getConteudo().size() > 1) {
            erros.append("Resposta dissertativa longa não pode ter mais de uma resposta\n");
        }
        else if (this.getConteudo().size() == 1 && 
                 this.getConteudo().get(0).length() > 2048){
            erros.append("Resposta dissertativa longa não pode ter mais de 2048 caracteres\n");
        }
    }
    
}
