package br.com.login.login.DAO;

import org.springframework.data.repository.CrudRepository;

import br.com.login.login.model.Usuario;

public interface IUsuario extends CrudRepository<Usuario, Integer>{

}
