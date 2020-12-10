package br.com.alura.forum.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.alura.forum.model.Usuario;
import br.com.alura.forum.repository.UsuarioRepository;


public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {
	
	private TokenService tokenService;
	
	private UsuarioRepository repository;

	public AutenticacaoViaTokenFilter(TokenService tokenService, UsuarioRepository repository) {
		this.tokenService = tokenService;
		this.repository = repository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// Implementação para receber o token e fazer a autenticação
		
		String token = recuperarToken(request); // Recupera o token
		
		boolean valido = tokenService.isTokenValido(token); // Valida o token

		if(valido) {
			autenticarCliente(token); // autentica o token			
		}
		
		filterChain.doFilter(request, response); 		
	}

	private void autenticarCliente(String token) {
		
		Long idUsuario = tokenService.getIdUsuario(token); // Busca o Id do usuário
		 
		Usuario usuario = repository.findById(idUsuario).get(); // recupera o Usuário 
		
		UsernamePasswordAuthenticationToken authentication = 
				new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities()); // Autentica o Usuário
		
		SecurityContextHolder.getContext().setAuthentication(authentication); // Seta a autorização
	}

	private String recuperarToken(HttpServletRequest request) {		
		String token = request.getHeader("Authorization"); // recupera o token pelo authorization
		
		if(token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;
		}
		
		return token.substring(7, token.length());
	}

}
