<%@page import="al.viagens.model.Postagem"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${postagem.titulo}</title>
<link rel="stylesheet" href="../resources/css/paginaPostagem.css">
<script src="https://kit.fontawesome.com/27131278a4.js" crossorigin="anonymous"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
</head>
<body>
<header>
            <nav class="navbar navbar-expand-lg navbar-light bg-light shadow-lg" id="nav_header">
                <div class="container-fluid">
                  <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                  </button>
                  <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                      <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="/">Inicio</a>
                      </li>
                      <li class="nav-item">
                        <a class="nav-link" href="/postagens/">Pacotes</a>
                      </li>
                      <li class="nav-item">
                        <a class="nav-link" href="#">Contato</a>
                      </li>
                      <li class="dropdown">
                            <button class="btn dropdown-toggle" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                            <i class="fas fa-sign-in-alt"></i>
                            </button>
                            <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                            <% if(request.getSession().getAttribute("userLogado") != null){ %>
                                	<li><a class="dropdown-item" href="/users/home">Home</a></li>
                                	<li><a class="dropdown-item" href="/users/logout">Logout</a></li>
                                <%}else{%>
                                	<li><a class="dropdown-item" href="/users/login">Login</a></li>
                                	<li><a class="dropdown-item" href="/users/registrar">Criar conta</a></li>
                            <%}%>
                            </ul>
                        </li> 
                    </ul>
                    <form class="d-flex">
                      <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
                      <button class="btn btn-outline-success" type="submit">Search</button>
                    </form>
                  </div>
                </div>
              </nav>
        </header>
        <main>
        <section id="form_container">
			<h1>${postagem.titulo}</h1>
			<p>${postagem.corpo}</p>
				
				
			<h2>Adicionar um comentário</h2>
			<form:form method="POST" modelAttribute="comentarioForm" action="/postagens/pagina/${postagem.id}/comentario/adicionar">
			
		        
				<label for="corpo">Corpo:</label>
		        <form:input path="corpo" cssClass="form-control"/>
		        
		        
		        <input type="submit" value="Adicionar" class="btn btn-primary mt-3">
		         
				
			</form:form>
			
			<h2>Comentarios</h2>
			<c:forEach var="comentario" items="${comentarios}">
				<h4>Autor: ${comentario.autor.nome}</h4>
				<h4>${comentario.corpo}</h4>
			</c:forEach>
		</section>
	</main>
</body>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js" integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf" crossorigin="anonymous"></script>

</html>