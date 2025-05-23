package br.com.fortfinance.controller;

import br.com.fortfinance.DAO.IUsuario;
import br.com.fortfinance.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/cadastrar")
public class UsuarioController {
	
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
		Optional<Usuario> telefoneUsado = dao.findByTelefone(usuario.getTelefone());

		if(usuarioExistente.isPresent()){
			return ResponseEntity.badRequest().body("Nome já em uso");
		}

		if(telefoneUsado.isPresent()){
			return ResponseEntity.badRequest().body("Telefone já em uso");
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

	@GetMapping("/verificar-tel")
	public ResponseEntity<Map<String, Boolean>> verificarTelefone(@RequestParam String telefone) {
		try {
			Optional<Usuario> usuario = dao.findByTelefone(telefone);
	
			Map<String, Boolean> resposta = new HashMap<>();
			if (usuario.isPresent()) {
				resposta.put("telefoneValido", false);
			} 
			else {
				resposta.put("telefoneValido", true);
			}
	
			return ResponseEntity.ok(resposta);
		} catch (Exception e) {
			Map<String, Boolean> erro = new HashMap<>();
			erro.put("telefoneValido", false);
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

	@PostMapping("/verificar-senha")
	public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
		String email = loginData.get("email");
		String senha = loginData.get("senha");

		Optional<Usuario> usuarioOptional = dao.findByEmailIgnoreCase(email);

		if (usuarioOptional.isPresent()) {
			Usuario usuario = usuarioOptional.get();

			if (usuario.getSenha().equals(senha)) {
				return ResponseEntity.ok(usuario);
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Senha incorreta");
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email não encontrado");
		}
	}


	@PutMapping("/editar/{id}")
	public ResponseEntity<?> editarUsuario(@PathVariable Integer id, @RequestBody Usuario usuario) {
    try {
        Optional<Usuario> usuarioExistente = dao.findById(id);
        if (usuarioExistente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }

        Optional<Usuario> emailExistente = dao.findByEmailIgnoreCase(usuario.getEmail());
        if (emailExistente.isPresent() && !emailExistente.get().getId().equals(id)) {
            return ResponseEntity.badRequest().body("Email já está em uso");
        }

        Optional<Usuario> usernameExistente = dao.findByUsernameIgnoreCase(usuario.getUsername());
        if (usernameExistente.isPresent() && !usernameExistente.get().getId().equals(id)) {
            return ResponseEntity.badRequest().body("Username já está em uso");
        }

        Optional<Usuario> telefoneExistente = dao.findByTelefone(usuario.getTelefone());
        if (telefoneExistente.isPresent() && !telefoneExistente.get().getId().equals(id)) {
            return ResponseEntity.badRequest().body("Telefone já está em uso");
        }

        usuario.setId(id);
        Usuario usuarioAtualizado = dao.save(usuario);
        return ResponseEntity.ok(usuarioAtualizado);
        
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                           .body("Erro ao atualizar usuário: " + e.getMessage());
    }
}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluirUsuario (@PathVariable Integer id){
		dao.deleteById(id);
		return ResponseEntity.status(204).build();
	}
}