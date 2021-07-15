package al.viagens.repository;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import al.viagens.model.Postagem;

@Repository
public interface PostagemDAOI extends JpaRepository<Postagem, Long> {

}
