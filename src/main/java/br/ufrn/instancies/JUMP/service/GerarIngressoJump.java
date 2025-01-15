package br.ufrn.instancies.JUMP.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import br.ufrn.FormsFramework.model.Formulario;
import br.ufrn.FormsFramework.model.Secao;
import br.ufrn.FormsFramework.service.interfaces.GeracaoArquivo;
import br.ufrn.instancies.JUMP.mapper.arquivo.DadosPessoaisIngresso;
import br.ufrn.instancies.JUMP.mapper.arquivo.InfoIngresso;

@Service
public class GerarIngressoJump implements GeracaoArquivo  {
    @Override
    public InfoIngresso montarArquivo(Formulario formulario) {
        List<Secao> secoes = formulario.getSecoes();

        String nomeTitular = "";
        String cpfTitular = "";
        String idadeTitular = "";
        Boolean ehMeiaEntrada = false;
        for(Secao secao : secoes) {
            if(secao.getTitulo().toLowerCase().equals("dados pessoais")) {
                nomeTitular = secao.getQuesitos().get(0).getResposta().getConteudo().get(0);
                cpfTitular = secao.getQuesitos().get(1).getResposta().getConteudo().get(0);
                idadeTitular = secao.getQuesitos().get(2).getResposta().getConteudo().get(0);

                if(idadeTitular != null && Integer.parseInt(idadeTitular) < 18) {
                    ehMeiaEntrada = true;
                }
            } 
        }

        return new InfoIngresso(
            DateTimeFormatter.ofPattern("dd/MM/yyyy").format(LocalDateTime.now()), 
            formulario.getDescricao(),
            ehMeiaEntrada,
            new DadosPessoaisIngresso(nomeTitular, cpfTitular, idadeTitular),
            formulario.getFeedbackLLM() == null ? "" : formulario.getFeedbackLLM()
        );
    }
}
