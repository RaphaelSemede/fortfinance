package br.com.login.login.DAO;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.login.login.model.Usuario;

public interface IUsuario extends CrudRepository<Usuario, Integer>{
    List<Usuario> findByEmail(String email);
}   
