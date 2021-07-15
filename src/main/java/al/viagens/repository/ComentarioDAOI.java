package al.viagens.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import al.viagens.model.Comentario;

@Repository
public interface ComentarioDAOI extends JpaRepository<Comentario, Long> {
	
	@Query("select p from Comentario p where p.postagem.id=:id_postagem")
	List<Comentario> findByPostagem(@Param("id_postagem") Long id);
	
	@Query("select p from Comentario p where p.autor.id=:id_autor")
	List<Comentario> findByAutor(@Param("id_autor") Long id);
}
