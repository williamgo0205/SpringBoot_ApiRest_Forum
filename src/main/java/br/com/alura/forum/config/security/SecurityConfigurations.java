package br.com.alura.forum.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.alura.forum.repository.UsuarioRepository;

@EnableWebSecurity
@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private AutenticacaoService autenticacaoService;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	// Configurações de autenticação
	@Override 
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(autenticacaoService).passwordEncoder(new BCryptPasswordEncoder()); // encripita��o da senha
	}
	
	// Configura��es de Autoriza��o (URL's do Projeto)
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Libera acesso requisição nesse caso é�todo GET do /topicos (Lista e detalhes do t�pico)
		http.authorizeRequests()
		  .antMatchers(HttpMethod.GET, "/topicos").permitAll() 
		  .antMatchers(HttpMethod.GET, "/topicos/*").permitAll()
		  .antMatchers(HttpMethod.POST, "/auth").permitAll()
		  .antMatchers(HttpMethod.GET, "/actuator/**").permitAll()
		  .anyRequest().authenticated() // Somente user autenticados
		  //.and().formLogin() // Formulário padrão de Login
		  .and().csrf().disable() //Autenticação via token
		  .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Não cria sessão utiliza token de maneira stateless, mas n�o tem mais formul�rio
		  .and().addFilterBefore(new AutenticacaoViaTokenFilter(tokenService, usuarioRepository), UsernamePasswordAuthenticationFilter.class); // Adicionano filtro para a autentica��o do token 
	}
	
	// Configurações de Recusros Estáticos (Js, css, imagens)
	@Override
    public void configure(WebSecurity web) {
        web.ignoring()
            .antMatchers("/**.html", "/v2/api-docs", "/webjars/**", "/configuration/**", "/swagger-resources/**");
    }

	
	// Gera senha cripitografada
//	public static void main(String[] args) {
//		System.out.println(new BCryptPasswordEncoder().encode("123456"));
//	}
	
}
