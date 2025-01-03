package br.ufrn.DASH.model;

import jakarta.persistence.Entity;

@Entity
public class RespostaDissertativaCurta extends Resposta {

    public void validar(StringBuilder erros) {
        if (this.getConteudo().size() > 1) {
            erros.append("Resposta dissertativa curta não pode ter mais de uma resposta\n");
        }
        else if (this.getConteudo().size() == 1 && 
                 this.getConteudo().get(0).length() > 255){
            erros.append("Resposta dissertativa curta não pode ter mais de 255 caracteres\n");
        }
    }
    
}
