
package com.info.email.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
        HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable())
           .authorizeHttpRequests(auth -> auth
           .requestMatchers("/login").permitAll()
           .requestMatchers("/usuarios/**")
           .hasRole("ADMIN")
           .requestMatchers("/mensagens/**")
           .hasAnyRole("ADMIN", "USER")
           .anyRequest()
           .authenticated()
        )

        .formLogin(form -> form
            .loginPage("/login")
            .defaultSuccessUrl("/redirecionar", true)
            .permitAll()
        )

            .logout(logout -> logout
            .logoutSuccessUrl("/login?logout")
        );

    return http.build();
    }
}
