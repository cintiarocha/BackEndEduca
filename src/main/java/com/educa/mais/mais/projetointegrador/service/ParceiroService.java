package com.educa.mais.mais.projetointegrador.service;

import java.nio.charset.Charset;
import java.util.Optional;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.educa.mais.mais.projetointegrador.model.Parceiro;
import com.educa.mais.mais.projetointegrador.model.ParceiroLogin;
import com.educa.mais.mais.projetointegrador.repository.ParceiroRepository;

@Service
public class ParceiroService {
	
	@Autowired
	private ParceiroRepository repository;
	
	public Optional<Parceiro> cadastrar(Parceiro parceiro){
		
		if (repository.findByEmail(parceiro.getEmail()).isPresent())
			return Optional.empty();
		
		parceiro.setSenha(criptografarSenha(parceiro.getSenha()));
		return Optional.of(repository.save(parceiro));	
	}
	
	public Optional<Parceiro> atualizar(Parceiro parceiro){
		
		if (repository.findById(parceiro.getId()).isPresent()) {
			parceiro.setSenha(criptografarSenha(parceiro.getSenha()));
			return Optional.of(repository.save(parceiro));
		}
		return Optional.empty();
	}
	
	public String criptografarSenha(String senha) {
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(senha);
	}
	
	public Optional<ParceiroLogin> autenticar(Optional<ParceiroLogin> parceiroLogin) {
		Optional<Parceiro> parceiro = repository.findByEmail(parceiroLogin.get().getEmail());
	
		if (parceiro.isPresent()) {
			if (compararSenhas(parceiroLogin.get().getSenha(), parceiro.get().getSenha())) {
				
				parceiroLogin.get().setId(parceiro.get().getId());
				parceiroLogin.get().setNome(parceiro.get().getNome());
				parceiroLogin.get().setFoto(parceiro.get().getFoto());
				parceiroLogin.get().setCnpj(parceiro.get().getCnpj());
				parceiroLogin.get().setEmail(parceiro.get().getEmail());
				parceiroLogin.get().setTelefone(parceiro.get().getTelefone());
				parceiroLogin.get().setSenha(parceiro.get().getSenha());
				parceiroLogin.get().setToken(gerarBasicToken(parceiroLogin.get().getEmail(), parceiroLogin.get().getSenha()));
				return parceiroLogin;	
			}
		}
		return Optional.empty();
	}
	
	private boolean compararSenhas(String senhaDigitada, String senhaBanco) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.matches(senhaDigitada, senhaBanco);
	}
	
	private String gerarBasicToken(String usuario, String senha) {

		String token = usuario + ":" + senha;
		byte[] tokenBase64 = Base64.encodeBase64(token.getBytes(Charset.forName("US-ASCII")));
		return "Basic " + new String(tokenBase64);
	}
	
}