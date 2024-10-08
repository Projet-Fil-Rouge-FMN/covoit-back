package covoit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI().info(new Info().title("API").version("1.0")
				.description("Cette API ")
				.termsOfService("OPEN DATA")
				.contact(new Contact().name("Frédéric , Noémie, Mohamed").email("").url("URL du contact"))
				.license(new License().name(" Frédéric, Noémie, Mohamed").url("URL de la licence")));
	}
}