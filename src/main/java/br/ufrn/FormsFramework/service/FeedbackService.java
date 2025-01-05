package br.ufrn.FormsFramework.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import br.ufrn.FormsFramework.exception.FeedbackAndOpcaoIncompatibleException;
import br.ufrn.FormsFramework.exception.EntityNotFoundException;
import br.ufrn.FormsFramework.exception.OpcaoAlreadyInFeedbackException;
import br.ufrn.FormsFramework.exception.OpcaoNotInFeedbackExecption;
import br.ufrn.FormsFramework.model.Feedback;
import br.ufrn.FormsFramework.model.Opcao;
import br.ufrn.FormsFramework.repository.FeedbackRepository;
import jakarta.transaction.Transactional;

@Service
public class FeedbackService {
    
    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    @Lazy
    private OpcaoService opcaoService;

    @Transactional
    public Feedback create(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    public List<Feedback> getAll() {
        return feedbackRepository.findAll();
    }

    public Feedback getById(Long id) {
        return feedbackRepository.findById(id)
        .orElseThrow(
            () -> new EntityNotFoundException(id, Feedback.class)
        );
    }

    @Transactional
    public Feedback update(Long id, Feedback feedback) {
        Feedback feedbackExistente = this.getById(id);
        
        feedbackExistente.setDescricao(feedback.getDescricao());

        return feedbackRepository.save(feedbackExistente);
    }

    @Transactional
    public void delete(Long id) {
        feedbackRepository.deleteById(id);
    }

    @Transactional
    public void deleteAll() {
        feedbackRepository.deleteAll();
    }

    @Transactional
    public Opcao addOpcao(Long idFeedback, Long idOpcao) {
        Feedback feedback = this.getById(idFeedback);
        Opcao opcao = opcaoService.getById(idOpcao);
        
        if(feedback.getFormulario() != opcao.getQuesito().getFormulario()){
            throw new FeedbackAndOpcaoIncompatibleException(idFeedback, idOpcao);
        }


        if(feedback.getOpcoesMarcadas().contains(opcao)){
            throw new OpcaoAlreadyInFeedbackException(idFeedback, idOpcao);
        }

        feedback.getOpcoesMarcadas().add(opcao);
        feedbackRepository.save(feedback);
        return opcao;
    }

    @Transactional
    public Opcao removeOpcao(Long idFeedback, Long idOpcao) {
        Feedback feedback = this.getById(idFeedback);
        Opcao opcao = opcaoService.getById(idOpcao);
        
        if(!feedback.getOpcoesMarcadas().contains(opcao)){
            throw new OpcaoNotInFeedbackExecption(idFeedback, idOpcao);
        }else{
            feedback.getOpcoesMarcadas().remove(opcao);
            opcao.getFeedbacks().remove(feedback);
            feedbackRepository.save(feedback);
        }
        return opcao;
    }
}
