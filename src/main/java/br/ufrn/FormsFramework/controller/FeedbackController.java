package br.ufrn.FormsFramework.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.FormsFramework.mapper.feedback.FeedbackCreate;
import br.ufrn.FormsFramework.mapper.feedback.FeedbackMapper;
import br.ufrn.FormsFramework.mapper.feedback.FeedbackOutput;
import br.ufrn.FormsFramework.mapper.feedback.FeedbackUpdate;
import br.ufrn.FormsFramework.mapper.opcao.OpcaoMapper;
import br.ufrn.FormsFramework.mapper.opcao.OpcaoOutput;
import br.ufrn.FormsFramework.model.Feedback;
import br.ufrn.FormsFramework.model.Opcao;
import br.ufrn.FormsFramework.service.FeedbackService;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private FeedbackMapper feedbackMapper;

    @Autowired
    private OpcaoMapper opcaoMapper;

    @PostMapping
    public ResponseEntity<FeedbackOutput> create(@RequestBody FeedbackCreate feedbackCreate) {
        Feedback feedback = feedbackMapper.toFeedbackFromCreate(feedbackCreate);
        Feedback feedbackCriado = feedbackService.create(feedback);
        FeedbackOutput feedbackOutput = feedbackMapper.toFeedbackOutput(feedbackCriado);
        return new ResponseEntity<FeedbackOutput>(feedbackOutput, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<FeedbackOutput> getById(@PathVariable Long id) {
        Feedback feedback = feedbackService.getById(id);
        FeedbackOutput feedbackOutput = feedbackMapper.toFeedbackOutput(feedback);
        return new ResponseEntity<FeedbackOutput>(feedbackOutput, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<FeedbackOutput>> getAll() {
        List<Feedback> secoes = feedbackService.getAll();
        List<FeedbackOutput> secoesOutput = secoes
                .stream()
                .map(feedbackMapper::toFeedbackOutput)
                .toList();
        return new ResponseEntity<List<FeedbackOutput>>(secoesOutput, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FeedbackOutput> update(@PathVariable Long id, @RequestBody FeedbackUpdate feedbackUpdate) {
        Feedback feedback = feedbackMapper.toFeedbackFromUpdate(feedbackUpdate);
        Feedback feedbackAtualizado = feedbackService.update(id, feedback);
        FeedbackOutput feedbackOutput = feedbackMapper.toFeedbackOutput(feedbackAtualizado);
        return new ResponseEntity<FeedbackOutput>(feedbackOutput, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        feedbackService.delete(id);
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteAll() {
        feedbackService.deleteAll();
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    @PostMapping("/{idFeedback}/opcao/{idOpcao}/addOpcao")
    public ResponseEntity<OpcaoOutput> addOpcao(@PathVariable Long idFeedback, @PathVariable Long idOpcao) {
        Opcao opcaoCriado = feedbackService.addOpcao(idFeedback, idOpcao);
        OpcaoOutput opcaoOutput = opcaoMapper.toOpcaoOutput(opcaoCriado);
        return new ResponseEntity<OpcaoOutput>(opcaoOutput, HttpStatus.OK);
    }

    @PostMapping("/{idFeedback}/opcao/{idOpcao}/removeOpcao")
    public ResponseEntity<OpcaoOutput> removeOpcao(@PathVariable Long idFeedback, @PathVariable Long idOpcao) {
        Opcao opcaoCriado = feedbackService.removeOpcao(idFeedback, idOpcao);
        OpcaoOutput opcaoOutput = opcaoMapper.toOpcaoOutput(opcaoCriado);
        return new ResponseEntity<OpcaoOutput>(opcaoOutput, HttpStatus.OK);
    }

}
