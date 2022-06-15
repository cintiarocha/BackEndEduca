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
import com.educa.mais.mais.projetointegrador.model.Parceiro;
import com.educa.mais.mais.projetointegrador.model.ParceiroLogin;
import com.educa.mais.mais.projetointegrador.repository.CursoRepository;
import com.educa.mais.mais.projetointegrador.repository.ParceiroRepository;
import com.educa.mais.mais.projetointegrador.service.ParceiroService;

@RestController
@RequestMapping("/parceiros")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ParceiroController {
	
	@Autowired
	private ParceiroRepository parceiroRepository;
	@Autowired
	private CursoRepository cursoRepository;
	@Autowired 
	private ParceiroService service;
	
	@GetMapping("/{id}")
	public ResponseEntity<Parceiro> getById(@PathVariable long id) {
		return parceiroRepository.findById (id).map(resposta -> ResponseEntity.ok(resposta))
									   		   .orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/email/{email}")
	public ResponseEntity<Parceiro> getByEmail(@PathVariable String email) {
		return parceiroRepository.findByEmail(email).map(resposta -> ResponseEntity.ok(resposta))
													.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/cnpj/{cnpj}")
	public ResponseEntity<Parceiro> getByCnpj(@PathVariable String Cnpj) {
		return parceiroRepository.findByCnpj(Cnpj).map(resposta -> ResponseEntity.ok(resposta))
												  .orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Parceiro>> getByNome(@PathVariable String nome) {
		return ResponseEntity.ok(parceiroRepository.findByNomeContainingIgnoreCase(nome));
	}
	
	@GetMapping("/curso/{parceiro}")
	public ResponseEntity<List<Curso>> getByCurso(@PathVariable String emailDoParceiro) {
		return ResponseEntity.ok(cursoRepository.findByParceiro(emailDoParceiro));
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Parceiro>> getAll() {
		return ResponseEntity.ok(parceiroRepository.findAll());
	}
	
	@PostMapping("/logar")
	public ResponseEntity<ParceiroLogin> login(@RequestBody Optional<ParceiroLogin> parceiro) {
		return service.autenticar(parceiro).map(resposta -> ResponseEntity.ok(resposta))
											  .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}

	@PostMapping("/cadastrar")
	public ResponseEntity<Parceiro> post(@Valid @RequestBody Parceiro parceiro) {
		return service.cadastrar(parceiro).map(resposta -> ResponseEntity.status(HttpStatus.CREATED).body(resposta))
												.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
	}
	
	@PostMapping("/comprar")
	public ResponseEntity<Parceiro> post(@Valid @RequestBody Parceiro parceiro, Curso curso) {
		return service.comprar(parceiro, curso).map(resposta -> ResponseEntity.status(HttpStatus.CREATED).body(resposta))
											   .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
	}
	
	@PutMapping
	public ResponseEntity<Parceiro> put(@Valid @RequestBody Parceiro parceiro) {
		return ResponseEntity.status(HttpStatus.OK).body(parceiroRepository.save(parceiro));
	}
	
	@DeleteMapping("/{id}")
	public void delete (@PathVariable long id) {
		parceiroRepository.deleteById(id);
	}
}
