package covoit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import covoit.entities.UserAccount;
import covoit.repository.UserAccountRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig implements WebMvcConfigurer {
	@Bean
	public SecurityFilterChain apiSecurity(HttpSecurity http) throws Exception {
	    HttpSessionSecurityContextRepository repo = new HttpSessionSecurityContextRepository();
	    http.authorizeHttpRequests(authorize -> authorize
	        .requestMatchers("/user/", "/user/register", "auth/**", "/**", "/swagger-ui/")
	        .permitAll()
	        .requestMatchers("/user/{id}")
	        .hasRole("USER")
	        .requestMatchers("/**", "/user/delete/**")
	        .hasRole("ADMIN")
	        .anyRequest().authenticated())
	    .httpBasic(Customizer.withDefaults())
	    .securityContext((context -> context.securityContextRepository(repo)))
	    .csrf(csrf -> csrf.disable());

	    //http.options("/**").permitAll();

	    return http.build();
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
	    return new WebMvcConfigurer() {
	        @Override
	        public void addCorsMappings(CorsRegistry registry) {
	            registry.addMapping("/**")
	                    .allowedOrigins("http://localhost:4200")
	                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
	                    .allowCredentials(true)
	                    .allowedHeaders("*")
	                    .allowedHeaders("Authotization");
	        }
	    };
	}
//	 @Bean
//	    public CorsFilter corsFilter() {
//	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//	        CorsConfiguration config = new CorsConfiguration();
//	        config.setAllowCredentials(true);
//	        config.addAllowedOrigin("*");
//	        config.addAllowedHeader("*");
//	        config.addAllowedMethod("*");
//	        source.registerCorsConfiguration("/**", config);
//	        return new CorsFilter(source);
//	    }
//	
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
				throw new BadCredentialsException("Wrong user or password");
			}
			return userAccount.asUserDetails(); // Assuming `asUserDetails()` converts `UserAccount` to `UserDetails`
		};
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}