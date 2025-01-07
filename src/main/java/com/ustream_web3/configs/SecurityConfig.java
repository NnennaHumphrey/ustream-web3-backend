package com.ustream_web3.configs;

import com.ustream_web3.services.impls.CustomUsersDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;
@Configuration
public class SecurityConfig {


    private final CustomUsersDetailsService customUserDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(CustomUsersDetailsService customUserDetailsService, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/api/auth/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/login/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/email/verify-otp/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/email/resend-otp/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/logout/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/auth/users/all-users").hasRole("ADMIN")

                        // Password reset
                        .requestMatchers(HttpMethod.POST, "/api/password-reset/request/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/password-reset/reset/**").permitAll()

                        // Video-related public endpoints
                        .requestMatchers(HttpMethod.GET, "/api/videos/search/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/videos/all/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/videos/download/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/videos/user/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/leaderboard/**").permitAll()

                        // Comment and like-related public endpoints
                        .requestMatchers(HttpMethod.POST, "/api/comments/add/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/comments/video/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/comments/delete/").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/comments/reply/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/likes/like/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/likes/count/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/likes/unlike/**").permitAll()

                        // Authentication docs
                        .requestMatchers(HttpMethod.GET, "/authentication-docs/**").permitAll()

                        // Authenticated-only endpoints for videos and profile
                        .requestMatchers(HttpMethod.POST, "/api/videos/upload").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/videos/delete/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/videos/watch/**").authenticated()

                        // Authenticated-only endpoints for user profile management
                        .requestMatchers(HttpMethod.PUT, "/api/v1/user/profile/change-username").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/v1/user/profile/change-email").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/v1/user/profile/change-password").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/v1/user/profile/upload-photo").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/v1/user/profile/delete-user").authenticated()

                        .anyRequest().authenticated())
                .authenticationManager(authenticationManager(http.getSharedObject(AuthenticationConfiguration.class)))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(corsFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
