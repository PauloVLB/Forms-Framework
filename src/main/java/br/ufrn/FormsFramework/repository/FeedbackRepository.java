package br.ufrn.FormsFramework.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufrn.FormsFramework.model.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

}
