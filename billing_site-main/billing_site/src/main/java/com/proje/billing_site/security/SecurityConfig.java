package com.proje.billing_site.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                // Swagger için izinler
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

                // Admin için
                .requestMatchers("/api/payments/create/**").hasAuthority("ADMIN")
                .requestMatchers("/api/users/**").hasRole("ADMIN")

                // Kullanıcı ve admin için
                .requestMatchers("/api/payments/user/**").hasAnyRole("USER", "ADMIN")

                // Genel erişim izni
                .requestMatchers("/api/auth/**").permitAll()

                // Diğer tüm istekler
                .anyRequest().authenticated()

                .and()
                // Stateless oturum yönetimi
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .formLogin().disable()
                .logout().disable();

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}








