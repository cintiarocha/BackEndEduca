package com.educa.mais.mais.projetointegrador.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RestController;

import com.educa.mais.mais.projetointegrador.model.Curso;
import com.educa.mais.mais.projetointegrador.model.Usuario;
import com.educa.mais.mais.projetointegrador.model.UsuarioLogin;
import com.educa.mais.mais.projetointegrador.repository.CursoRepository;
import com.educa.mais.mais.projetointegrador.repository.UsuarioRepository;
import com.educa.mais.mais.projetointegrador.service.UsuarioService;

@RestController
@RequestMapping ("/usuarios")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private CursoRepository cursoRepository;
	@Autowired
	private UsuarioService service;
	
	@GetMapping("/{id}")
	public ResponseEntity<Usuario> getById(@PathVariable long id) {
		return usuarioRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
											 .orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/email/{email}")
	public ResponseEntity<Usuario> getByUsuario(@PathVariable String email) {
		return usuarioRepository.findByUsuario(email).map(resposta -> ResponseEntity.ok(resposta))
													 .orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Usuario>> getByNome(@PathVariable String nome) {
		return ResponseEntity.ok(usuarioRepository.findByNomeContainingIgnoreCase(nome));
	}
	
	@GetMapping("/curso/{email}")
	public ResponseEntity<List<Curso>> getByCurso(@PathVariable String usuario) {
		Long id = usuarioRepository.findByUsuario(usuario).get().getId();
		return ResponseEntity.ok(cursoRepository.findAllById(id));
	}

	@GetMapping("/all")
	public ResponseEntity<List<Usuario>> getAll() {
		return ResponseEntity.ok(usuarioRepository.findAll());
	}

	@PostMapping("/logar")
	public ResponseEntity<UsuarioLogin> login(@RequestBody Optional<UsuarioLogin> user) {
		return service.autenticarUsuario(user).map(resposta -> ResponseEntity.ok(resposta))
											  .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}

	@PostMapping("/cadastrar")
	public ResponseEntity<Usuario> post(@Valid @RequestBody Usuario usuario) {
		return service.cadastrarUsuario(usuario).map(resposta -> ResponseEntity.status(HttpStatus.CREATED).body(resposta))
												.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
	}
	
	@PutMapping("/editar")
	public ResponseEntity<Usuario> put(@Valid @RequestBody Usuario usuario) {
		return ResponseEntity.status(HttpStatus.OK).body(usuarioRepository.save(usuario));
	}
	
	@DeleteMapping("/{id}")
	public void delete (@PathVariable long id) {
		usuarioRepository.deleteById(id);
	}
}
