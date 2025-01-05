package br.ufrn.FormsFramework.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufrn.FormsFramework.model.Formulario;

public interface FormularioRepository extends JpaRepository<Formulario, Long> {

}
