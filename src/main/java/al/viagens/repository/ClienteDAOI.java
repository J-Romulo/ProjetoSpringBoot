package al.viagens.repository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import al.viagens.model.Cliente;
import al.viagens.model.User;

@Repository
public interface ClienteDAOI extends JpaRepository<Cliente, Long> {

	Optional<Cliente> findByUser_id(Long id);

}
