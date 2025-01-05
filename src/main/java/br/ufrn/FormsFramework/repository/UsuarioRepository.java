package br.ufrn.FormsFramework.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufrn.FormsFramework.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
