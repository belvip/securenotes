package com.belvi.secure_notes.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> requests.anyRequest().authenticated());
        http.csrf(AbstractHttpConfigurer::disable);
        http.httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);

        // Override the default SQL queries to match your schema
        manager.setUsersByUsernameQuery(
                "SELECT username, password, enabled, account_non_expired, account_non_locked, credentials_non_expired " +
                        "FROM users WHERE username = ?"
        );
        manager.setCreateUserSql(
                "INSERT INTO users (username, password, enabled, account_non_expired, account_non_locked, credentials_non_expired) " +
                        "VALUES (?, ?, ?, ?, ?, ?)"
        );
        manager.setUpdateUserSql(
                "UPDATE users SET password = ?, enabled = ?, account_non_expired = ?, account_non_locked = ?, credentials_non_expired = ? " +
                        "WHERE username = ?"
        );
        manager.setDeleteUserSql("DELETE FROM users WHERE username = ?");
        manager.setCreateAuthoritySql("INSERT INTO authorities (username, authority) VALUES (?, ?)");
        manager.setDeleteUserAuthoritiesSql("DELETE FROM authorities WHERE username = ?");
        manager.setAuthoritiesByUsernameQuery("SELECT username, authority FROM authorities WHERE username = ?");

        // Check if the "user1" already exists, if not, create it
        if (!manager.userExists("user1")) {
            manager.createUser(
                    User.withUsername("user1")               // Define username
                            .password("{noop}password1")    // Set password (noop means no encryption)
                            .roles("USER")                   // Assign "USER" role
                            .accountExpired(false)           // Set account_non_expired to false
                            .accountLocked(false)            // Set account_non_locked to false
                            .credentialsExpired(false)       // Set credentials_non_expired to false
                            .disabled(false)                 // Set enabled to true
                            .build()                         // Build the user details
            );
        }

        // Check if the "admin" already exists, if not, create it
        if (!manager.userExists("admin")) {
            manager.createUser(
                    User.withUsername("admin")              // Define username
                            .password("{noop}adminPass")   // Set password (noop means no encryption)
                            .roles("ADMIN")                  // Assign "ADMIN" role
                            .accountExpired(false)           // Set account_non_expired to false
                            .accountLocked(false)            // Set account_non_locked to false
                            .credentialsExpired(false)       // Set credentials_non_expired to false
                            .disabled(false)                 // Set enabled to true
                            .build()                         // Build the user details
            );
        }

        return manager;
    }
}