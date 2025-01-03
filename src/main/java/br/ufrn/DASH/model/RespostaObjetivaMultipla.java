package br.ufrn.DASH.model;

import jakarta.persistence.Entity;

@Entity
public class RespostaObjetivaMultipla extends Resposta {

    public void validar(StringBuilder erros) {
        if (this.getConteudo().size() < 1) {
            erros.append("Escolha pelo menos uma opção em respostas objetivas múltiplas\n");
        }
    }
    
}
