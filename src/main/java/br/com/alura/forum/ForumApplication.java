package br.com.alura.forum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication       // Projeto Spring
@EnableSpringDataWebSupport  // Habilita Busca as informa��es de pagina��o
@EnableCaching               // Habilitar Cache no projeto
@EnableSwagger2              // Habilitar o swagger no projeto
public class ForumApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForumApplication.class, args);
	}

}
