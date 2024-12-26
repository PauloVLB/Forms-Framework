package br.ufrn.DASH.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.DASH.mapper.diagnostico.DiagnosticoCreate;
import br.ufrn.DASH.mapper.diagnostico.DiagnosticoMapper;
import br.ufrn.DASH.mapper.diagnostico.DiagnosticoOutput;
import br.ufrn.DASH.mapper.formulario.FormularioCompleteOutput;
import br.ufrn.DASH.mapper.formulario.FormularioCreate;
import br.ufrn.DASH.mapper.formulario.FormularioMapper;
import br.ufrn.DASH.mapper.formulario.FormularioOutput;
import br.ufrn.DASH.mapper.formulario.FormularioUpdate;
import br.ufrn.DASH.mapper.resposta.RespostaCreate;
import br.ufrn.DASH.mapper.resposta.RespostaMapper;
import br.ufrn.DASH.mapper.resposta.RespostaOutput;
import br.ufrn.DASH.mapper.secao.SecaoCreate;
import br.ufrn.DASH.mapper.secao.SecaoMapper;
import br.ufrn.DASH.mapper.secao.SecaoOutput;
import br.ufrn.DASH.model.Diagnostico;
import br.ufrn.DASH.model.Formulario;
import br.ufrn.DASH.model.Resposta;
import br.ufrn.DASH.model.Secao;
import br.ufrn.DASH.service.FormularioService;

@RestController
@RequestMapping("/formulario")
public class FormularioController {

    @Autowired
    private FormularioService formularioService;

    @Autowired
    private FormularioMapper formularioMapper;

    @Autowired
    private SecaoMapper secaoMapper;

    @Autowired
    private RespostaMapper respostaMapper;

    @Autowired
    private DiagnosticoMapper diagnosticoMapper;

    @PostMapping
    public ResponseEntity<FormularioOutput> create(@RequestBody FormularioCreate formularioCreate) {
        Formulario formulario = formularioMapper.toFormularioFromCreate(formularioCreate);
        Formulario formularioCriado = formularioService.create(formulario);
        FormularioOutput formularioOutput = formularioMapper.toFormularioOutput(formularioCriado);
        return new ResponseEntity<FormularioOutput>(formularioOutput, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FormularioOutput> getById(@PathVariable Long id) {
        Formulario formulario = formularioService.getById(id);
        FormularioOutput formularioOutput = formularioMapper.toFormularioOutput(formulario);
        return new ResponseEntity<FormularioOutput>(formularioOutput, HttpStatus.OK);
    }

    @GetMapping("/{id}/complete")
    public ResponseEntity<FormularioCompleteOutput> getByIdComplete(
        @PathVariable Long id, 
        @RequestParam(defaultValue = "true") boolean incluirDesabilitados
    ) {
        Formulario formulario = formularioService.getById(id, incluirDesabilitados);
        FormularioCompleteOutput formularioOutput = formularioMapper.toFormularioCompleteOutput(formulario);
        return new ResponseEntity<FormularioCompleteOutput>(formularioOutput, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<FormularioOutput>> getAll() {
        List<Formulario> formularios = formularioService.getAll();
        List<FormularioOutput> formulariosOutput = formularios
                .stream()
                .map(formularioMapper::toFormularioOutput)
                .toList();
        return new ResponseEntity<List<FormularioOutput>>(formulariosOutput, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FormularioOutput> update(@PathVariable Long id, @RequestBody FormularioUpdate formularioUpdate) {
        Formulario formulario = formularioMapper.toFormularioFromUpdate(formularioUpdate);
        Formulario formularioAtualizado = formularioService.update(id, formulario);
        FormularioOutput formularioOutput = formularioMapper.toFormularioOutput(formularioAtualizado);
        return new ResponseEntity<FormularioOutput>(formularioOutput, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        formularioService.delete(id);
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteAll() {
        formularioService.deleteAll();
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    @PatchMapping("/{id}/tornarPublico")
    public ResponseEntity<FormularioOutput> tornarPublico(@PathVariable Long id) {
        Formulario formulario = formularioService.tornarPublico(id);
        FormularioOutput formularioOutput = formularioMapper.toFormularioOutput(formulario);
        return new ResponseEntity<FormularioOutput>(formularioOutput, HttpStatus.OK);
    }

    @PatchMapping("/{id}/tornarPrivado")
    public ResponseEntity<FormularioOutput> tornarPrivado(@PathVariable Long id) {
        Formulario formulario = formularioService.tornarPrivado(id);
        FormularioOutput formularioOutput = formularioMapper.toFormularioOutput(formulario);
        return new ResponseEntity<FormularioOutput>(formularioOutput, HttpStatus.OK);
    }

    @PostMapping("/{idFormulario}/addSecao")
    public ResponseEntity<SecaoOutput> addSecao(@PathVariable Long idFormulario, @RequestBody SecaoCreate secaoCreate) {
        Secao secaoNova = secaoMapper.toSecaoFromCreate(secaoCreate);
        Secao secaoCriada = formularioService.addSecao(idFormulario, secaoNova);
        SecaoOutput secaoOutput = secaoMapper.toSecaoOutput(secaoCriada);
        return new ResponseEntity<SecaoOutput>(secaoOutput, HttpStatus.CREATED);
    }

    @PostMapping("/{idFormulario}/duplicar")
    public ResponseEntity<FormularioOutput> duplicar(
        @PathVariable Long idFormulario,
        @RequestParam Long idUsuario                                                      
    ) {
        Formulario formularioDuplicado = formularioService.duplicar(idFormulario, idUsuario);
        FormularioOutput formularioOutput = formularioMapper.toFormularioOutput(formularioDuplicado);
        return new ResponseEntity<FormularioOutput>(formularioOutput, HttpStatus.CREATED);
    }
    
    @PostMapping("/{idFormulario}/quesito/{idQuesito}/addResposta")
    public ResponseEntity<RespostaOutput> addResposta(@PathVariable Long idFormulario, @PathVariable Long idQuesito, @RequestBody RespostaCreate respostaCreate){
        Resposta respostaNova = respostaMapper.toRespostaFromCreate(respostaCreate);
        Resposta respostaCriada = formularioService.addResposta(idFormulario, idQuesito, respostaNova);
        RespostaOutput respostaOutput = respostaMapper.toRespostaOutput(respostaCriada);
        return new ResponseEntity<RespostaOutput>(respostaOutput, HttpStatus.CREATED);                    
    }

    @PostMapping("/template/{idTemplate}/addFormulario")
    public ResponseEntity<FormularioOutput> addFormularioFromTemplate(@PathVariable Long idTemplate) {
        Formulario formularioCriado = formularioService.addFormularioFromTemplate(idTemplate);
        FormularioOutput formularioOutput = formularioMapper.toFormularioOutput(formularioCriado);
        return new ResponseEntity<FormularioOutput>(formularioOutput, HttpStatus.CREATED);
    }

    @GetMapping("/{idFormulario}/diagnosticoLLM")
    public ResponseEntity<Map<String, String>> getPath(@PathVariable Long idFormulario) {
        Map<String, String> response = formularioService.getDiagnosticoLLM(idFormulario);
        return new ResponseEntity<Map<String, String>>(response, HttpStatus.OK);
    }
    
    @PostMapping("/{idFormulario}/addDiagnostico")
    public ResponseEntity<DiagnosticoOutput> addDiagnostico(@PathVariable Long idFormulario, @RequestBody DiagnosticoCreate diagnosticoCreate) {
        Diagnostico diagnosticoNovo = diagnosticoMapper.toDiagnosticoFromCreate(diagnosticoCreate);        
        Diagnostico diagnosticoCriado = formularioService.addDiagnostico(idFormulario, diagnosticoNovo);
        DiagnosticoOutput diagnosticoOutput = diagnosticoMapper.toDiagnosticoOutput(diagnosticoCriado);
        return new ResponseEntity<DiagnosticoOutput>(diagnosticoOutput, HttpStatus.CREATED);
    }

    @DeleteMapping("/{idFormulario}/diagnostico/{idDiagnostico}/removeDiagnostico")
    public ResponseEntity<Boolean> removeDiagnostico(@PathVariable Long idFormulario, @PathVariable Long idDiagnostico) {
        // removeDiagnostico é void
        formularioService.removeDiagnostico(idFormulario, idDiagnostico);
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    @GetMapping("/{idFormulario}/diagnostico")
    public ResponseEntity<DiagnosticoOutput> diagnostico(@PathVariable Long idFormulario) {
        Diagnostico diagnostico = formularioService.getDiagnostico(idFormulario);
        DiagnosticoOutput diagnosticoOutput = diagnosticoMapper.toDiagnosticoOutput(diagnostico);
        return new ResponseEntity<DiagnosticoOutput>(diagnosticoOutput, HttpStatus.OK);
    }

    @PatchMapping("/{idFormulario}/finalizarFormulario")
    public ResponseEntity<FormularioOutput> finalizarFormulario(@PathVariable Long idFormulario) {
        Formulario formularioFinalizado = formularioService.finalizarFormulario(idFormulario);
        FormularioOutput formularioOutput = formularioMapper.toFormularioOutput(formularioFinalizado);
        return new ResponseEntity<FormularioOutput>(formularioOutput, HttpStatus.OK);
    }
}
