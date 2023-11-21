package com.developez.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {

    @Value( "${app.jwt-secret}" )
    private String jwtSecret;

    @Value( "${app-jwt-expiration-milliseconds}" )
    private long jwtExpirationInMs;

    // Generazione del token JWT
    public String generateToken( Authentication authentication ) {
        String username = authentication.getName();

        Date currentDate = new Date();

        Date expirationDate = new Date( currentDate.getTime() + jwtExpirationInMs );

        Map<String, Object> autorities = new HashMap<>();
        autorities.put( "authorities", authentication.getAuthorities().stream()
                .map( GrantedAuthority::getAuthority )
                .toArray() );

        // Ritorna il token JWT
        return Jwts.builder()
                .setSubject( username )
                .setIssuedAt( new Date() )
                .setExpiration( expirationDate )
                .addClaims( autorities )
                .setHeaderParam( "typ", "JWT" )
                .signWith( key() )
                .compact();

    }

    // Recupero della chiave segreta
    private Key key() {
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode( jwtSecret )
        );
    }

    // Recupero del nome utente dal token JWT
    public String getUsernameFromJWT( String token ) {
        // Recupero il nome utente dal token JWT
        Claims claims = Jwts.parserBuilder()
                .setSigningKey( key() )
                .build()
                .parseClaimsJws( token )
                .getBody();

        // Ritorna il nome utente
        return claims.getSubject();
    }

    // Verifica se il token JWT è valido
    public boolean validateToken( String authToken ) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey( key() )
                    .build()
                    .parse( authToken );
            return true;
        } catch( MalformedJwtException ex ) {
            System.out.println( "Token JWT malformato" );
        } catch( ExpiredJwtException ex ) {
            System.out.println("Token JWT scaduto" );
        } catch( UnsupportedJwtException ex ) {
            System.out.println( "Token JWT non supportato" );
        } catch( IllegalArgumentException ex ) {
            System.out.println( "Token JWT vuoto" );
        }
        return false;
    }

}
