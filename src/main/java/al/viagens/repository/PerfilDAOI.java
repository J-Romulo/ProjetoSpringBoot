package al.viagens.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import al.viagens.model.Perfil;

@Repository
public interface PerfilDAOI extends JpaRepository<Perfil, Long>{
	
}
