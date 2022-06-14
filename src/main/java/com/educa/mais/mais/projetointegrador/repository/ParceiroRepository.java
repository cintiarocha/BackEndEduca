package com.educa.mais.mais.projetointegrador.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.educa.mais.mais.projetointegrador.model.Curso;
import com.educa.mais.mais.projetointegrador.model.Parceiro;

@Repository
public interface ParceiroRepository extends JpaRepository<Parceiro, Long> {
	
	public Optional<Parceiro> findByEmail(String emailParceiro); // get retorna parceiro por email
	
	public Optional<Parceiro> findByCnpj(String cnpj); // get retorna empresa por cnpj
	
	public List<Parceiro> findByNomeContainingIgnoreCase(String nomeEmpresa); // get retorna uma lista de empresa por nome
	
	public List<Parceiro> findByCurso(Curso curso);

}
