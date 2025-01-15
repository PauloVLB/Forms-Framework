package br.ufrn.instancies.JUMP.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.ufrn.FormsFramework.controller.FormularioController;
import br.ufrn.instancies.JUMP.mapper.arquivo.InfoIngresso;
@Controller
public class FormularioEventoJump extends FormularioController {

    @Override
    @GetMapping("/{idFormulario}/informacoesArquivo")
    public ResponseEntity<InfoIngresso> gerarArquivo(@PathVariable Long idFormulario) {
        InfoIngresso infoIngresso = formularioService.gerarInformacoesArquivo(idFormulario);
        return new ResponseEntity<InfoIngresso>(infoIngresso, HttpStatus.OK);
    }
}
