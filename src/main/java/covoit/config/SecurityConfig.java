package covoit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import ch.qos.logback.core.Context;
import covoit.entities.UserAccount;
import covoit.repository.UserAccountRepository;

@Configuration
public class SecurityConfig implements WebMvcConfigurer {
	@Bean
	public SecurityFilterChain apiSecurity(HttpSecurity http) throws Exception {
		HttpSessionSecurityContextRepository repo = new HttpSessionSecurityContextRepository();
		http.authorizeHttpRequests(
				(request) -> request.requestMatchers("/user/", "/user/register", "auth/login", "/**", "/swagger-ui/")
						.permitAll().requestMatchers("/user/{id}").hasRole("USER")
						.requestMatchers("/**", "/user/delete/**").hasRole("ADMIN").anyRequest().authenticated())
				.httpBasic(Customizer.withDefaults())
				.securityContext((context -> context.securityContextRepository(repo)));
		// Configurer CSRF avec CookieCsrfTokenRepository et HttpOnly désactivé
        http.csrf(csrf -> csrf
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()));


		//http.csrf(csrf -> csrf.disable());
		return http.build();
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("http://localhost:4200")
						.allowedMethods("GET", "POST", "PUT", "DELETE").allowedHeaders("*");
				// .allowCredentials(true);
			}
		};
	}
	// Creation UserDetail temporaire
//	@Bean
//	public InMemoryUserDetailsManager userDetailsService() {
//		UserDetails user = User.builder().username("user").password(passwordEncoder().encode("user")).roles("USER")
//				.build();
//		UserDetails admin = User.builder().username("admin").password(passwordEncoder().encode("admin")).roles("ADMIN")
//				.build();
//		System.out.println(user.getAuthorities());
//		return new InMemoryUserDetailsManager(user, admin);
//	}

	@Bean
	public UserDetailsService userDetailsService(UserAccountRepository userAccountRepository) {
		return username -> {
			UserAccount userAccount = userAccountRepository.findByUserName(username);
			if (userAccount == null) {
				throw new UsernameNotFoundException("Wrong Username or Password");
			}
			return userAccount.asUserDetails(); // Assuming `asUserDetails()` converts `UserAccount` to `UserDetails`
		};
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
