package br.com.login.login.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	public ResponseEntity<?> criarUsuario(@RequestBody Usuario usuario) {

		Optional<Usuario> usuarioExistente = dao.findByUsernameIgnoreCase(usuario.getUsername());
		Optional<Usuario> emailUsado = dao.findByEmailIgnoreCase(usuario.getEmail());

		if(usuarioExistente.isPresent()){
			return ResponseEntity.badRequest().body("Nome já em uso");
		}

		if (emailUsado.isPresent()) {
			return ResponseEntity.badRequest().body("Email já cadastrado");
		}

		try {
			Usuario usuarioNovo = dao.save(usuario);
			return ResponseEntity.status(201).body(usuarioNovo);
		} catch (Exception e) {
			return ResponseEntity.status(500).body("Erro ao criar usuário");
		}
	}

	@GetMapping("/verificar-email")
	public ResponseEntity<Map<String, Boolean>> verificarEmail(@RequestParam String email) {
		try {
			Optional<Usuario> usuario = dao.findByEmailIgnoreCase(email);
	
			Map<String, Boolean> resposta = new HashMap<>();
			if (usuario.isPresent()) {
				resposta.put("emailValido", false);
			} 
			else {
				resposta.put("emailValido", true);
			}
	
			return ResponseEntity.ok(resposta);
		} catch (Exception e) {
			Map<String, Boolean> erro = new HashMap<>();
			erro.put("emailValido", false);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
		}
	}

	@GetMapping("/verificar-username")
	public ResponseEntity<Map<String, Boolean>> verificarUsername(@RequestParam String username) {
		try {
			Optional<Usuario> usuario = dao.findByUsernameIgnoreCase(username);
			
			Map<String, Boolean> resposta = new HashMap<>();
			if (usuario.isPresent()) {
				resposta.put("usernameValido", false);
			} 
			else {
				resposta.put("usernameValido", true);
			}
			
			return ResponseEntity.ok(resposta);
		} catch (Exception e) {
			Map<String, Boolean> erro = new HashMap<>();
			erro.put("usernameValido", false);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
		}
	}


	@PutMapping("/editar/{id}")
	public ResponseEntity<Usuario> editarUsuario(@PathVariable Integer id, @RequestBody Usuario usuario) {

		Optional<Usuario> usuarioExistente = dao.findById(id);
		if (!usuarioExistente.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		usuario.setId(id); 

		Usuario usuarioNovo = dao.save(usuario);
		return ResponseEntity.status(201).body(usuarioNovo);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluirUsuario (@PathVariable Integer id){
		dao.deleteById(id);
		return ResponseEntity.status(204).build();
	}
}
