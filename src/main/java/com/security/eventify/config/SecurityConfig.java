package com.security.eventify.config;

import com.security.eventify.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.core.userdetails.UserDetailsService;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final TokenService tokenService;
    private final UserDetailsService userDetailsService;

    private final JwtAuthenticationEntryPoint authenticationEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        TokenAuthenticationFilter tokenFilter = new TokenAuthenticationFilter(tokenService, (com.security.eventify.service.implementation.CustomUserDetailsService) userDetailsService);
        boolean useTokenAuth = true;

        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(ex -> ex
                                .authenticationEntryPoint(authenticationEntryPoint)   // 401
                                .accessDeniedHandler(accessDeniedHandler)             // 403
                )
                .authenticationProvider(customAuthenticationProvider)
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/api/public/**").permitAll()
                    .requestMatchers("/api/user/**").hasAnyRole("USER", "ORGANIZER", "ADMIN")
                    .requestMatchers("/api/organizer/**").hasRole("ORGANIZER")
                    .requestMatchers("/api/admin/**").hasRole("ADMIN")
                    .anyRequest().authenticated()
                );
                
        if (useTokenAuth) {
        http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
        } else {
            http.httpBasic(withDefaults());
        }
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
