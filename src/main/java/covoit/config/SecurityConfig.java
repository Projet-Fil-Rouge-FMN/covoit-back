package covoit.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import covoit.entities.UserAccount;
import covoit.repository.UserAccountRepository;
import covoit.services.CustomUserDetailsService;
import covoit.services.JwtService;

@Configuration
@EnableWebSecurity
public class SecurityConfig implements WebMvcConfigurer {
	@Bean
	public SecurityFilterChain apiSecurity(HttpSecurity http) throws Exception {
	    HttpSessionSecurityContextRepository repo = new HttpSessionSecurityContextRepository();

	    http
	        .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Application de la configuration CORS
	        .authorizeHttpRequests(authorize -> authorize
	            .requestMatchers("/user/", "/user/register", "auth/**", "/**", "/swagger-ui/")
	            .permitAll()
	            .requestMatchers("/user/{id}")
	            .hasRole("USER")
	            .requestMatchers("/**", "/user/delete/**")
	            .hasRole("ADMIN")
	            .anyRequest().authenticated())
	        .httpBasic(Customizer.withDefaults())
	        .securityContext((context -> context.securityContextRepository(repo)))
	        .csrf(csrf -> csrf.disable())
	        
	        .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
	        .addFilterBefore(anonymousAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	    return http.build();
	}
	   // Définition du filtre d'authentification anonyme
    @Bean
    public AnonymousAuthenticationFilter anonymousAuthenticationFilter() {
        return new AnonymousAuthenticationFilter("uniqueKeyForAnonymous", "anonymousUser",
                AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS"));
    }
    @Bean
    public AuthenticationTrustResolver authenticationTrustResolver() {
        return new AuthenticationTrustResolverImpl();
    }
	  @Autowired
	    private AuthenticationConfiguration authenticationConfiguration;
	 @Value("${jwt.secret}")
	    private String secretKey;
    @Autowired
    private JwtService jwtService;
    
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService);
    }
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtService, userDetailsService);
        filter.setAuthenticationManager(authenticationManager()); // Associe le AuthenticationManager au filtre
        return filter;
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
	    CorsConfiguration configuration = new CorsConfiguration();
	    configuration.setAllowedOrigins(List.of("http://localhost:4200","https://projet-fil-rouge-fmn.github.io/"));
	    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
	    configuration.setAllowedHeaders(Arrays.asList("*"));
	    configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
	    configuration.setAllowCredentials(true);
	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", configuration);
	    return source;
	}



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