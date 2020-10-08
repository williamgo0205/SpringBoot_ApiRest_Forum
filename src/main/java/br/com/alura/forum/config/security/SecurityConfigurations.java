package br.com.alura.forum.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter{
	
	// Configura��es de autentica��o
	@Override 
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	}
	
	// Configura��es de Autoriza��o (URL's do Projeto)
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Libera acesso � requisi��o nesse caso M�todo GET do /topicos (Lista e detalhes do t�pico)
		http.authorizeRequests()
		  .antMatchers(HttpMethod.GET, "/topicos").permitAll() 
		  .antMatchers(HttpMethod.GET, "/topicos/*").permitAll();
	}
	
	// Configura��es de Recusros Est�ticos (Js, css, imagens)
	@Override
	public void configure(WebSecurity web) {
	}
	
}
