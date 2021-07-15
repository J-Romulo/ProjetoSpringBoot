package al.viagens.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import al.viagens.model.Cliente;
import al.viagens.model.Comentario;
import al.viagens.model.Perfil;
import al.viagens.model.Postagem;
import al.viagens.model.User;
import al.viagens.repository.ClienteDAOI;
import al.viagens.repository.PerfilDAOI;
import al.viagens.repository.UserDAOI;

@Controller
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	UserDAOI userDAOI;
	
	@Autowired
	PerfilDAOI perfilDAOI;
	
	@Autowired
	ClienteDAOI clienteDAOI;
	
	@RequestMapping("/login")
	public ModelAndView login() {
		 
		User userForm = new User();
		ModelAndView mv= new ModelAndView();
		mv.addObject("userForm", userForm);
		mv.setViewName("paginaLogin");
		
		return mv;
	}
	
	 
	@RequestMapping("/efetuarlogin")
	public ModelAndView efetuarlogin(@Valid @ModelAttribute("userForm") User userForm , BindingResult bindingResults
			, HttpServletRequest request) {
		 
		
		ModelAndView mv= new ModelAndView();
		
		if (bindingResults.hasErrors()){
			System.out.println(bindingResults.getAllErrors());
			mv.setViewName("paginaLogin");
		}else {
			
		User user = userDAOI.findUser(userForm.getLogin(), userForm.getSenha());
		
		if (user!=null && user.getId()>0) {
		
			request.getSession().setAttribute("userLogado", user);
			mv.setViewName("index");
		}
		
		else{
			mv.addObject("errors", "Usuário ou Senha inválido");
			mv.setViewName("paginaLogin");
			
		}
		
		}
		return mv;
	}
	
	@RequestMapping("/registrar")
	public ModelAndView registro() {
		User userForm = new User();
		ModelAndView view = new ModelAndView();
		
		view.addObject("userForm" ,userForm);
		view.setViewName("paginaRegistrar");
		return view;
	}
	
	@RequestMapping("/efetuarRegistro")
	public ModelAndView efetuarRegistro(@Valid @ModelAttribute("userForm") User userForm , BindingResult bindingResults
			, HttpServletRequest request) {
		
		ModelAndView view= new ModelAndView();
		
		if (bindingResults.hasErrors()){
			view.setViewName("paginaRegistrar");
		}else {
			
			if(userDAOI.findByLogin(userForm.getLogin()) == null) {
				Optional<Perfil> perfil = perfilDAOI.findById((long) 2);
				List<Perfil> perfis = new ArrayList<Perfil>();
				perfis.add(perfil.get());
				userForm.setPerfis(perfis);
				User userRegistrado = userDAOI.save(userForm);
				
				request.getSession().setAttribute("userCriado", userRegistrado);
				Cliente novoCliente = new Cliente();
				view.addObject("clienteForm" , novoCliente);
				view.setViewName("novoCliente");
			}else {
				view.addObject("errors", "Login já existente");
				view.setViewName("paginaRegistrar");
			}
			
		
		}
		
		return view;
	}
	
	@RequestMapping("/logout")
	public ModelAndView efetuarlogout(HttpServletRequest request){
		
		ModelAndView mv= new ModelAndView();
		mv.setViewName("paginaLogin");
		mv.addObject("userForm", new User());
		request.getSession().invalidate();
		
		return mv;
		
	}
	
	@RequestMapping("/home")
	public ModelAndView homeUser(HttpServletRequest request) {
		ModelAndView mv= new ModelAndView();
		User user = (User)request.getSession().getAttribute("userLogado");
		if(user != null) {
			List<Perfil> perfis =  user.getPerfis();
			if(perfis.get(0).getDescricao().equalsIgnoreCase("ADMIN")) {
				mv.setViewName("homeAdmin");
				return mv;
			}else {
				Optional<Cliente> cliente = clienteDAOI.findByUser_id(user.getId());
				mv.addObject("cliente", cliente.get());
				mv.addObject("user", user);
				mv.setViewName("homeCliente");
				return mv;
			}
		}else {
			mv.setViewName("paginaLogin");
			mv.addObject("userForm", new User());
			return mv;
		}
		
	}
	
	@RequestMapping("/editar/{id}")
	public ModelAndView editarUser(@PathVariable("id") Long id, Model model, HttpServletRequest request) {
		ModelAndView mv= new ModelAndView();
		Optional<User> userEditado = userDAOI.findById(id);
		User user = (User)request.getSession().getAttribute("userLogado");
		List<Perfil> perfis = user.getPerfis();
		
		if(user.getId() == userEditado.get().getId()) {
			mv.setViewName("editaUser");
			mv.addObject("userForm", userEditado.get());
			return mv;
		}else {
			mv.setViewName("index");
			return mv;
		}
	}
	
	@RequestMapping("/editar2")
	public String editarUser2(@ModelAttribute("userForm") User userForm, Model model, BindingResult bindingResult, 
			HttpServletRequest request) {
		
		if(bindingResult.hasErrors()) {
			System.out.println(bindingResult.getAllErrors());
			return "editaUser";
		}
		userDAOI.save(userForm);
		
		return "redirect:/users/home";
	}
		
}
