package br.ufrn.FormsFramework.controller;

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

import br.ufrn.FormsFramework.mapper.feedback.FeedbackCreate;
import br.ufrn.FormsFramework.mapper.feedback.FeedbackMapper;
import br.ufrn.FormsFramework.mapper.feedback.FeedbackOutput;
import br.ufrn.FormsFramework.mapper.formulario.FormularioCompleteOutput;
import br.ufrn.FormsFramework.mapper.formulario.FormularioCreate;
import br.ufrn.FormsFramework.mapper.formulario.FormularioMapper;
import br.ufrn.FormsFramework.mapper.formulario.FormularioOutput;
import br.ufrn.FormsFramework.mapper.formulario.FormularioUpdate;
import br.ufrn.FormsFramework.mapper.resposta.RespostaCreate;
import br.ufrn.FormsFramework.mapper.resposta.RespostaMapper;
import br.ufrn.FormsFramework.mapper.resposta.RespostaOutput;
import br.ufrn.FormsFramework.mapper.secao.SecaoCreate;
import br.ufrn.FormsFramework.mapper.secao.SecaoMapper;
import br.ufrn.FormsFramework.mapper.secao.SecaoOutput;
import br.ufrn.FormsFramework.model.Feedback;
import br.ufrn.FormsFramework.model.Formulario;
import br.ufrn.FormsFramework.model.Resposta;
import br.ufrn.FormsFramework.model.Secao;
import br.ufrn.FormsFramework.service.FormularioService;

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
    private FeedbackMapper feedbackMapper;

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

    @GetMapping("/{idFormulario}/feedbackLLM")
    public ResponseEntity<Map<String, String>> getPath(@PathVariable Long idFormulario) {
        Map<String, String> response = formularioService.getFeedbackLLM(idFormulario);
        return new ResponseEntity<Map<String, String>>(response, HttpStatus.OK);
    }
    
    @PostMapping("/{idFormulario}/addFeedback")
    public ResponseEntity<FeedbackOutput> addFeedback(@PathVariable Long idFormulario, @RequestBody FeedbackCreate feedbackCreate) {
        Feedback feedbackNovo = feedbackMapper.toFeedbackFromCreate(feedbackCreate);        
        Feedback feedbackCriado = formularioService.addFeedback(idFormulario, feedbackNovo);
        FeedbackOutput feedbackOutput = feedbackMapper.toFeedbackOutput(feedbackCriado);
        return new ResponseEntity<FeedbackOutput>(feedbackOutput, HttpStatus.CREATED);
    }

    @DeleteMapping("/{idFormulario}/feedback/{idFeedback}/removeFeedback")
    public ResponseEntity<Boolean> removeFeedback(@PathVariable Long idFormulario, @PathVariable Long idFeedback) {
        // removeFeedback é void
        formularioService.removeFeedback(idFormulario, idFeedback);
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    @GetMapping("/{idFormulario}/feedback")
    public ResponseEntity<FeedbackOutput> feedback(@PathVariable Long idFormulario) {
        Feedback feedback = formularioService.getFeedback(idFormulario);
        FeedbackOutput feedbackOutput = feedbackMapper.toFeedbackOutput(feedback);
        return new ResponseEntity<FeedbackOutput>(feedbackOutput, HttpStatus.OK);
    }

    @PatchMapping("/{idFormulario}/finalizarFormulario")
    public ResponseEntity<FormularioOutput> finalizarFormulario(@PathVariable Long idFormulario) {
        Formulario formularioFinalizado = formularioService.finalizarFormulario(idFormulario);
        FormularioOutput formularioOutput = formularioMapper.toFormularioOutput(formularioFinalizado);
        return new ResponseEntity<FormularioOutput>(formularioOutput, HttpStatus.OK);
    }

    @PatchMapping("/{idFormulario}/finalizarRespostas")
    public ResponseEntity<FormularioOutput> finalizarRespostas(@PathVariable Long idFormulario) {
        Formulario formularioRespondido = formularioService.finalizarRespostas(idFormulario);
        FormularioOutput formularioOutput = formularioMapper.toFormularioOutput(formularioRespondido);
        return new ResponseEntity<FormularioOutput>(formularioOutput, HttpStatus.OK);
    }
    
    @PostMapping("/{idFormulario}/instanciarFormulario/{idUsuario}")
    public ResponseEntity<FormularioOutput> instanciarFormulario(@PathVariable Long idFormulario, @PathVariable Long idUsuario) {
        Formulario formularioInstanciado = formularioService.instanciarFormulario(idFormulario, idUsuario);
        FormularioOutput formularioOutput = formularioMapper.toFormularioOutput(formularioInstanciado);
        return new ResponseEntity<FormularioOutput>(formularioOutput, HttpStatus.CREATED);
    }

    @GetMapping("/{idFormulario}/estatisticasLLM")
    public ResponseEntity<Map<String, String>> gerarEstatisticasLLM(@PathVariable Long idFormulario) {
        Map<String, String> response = formularioService.gerarEstatisticasLLM(idFormulario);
        return new ResponseEntity<Map<String, String>>(response, HttpStatus.OK);
    }
}
