package al.viagens.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import al.viagens.model.Cliente;
import al.viagens.model.Comentario;
import al.viagens.model.Postagem;
import al.viagens.model.User;
import al.viagens.repository.ClienteDAOI;
import al.viagens.repository.ComentarioDAOI;
import al.viagens.repository.PostagemDAOI;

@Controller
public class ComentarioController {
	
	@Autowired
	ComentarioDAOI comentarioDAOI;
	
	@Autowired
	PostagemDAOI postagemDAOI;
	
	@Autowired
	ClienteDAOI clienteDAOI;
	
	@RequestMapping("/postagens/pagina/{postagemId}/comentario/adicionar")
	public String adicionarComentario(@PathVariable("postagemId") Long postagemId, Model model, 
			@ModelAttribute("comentarioForm") Comentario comentarioForm, HttpServletRequest request) {
		
		Optional<Postagem> postagem = postagemDAOI.findById(postagemId);
		User user = (User)request.getSession().getAttribute("userLogado");
		if(user != null) {
			Optional<Cliente> autor = clienteDAOI.findByUser_id(user.getId());
			comentarioForm.setPostagem(postagem.get());
			comentarioForm.setAutor(autor.get());
			
			comentarioDAOI.save(comentarioForm);
			return "redirect:/postagens/pagina/" + postagemId;
		}else {
			return "redirect:/users/login";
		}
		
		
	}
}
