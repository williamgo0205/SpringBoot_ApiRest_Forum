package br.com.alura.forum.config.swagger;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.alura.forum.model.Usuario;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfigurations {
	
	// Utilizar as bibliotecas do swagger "springfox-swagge2" e "springfox-swagger-ui" ao pom
	// Anotar a aplicação com @EnableSwagger2 
	// Site de visualização swagger     : https://swagger.io/solutions/api-documentation/
	// site do SpringFox                : https://springfox.github.io/springfox/
	// Endereço local do swagger gerado : http://localhost:8080/swagger-ui.html
	
	@Bean
	// metodo para configuração do swagger 
	public Docket forumApi() {		
		return new Docket(DocumentationType.SWAGGER_2) // Tipo de Documento
		.select()
		.apis(RequestHandlerSelectors.basePackage("br.com.alura.forum"))//Pacote de inicio do projeto
		.paths(PathSelectors.ant("/**")) // Qual os endpoint lidos
		.build()
		.ignoredParameterTypes(Usuario.class) // Ignore todas as url da classe definida
		.globalOperationParameters(Arrays.asList(
				new ParameterBuilder()
				.name("Authorization")
				.description("Header para token JWT")
				.modelRef(new ModelRef("string"))
				.parameterType("header")
				.required(false)
				.build()));
	}
}

