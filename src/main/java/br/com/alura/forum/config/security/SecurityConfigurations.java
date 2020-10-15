package br.com.alura.forum.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private AutenticacaoService autenticacaoService;
	
	// Configura��es de autentica��o
	@Override 
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(autenticacaoService).passwordEncoder(new BCryptPasswordEncoder()); // encripita��o da senha
	}
	
	// Configura��es de Autoriza��o (URL's do Projeto)
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Libera acesso � requisi��o nesse caso M�todo GET do /topicos (Lista e detalhes do t�pico)
		http.authorizeRequests()
		  .antMatchers(HttpMethod.GET, "/topicos").permitAll() 
		  .antMatchers(HttpMethod.GET, "/topicos/*").permitAll()
		  .antMatchers(HttpMethod.POST, "/auth").permitAll()
		  .anyRequest().authenticated() // Somente user autenticados
		  //.and().formLogin() // Formul�rio padr�o de Login
		  .and().csrf().disable() //Autentica��o via token
		  .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // N�o cria sess�o e utiliza token de maneira stateless, mas n�o tem mais formul�rio
		  ;
	}
	
	// Configura��es de Recusros Est�ticos (Js, css, imagens)
	@Override
	public void configure(WebSecurity web) {
		
	}
	
	// Gera senha cripitografada
//	public static void main(String[] args) {
//		System.out.println(new BCryptPasswordEncoder().encode("123456"));
//	}
	
}
