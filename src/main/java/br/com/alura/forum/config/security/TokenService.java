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
		Usuario logado = (Usuario) authentication.getPrincipal();	// Pega usuário logado
		
		Date hoje = new Date(); // Data de geração do token
		
		Date dataExpiracao = new Date(hoje.getTime() + Long.parseLong(expiration));
		
		return Jwts.builder()
				.setIssuer("API do Fórum da Alura") // quem gerou o token
				.setSubject(logado.getId().toString()) // Usuário do token
				.setIssuedAt(hoje) //Qual data de geração do token
				.setExpiration(dataExpiracao) // Tempo de expiração
				.signWith(SignatureAlgorithm.HS256, secret) // gera o algorítimo com a senha definida no properties
				.compact(); // Manda criar 
	}

}
