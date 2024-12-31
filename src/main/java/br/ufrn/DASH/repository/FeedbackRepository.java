package br.ufrn.DASH.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufrn.DASH.model.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

}
