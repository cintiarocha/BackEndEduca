package com.educa.mais.mais.projetointegrador.controller;

import java.util.List;

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
import com.educa.mais.mais.projetointegrador.model.Parceiro;
import com.educa.mais.mais.projetointegrador.model.Usuario;
import com.educa.mais.mais.projetointegrador.repository.CursoRepository;
import com.educa.mais.mais.projetointegrador.repository.ParceiroRepository;
import com.educa.mais.mais.projetointegrador.repository.UsuarioRepository;

@RestController
@RequestMapping ("/curso")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CursoController {

	@Autowired	
	private CursoRepository cursoRepository;
	@Autowired	
	private UsuarioRepository usuarioRepository;
	@Autowired	
	private ParceiroRepository parceiroRepository;
	
	@GetMapping("/{id}")
	public ResponseEntity<Curso> getById(@PathVariable long id) {
		return cursoRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
									       .orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Curso>> getByNome(@PathVariable String titulo) {
		return ResponseEntity.ok(cursoRepository.findByTituloContainingIgnoreCase(titulo));
	}

	@GetMapping("/usuario/{email}")
	public ResponseEntity<List<Usuario>> getByUsuario(@PathVariable Curso curso) {
		return ResponseEntity.ok(usuarioRepository.findByCurso(curso));
	}
	
	@GetMapping("/parceiro/{curso}")
	public ResponseEntity<List<Parceiro>> getByParceiro(@PathVariable Curso curso) {
		return ResponseEntity.ok(parceiroRepository.findByCurso(curso));
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Curso>> getAll() {
		return ResponseEntity.ok(cursoRepository.findAll());
	}
	
	@PostMapping("/cadastrar")
	public ResponseEntity<Curso> post(@Valid @RequestBody Curso curso) {
		return ResponseEntity.status(HttpStatus.CREATED).body(cursoRepository.save(curso));
	}
	
	@PutMapping("/editar")
	public ResponseEntity<Curso> put(@Valid @RequestBody Curso curso) {
		return ResponseEntity.status(HttpStatus.OK).body(cursoRepository.save(curso));
	}
	
	@DeleteMapping ("/{id}")
	public void delete (@PathVariable long id) {
		cursoRepository.deleteById(id);
	}
 
}
