package com.belvi.secure_notes.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
/**
 * This class configures the security settings for the application.
 * It defines the security filter chain and customizes the HTTP security features.
 */
public class SecurityConfig {

    /**
     * Configures the default security filter chain.
     *
     * @param http the {@link HttpSecurity} object used to customize security settings
     * @return the configured {@link SecurityFilterChain}
     * @throws Exception if an error occurs while building the security filter chain
     */
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        // Configures authorization rules to ensure all incoming requests are authenticated
        http.authorizeHttpRequests((requests)
                -> requests.anyRequest().authenticated());

        // Disables Cross-Site Request Forgery (CSRF) protection.
        // This is often used in stateless applications or APIs where CSRF protection is not needed.
        http.csrf(AbstractHttpConfigurer::disable);

        // Enables basic HTTP authentication (username and password in the request header).
        http.httpBasic(withDefaults());

        // Optionally, you can enable form-based login by uncommenting the line below:
        // http.formLogin(withDefaults());

        // Builds and returns the configured SecurityFilterChain instance
        return http.build();
    }
}
