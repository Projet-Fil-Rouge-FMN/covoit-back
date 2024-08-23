package covoit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import covoit.repository.UserAccountRepository;

@Configuration
public class SecurityConfig {
	@Bean
	public SecurityFilterChain apiSecurity(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((request) -> request.requestMatchers("/user/", "/user/register").permitAll()
				.requestMatchers("/user/{id}").hasRole("USER").requestMatchers("/user/delete/**").hasRole("ADMIN")
				.anyRequest().authenticated()).httpBasic(Customizer.withDefaults());

		http.csrf(csrf -> csrf.disable());
		return http.build();
	}

	//Creation UserDetail temporaire
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
	UserDetailsService userDetailsService(UserAccountRepository userAccountRepository) {
		
		return username -> userAccountRepository.findByUserName(username).asUserDetails();

	}

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
