package br.com.alura.forum.config.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.alura.forum.model.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {
	
	@Value("${forum.jwt.expiration}")
	private String expiration;
	
	@Value("${forum.jwt.secret}")
	private String secret;

	public String gerarToken(Authentication authentication) {
		Usuario logado = (Usuario) authentication.getPrincipal();	// Pega usu�rio logado
		
		Date hoje = new Date(); // Data de gera��o do token
		
		Date dataExpiracao = new Date(hoje.getTime() + Long.parseLong(expiration));
		
		return Jwts.builder()
				.setIssuer("API do F�rum da Alura") // quem gerou o token
				.setSubject(logado.getId().toString()) // Usu�rio do token
				.setIssuedAt(hoje) //Qual data de gera��o do token
				.setExpiration(dataExpiracao) // Tempo de expira��o
				.signWith(SignatureAlgorithm.HS256, secret) // gera o algor�timo com a senha definida no properties
				.compact(); // Manda criar 
	}

}
