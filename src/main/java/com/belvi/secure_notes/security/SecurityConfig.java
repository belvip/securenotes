package com.belvi.secure_notes.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

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

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        // Create an in-memory user details manager
        // InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);

        // Check if the "user1" already exists, if not, create it
        if (!manager.userExists("user1")) {
            manager.createUser(
                    User.withUsername("user1")               // Define username
                            .password("{noop}password1")    // Set password (noop means no encryption)
                            .roles("USER")                   // Assign "USER" role
                            .build()                         // Build the user details
            );
        }

        // Check if the "admin" already exists, if not, create it
        if (!manager.userExists("admin")) {
            manager.createUser(
                    User.withUsername("admin")              // Define username
                            .password("{noop}adminPass")   // Set password (noop means no encryption)
                            .roles("ADMIN")                  // Assign "ADMIN" role
                            .build()                         // Build the user details
            );
        }

        // Return the configured user details service manager
        return manager;
    }

}
