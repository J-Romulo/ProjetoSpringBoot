package al.viagens.controller;
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
import al.viagens.model.User;
import al.viagens.repository.ClienteDAOI;
import al.viagens.repository.ComentarioDAOI;
import al.viagens.repository.UserDAOI;

@RequestMapping("/clientes")
@Controller
public class ClienteController {
	
	@Autowired
	ClienteDAOI clienteDAOI;
	
	@Autowired
	ComentarioDAOI comentarioDAOI;
	
	@Autowired
	UserDAOI userDAOI;
	
	@RequestMapping("/novo")
	public String novo(Long id, Model model) {
		model.addAttribute("clienteForm", new Cliente());
		
		return "novoCliente";
		
	}
	
	@RequestMapping("/adicionar")
	public String adicionarNoBanco(@Valid @ModelAttribute("clienteForm") Cliente clienteForm,BindingResult bindingResult, 
			HttpServletRequest request) {
		
		if(bindingResult.hasErrors()) {
			System.out.println(bindingResult.getAllErrors());
			return "novoCliente";
		}
		User user = (User)request.getSession().getAttribute("userCriado");
		clienteForm.setUser(user);
		clienteDAOI.save(clienteForm);
		
		return "redirect:/";
	}
	
	@RequestMapping(value = {"/all", "/"})
	public ModelAndView allClientes(Model model, HttpServletRequest request){
		User checkLog = (User)request.getSession().getAttribute("userLogado");
		ModelAndView mv= new ModelAndView();
		if(checkLog != null) {
			List<Cliente> clientes = clienteDAOI.findAll();
			
			mv.setViewName("listaClientes");
			mv.addObject("clientes", clientes);
			
			return mv;
		}else {
			mv.setViewName("index");
			return mv;
		}
	}
	
	@RequestMapping("/excluir/{id}")
	public String excluirCliente(@PathVariable("id") Long id, Model model, HttpServletRequest request) {
		User checkLog = (User)request.getSession().getAttribute("userLogado");
		if(checkLog != null) {
			Optional<Cliente> cliente = clienteDAOI.findById(id);
			List<Comentario> comentarios = comentarioDAOI.findByAutor(id);
			User user = cliente.get().getUser();
			if(!cliente.isEmpty()) {
				comentarioDAOI.deleteAll(comentarios);
				clienteDAOI.delete(cliente.get());
				userDAOI.delete(user);
			}

			return "redirect:/clientes/all";
		}else {
			return "redirect:/users/login";
		}
	}
	
	@RequestMapping("/editar/{id}")
	public ModelAndView editarCliente(@PathVariable("id") Long id, Model model, HttpServletRequest request) {
		ModelAndView mv= new ModelAndView();
		Optional<Cliente> cliente = clienteDAOI.findById(id);
		User user = (User)request.getSession().getAttribute("userLogado");
		List<Perfil> perfis = user.getPerfis();
		
		Optional<Cliente> editor = clienteDAOI.findByUser_id(user.getId());

		if(perfis.get(0).getDescricao().equalsIgnoreCase("ADMIN")) {
			if(!cliente.isEmpty()) {
				mv.setViewName("editaCliente");
				mv.addObject("clienteForm", cliente.get());
				return mv;
			}else {
				List<Cliente> clientes = clienteDAOI.findAll();
				
				mv.setViewName("listaClientes");
				mv.addObject("clientes", clientes);
				return mv;
			}
			
		}else{
			if(editor.get().getId( )!= cliente.get().getId()) {
				mv.setViewName("index");
				return mv;
			}else {
				mv.setViewName("editaCliente");
				mv.addObject("clienteForm", cliente.get());
				return mv;
			}
		}
		
	}
	
	@RequestMapping("/editar2")
	public String editarCliente2(@ModelAttribute("clienteForm") Cliente clienteForm, Model model, BindingResult bindingResult, 
			HttpServletRequest request) {
		Optional<User> user = userDAOI.findById(clienteForm.getUser().getId());
		if(bindingResult.hasErrors()) {
			System.out.println(bindingResult.getAllErrors());
			return "editaCliente";
		}
		clienteDAOI.save(clienteForm);
		
		return "redirect:/clientes/all";
	}
	
	
	
}
