package br.ufrn.instancies.JUMP.mapper.arquivo;

import br.ufrn.FormsFramework.model.interfaces.IInformacoesArquivo;

public record InfoIngresso(
    String dataCompra,
    String sobreEvento,
    Boolean ehMeiaEntrada,
    DadosPessoaisIngresso dadosPessoais,
    String sugestaoLLM    
) implements IInformacoesArquivo
{}
