package al.viagens.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import al.viagens.model.User;

@Repository
public interface UserDAOI extends JpaRepository<User, Long> {

	@Query("select u from User u where u.login=:login and u.senha=:senha")
	public User findUser(String login, String senha);

	
	public User findByLogin(String login);
}
