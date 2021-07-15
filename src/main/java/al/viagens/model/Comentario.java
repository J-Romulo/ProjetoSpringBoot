package al.viagens.model;

import javax.persistence.*;

@Entity
@Table(name = "comentarios")
public class Comentario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String corpo;
	
	@ManyToOne
	@JoinColumn(name = "id_autor")
	private Cliente autor;
	
	@ManyToOne
	@JoinColumn(name = "id_postagem")
	private Postagem postagem;
	
	public Postagem getPostagem() {
		return postagem;
	}
	public void setPostagem(Postagem postagem) {
		this.postagem = postagem;
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	
	public Cliente getAutor() {
		return autor;
	}
	public void setAutor(Cliente autor) {
		this.autor = autor;
	}
	
	
	public String getCorpo() {
		return corpo;
	}
	public void setCorpo(String corpo) {
		this.corpo = corpo;
	}
}
