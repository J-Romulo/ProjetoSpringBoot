package al.viagens.model;

import java.sql.Blob;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

import org.hibernate.validator.constraints.br.CPF;


@Entity
@Table(name = "clientes")
public class Cliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Nome não pode estar vazio!")
	private String nome;
	
	@NotBlank(message = "CPF não pode estar vazio!")
	@CPF(message = "CPF inválido!")
	private String cpf;
	
	@OneToMany(mappedBy = "autor")
	List<Comentario> comentarios;
	
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@NotBlank(message = "Email não pode estar vazio!")
	@Email(message = "Email inválido!")
	private String email;
	
	@NotBlank(message = "Sua data de nascimento não pode estar vazia!")
	private String nascimento;
	
	@NotBlank(message = "Telefone não pode estar vazio!")
	private String telefone;
	
	@Lob
	private String img_perfil;
	
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	public String getNascimento() {
		return nascimento;
	}
	public void setNascimento(String nascimento) {
		this.nascimento = nascimento;
	}
	
	
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	
	public String getImg_perfil() {
		return img_perfil;
	}
	public void setImg_perfil(String img_perfil) {
		this.img_perfil = img_perfil;
	}
	
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
	public List<Comentario> getComentarios() {
		return comentarios;
	}
	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
}
