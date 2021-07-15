package al.viagens.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import al.viagens.model.Comentario;
import al.viagens.model.Perfil;
import al.viagens.model.Postagem;
import al.viagens.model.User;
import al.viagens.repository.ComentarioDAOI;
import al.viagens.repository.PostagemDAOI;

@RequestMapping("/postagens")
@Controller
public class PostagemController {
	@Autowired
	PostagemDAOI postagemDAOI;
	
	@Autowired
	ComentarioDAOI comentarioDAOI;
	
	@RequestMapping("/criar")
	public String novo(Long id, Model model, HttpServletRequest request) {
		User checkLog = (User)request.getSession().getAttribute("userLogado");
		if(checkLog != null) {
			model.addAttribute("postagemForm", new Postagem());
			
			return "novaPostagem";
		}else {
			return "redirect:/users/login";
		}
		
	}
	
	@RequestMapping("/adicionar")
	public String adicionarNoBanco(@ModelAttribute("postagemForm") Postagem form,BindingResult bindingResult, Model model) {
		
		postagemDAOI.save(form);
		return "forward:/postagens/all";
	}
	
	@RequestMapping(value = {"/all", "/"})
	public String todasPostagens(Long id, Model model, HttpServletRequest request) {
		User user = (User)request.getSession().getAttribute("userLogado");
		request.getSession().setAttribute("check", true);
		if(user != null) {
			List<Perfil> perfis = user.getPerfis();
			if(perfis.get(0).getDescricao().equalsIgnoreCase("ADMIN")) {
				request.getSession().setAttribute("admin", true);
			}else {
				request.getSession().setAttribute("admin", false);
			}
			
		}else {
			request.getSession().setAttribute("admin", false);
		}
		
		List<Postagem> listPostagens = postagemDAOI.findAll();
		model.addAttribute("listPostagens", listPostagens);
		
		return "listaPostagens";
	}
	
	@RequestMapping("/excluir/{id}")
	public String excluirPostagem(@PathVariable("id") Long id, Model model, HttpServletRequest request) {
		User checkLog = (User)request.getSession().getAttribute("userLogado");
		if(checkLog != null) {
			Optional<Postagem> postagem = postagemDAOI.findById(id);
			List<Comentario> comentarios = comentarioDAOI.findByPostagem(id);
			
			if(!postagem.isEmpty()) {
				comentarioDAOI.deleteAll(comentarios);
				postagemDAOI.delete(postagem.get());
			}
			
			return "redirect:/postagens/all";
		}else {
			return "redirect:/users/login";
		}	
	}
	
	@RequestMapping("/editar/{id}")
	public String editarPostagem(@PathVariable("id") Long id, Model model, HttpServletRequest request) {
		User checkLog = (User)request.getSession().getAttribute("userLogado");
		if(checkLog != null) {
			Optional<Postagem> postagem = postagemDAOI.findById(id);
			
			if(!postagem.isEmpty()) {
				model.addAttribute("postagemForm", postagem.get());
				return "editaPostagem";
			}
			
			return "redirect:/postagens/all";
		}else {
			return "redirect:/users/login";
		}
	}
	
	@RequestMapping("/editar2")
	public String editarPostagem2(@ModelAttribute("postagemForm") Postagem postagemForm, Model model) {
		postagemDAOI.save(postagemForm);
		
		
		return "redirect:/postagens/all";
	}
	
	@RequestMapping("/pagina/{id}")
	public String mostrarPagina(@PathVariable("id") Long id, Model model) {
		Optional<Postagem> postagem = postagemDAOI.findById(id);
		List<Comentario> comentarios = comentarioDAOI.findByPostagem(id);
		
		if(!postagem.isEmpty()) {
			model.addAttribute("postagem", postagem.get());
			model.addAttribute( "comentarios", comentarios);
			model.addAttribute("comentarioForm", new Comentario());
			return "paginaPostagem";
		}
		
		return "redirect:/postagens/all";
	}
	
}
