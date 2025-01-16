package br.ufrn.instancies.GRAB.model;

import java.util.Base64;

import br.ufrn.FormsFramework.model.Resposta;
import jakarta.persistence.Entity;

@Entity
public class RespostaArquivo extends Resposta {
    
    private static final long MAX_SIZE = 10 * 1024 * 1024;

    public void validar(StringBuilder erros) {
        if (this.getConteudo().size() > 1) {
            erros.append("Resposta arquivo não pode ter mais de um arquivo como resposta\n");
            return;
        }
        String conteudoBase64 = this.getConteudo().get(0);
        if (conteudoBase64 == null || conteudoBase64.isEmpty()) {
            erros.append("Resposta arquivo não pode ter conteúdo vazio\n");
            return;
        }

        try {
            byte[] conteudo = Base64.getDecoder().decode(conteudoBase64);

            if(conteudo.length > MAX_SIZE) {
                erros.append("Resposta arquivo não pode ter mais de 10MB\n");
            }
        } catch (IllegalArgumentException e) {
            erros.append("Resposta arquivo não pode ter conteúdo inválido\n");
        } catch (Exception e) {
            erros.append("Erro desconhecido ao validar resposta arquivo\n");
        }


    }
}
