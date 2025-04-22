package br.com.login.login.DAO;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import br.com.login.login.model.Usuario;

public interface IUsuario extends CrudRepository<Usuario, Integer>{
    Optional<Usuario> findByEmailIgnoreCase(String email);
    Optional<Usuario> findByUsernameIgnoreCase(String username);
    Optional<Usuario> findByTelefone(String telefone);
}   
