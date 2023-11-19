package com.example.restapi;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuth;

    public SecurityConfig(JwtAuthFilter jwtAuth) {
        this.jwtAuth = jwtAuth;
    }

    @Bean
    public static PasswordEncoder passEncod() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(HttpMethod.POST, "api/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "api/auth/signIn").permitAll()
                        .requestMatchers(HttpMethod.GET, "api/auth/hape/poto/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "api/auth/hape/all", "api/auth/hape/edit/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "api/auth/hape/add").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "api/auth/hape/edit/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "api/auth/hape/hapus/**").hasRole("ADMIN")
                ).addFilterBefore(jwtAuth, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
