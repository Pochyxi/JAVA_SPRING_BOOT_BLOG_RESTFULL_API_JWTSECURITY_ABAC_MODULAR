package com.developez.security;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableMethodSecurity
@SecuritySchemes({
        @SecurityScheme(
                name = "Bear Authentication",
                type = SecuritySchemeType.HTTP,
                bearerFormat = "JWT",
                scheme = "bearer"
        )
})
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    SecurityConfig( UserDetailsService userDetailsService,
                    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                    JwtAuthenticationFilter jwtAuthenticationFilter ) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager( AuthenticationConfiguration configuration ) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain( HttpSecurity http ) throws Exception {

        http
                // Configura CORS
                .cors( ( httpSecurityCorsConfigurer ) -> httpSecurityCorsConfigurer
                        .configurationSource( ( httpServletRequest ) -> {
                            // Crea una nuova configurazione CORS
                            CorsConfiguration corsConfiguration = new CorsConfiguration();
                            // Consente le richieste da "http://localhost:4200" e per sicurezza aggiungiamo anche
                            // "http://127.0.0.1:4200" che è l'indirizzo IP di localhost Angular
                            corsConfiguration.setAllowedOrigins(
                                    List.of(
                                            "http://127.0.0.1:4200/",
                                            "http://localhost:4200/",
                                            "https://developezapiblog.com/",
                                            "http://developezapiblog.com/",
                                            "https://developezapp.com/"
                                    ) );
                            // Consente tutti i metodi HTTP
                            corsConfiguration.setAllowedMethods( List.of( "GET", "POST", "PUT", "DELETE", "HEAD",
                                    "OPTIONS" ) );
                            // Consente tutti gli header
                            corsConfiguration.setAllowedHeaders( List.of( "*" ) );
                            // Consente le credenziali
                            corsConfiguration.setAllowCredentials( true );
                            corsConfiguration.setExposedHeaders( Arrays.asList( "Authorization", "X-XSRF-TOKEN" ) );
                            // Imposta l'età massima del risultato preflight (in secondi) a 3600
                            corsConfiguration.setMaxAge( 3600L );
                            return corsConfiguration;
                        } )
                )
                .csrf().disable()
                .authorizeHttpRequests( ( authorize ) ->
                        authorize
                                .requestMatchers( "/api/auth/**" ).permitAll()
                                .requestMatchers( HttpMethod.GET, "/api/posts/**" ).permitAll()
                                .requestMatchers( HttpMethod.GET, "/api/comments/**" ).permitAll()
                                // Accesso pubblico a Swagger
                                .requestMatchers( "/" ).permitAll()
                                .requestMatchers( "/swagger-ui/**" ).permitAll()
                                .requestMatchers( "/v3/api-docs/**" ).permitAll()
                                .anyRequest().authenticated() ).exceptionHandling( exception -> exception
                        .authenticationEntryPoint( jwtAuthenticationEntryPoint )
                ).sessionManagement( session -> session
                        .sessionCreationPolicy( SessionCreationPolicy.STATELESS )
                );

        http.addFilterBefore( jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class );

        return http.build();
    }
}
