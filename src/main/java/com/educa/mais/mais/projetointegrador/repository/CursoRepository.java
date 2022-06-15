package com.educa.mais.mais.projetointegrador.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.educa.mais.mais.projetointegrador.model.Curso;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
	
	public List<Curso> findByParceiro(String emailParceiro); // get retorna uma lista de cursos comprados por parceiro
	
	public List<Curso> findByUsuario(String emailUsuario); // get retorna uma lista de aluno cadastrados no curso
	
	public List<Curso> findByTituloContainingIgnoreCase(String tituloCurso); //get retorna curso por titulo

	public List<Curso> findAllById(Long id);
	
}
