package br.com.login.login.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.login.login.DAO.IUsuario;
import br.com.login.login.model.Usuario;

@RestController
@CrossOrigin
@RequestMapping("/cadastrar")
public class UsuarioController{
	
	@Autowired
	private IUsuario dao;
	
	@GetMapping
	public ResponseEntity<List<Usuario>> listaUsuarios (){
		List<Usuario> lista =(List<Usuario>) dao.findAll();
		return ResponseEntity.status(200).body(lista);
	}
	
	@PostMapping
	public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario) {

		List<Usuario> usuarioExistente = dao.findByEmail(usuario.getEmail());
		if(!usuarioExistente.isEmpty()){
			return ResponseEntity.status(400).build(); // Return 400 Bad Request for existing email
		}

		Usuario usuarioNovo = dao.save(usuario);
		return ResponseEntity.status(201).body(usuarioNovo);
	}
	@PutMapping
	public ResponseEntity<Usuario> editarUsuario(@RequestBody Usuario usuario) {
		Usuario usuarioNovo = dao.save(usuario);
		return ResponseEntity.status(201).body(usuarioNovo);
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluirUsuario (@PathVariable Integer id){
		dao.deleteById(id);
		return ResponseEntity.status(204).build();
	}
}
